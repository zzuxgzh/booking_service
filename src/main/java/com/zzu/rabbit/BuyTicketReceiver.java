package com.zzu.rabbit;

import com.zzu.config.RabbitMQConfig;
import com.zzu.booking_service.test.ITestDao;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//购票之后的消息队列监听器
@Component
public class BuyTicketReceiver {

    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(2,5,60, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(20000), Executors.defaultThreadFactory());

    @Autowired
    ITestDao testDao;

    //购票进入消息队列之后执行的过程
    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void success(String info) {

        //开始加入线程池依次抢票
        pool.execute(new Thread(){
            @Override
            public void run() {
                try {
                    testDao.desTicketNum("1");
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(info + "模拟发送邮件信息等业务...");
            }

        });


    }

}
