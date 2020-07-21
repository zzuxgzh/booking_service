package com.zzu.rabbit;


import com.zzu.booking_service.bean.BuyUserOn;
import com.zzu.booking_service.bean.UserOn;
import com.zzu.booking_service.buyticket.IBuyTicketDao;
import com.zzu.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//购票之后的消息队列监听器
@Component
@RabbitListener(queues = RabbitMQConfig.BUY_TICKET_QUEUE)
public class BuyTicketQueueReceiver {

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(2,5,60, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(20000), Executors.defaultThreadFactory());

    @Autowired
    RedisTemplate<Object, Object> redisAllTemplate;

    @Autowired
    IBuyTicketDao buyTicketDao;

    //购票进入消息队列之后执行的过程
    @RabbitHandler
    public void success(String mqInfo) {

        //开始加入线程池依次抢票
        pool.execute(new Thread(){
            @Override
            public void run() {
                String[] split = mqInfo.split("-");
                String keyUser = split[0];
                String keyList = split[1];
                System.out.println("keyUser-list:"+keyUser + "." + keyList);
                //开始取数据执行sql
                redisAllTemplate.opsForValue().get(keyUser);
                UserOn theUser = (UserOn) redisAllTemplate.opsForValue().get(keyUser);
                List<Object> range = redisAllTemplate.opsForList().range(keyList, 0, -1);
                List<BuyUserOn> theList = new ArrayList<>();
                for (Object o : range) {
                    theList.add((BuyUserOn) o);
                    System.out.println("0:"+o.toString());
                }
                int thenum = theList.size();
                System.out.println("num:"+thenum);
                //对数据库进行减票操作
                try {
                    buyTicketDao.decFlightNum(theUser.getKind(),thenum,theUser.getFlightId());
                } catch (Exception e) {
                    e.printStackTrace();//
                    System.out.println("说明票不够。。。需要推出");
                    return;
                }

                //依次对每个用户进行订单的添加
                //flightId,buyUserId,type,price,userId,onTime
                int themax = 0;
                //当前时间
                Date date = new Date();
                DateFormat format = DateFormat.getDateTimeInstance();//可以精确到时分秒
                String onTime = format.format(date);
                System.out.println(onTime);
                for (BuyUserOn buyUserOn : theList) {
                    int re = 0;
                    try {
                        re = buyTicketDao.insetTicket(theUser.getFlightId(),buyUserOn.getId(),theUser.getType(),buyUserOn.getMoney(),theUser.getId(),onTime);
                    } catch (Exception e) {
                        e.printStackTrace();
                        re = 0;
                    }
                    themax = themax + re;
                }
                //如果上面报错，对数据库进行加票操作 ,或者通过上面加的订单不够，将其余票加回来
                if(thenum == themax) return; //说明所有人的订单都加上了
                try {
                    buyTicketDao.addFlightNum(theUser.getKind(),thenum-themax,theUser.getFlightId());
                } catch (Exception e) {
                    e.printStackTrace();//
                    System.out.println("没加成功。。。需要推出");
                    return;
                }
                return;
            }

        });


    }

}