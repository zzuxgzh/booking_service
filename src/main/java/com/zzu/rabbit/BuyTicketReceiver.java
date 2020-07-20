package com.zzu.rabbit;

import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.config.MybatisConfig;
import com.zzu.config.RabbitMQConfig;
import com.zzu.booking_service.test.ITestDao;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//购票之后的消息队列监听器
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE)
public class BuyTicketReceiver {

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(2,5,60, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(20000), Executors.defaultThreadFactory());

    @Autowired
    ReactiveRedisOperations<Object, Object> redisAllTemplate;

    @Autowired
    ITestDao testDao;

    //购票进入消息队列之后执行的过程
    @RabbitHandler
    public void success(String info) {

        //开始加入线程池依次抢票
        pool.execute(new Thread(){
            @Override
            public void run() {
                boolean f = false;
                String[] s = info.split("-");
                System.out.println(info);
                try {
                    testDao.desTicketNum(s[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    testDao.insertOrder(s[0]+s[1],s[1],s[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                    f = true;
                }
                if(f){  //说明订单插入不成功，也就是需要手动回滚事务
                    testDao.incTicketNum(s[1]);
                }else{
                    //更新余票
//                    String s1 = "ticket:all:" + s[1];
//                    System.out.println(s1);
//                    int num = 0;
//                    try {
//                        Ticket ticket = testDao.getTicket(s[1]);
//                        num = ticket.getNum();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.out.println("查询余票失败...");
//                    }
//                    //暂时不用
//                    redisAllTemplate.opsForValue().set(s1,num); //缓存中票的数量
//                    System.out.println(info + "模拟发送邮件信息等业务...");
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        });


    }

}
