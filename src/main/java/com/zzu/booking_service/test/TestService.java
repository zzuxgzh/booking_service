package com.zzu.booking_service.test;

import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.booking_service.bean.test.User;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sun.nio.ch.ThreadPool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class TestService implements ITestService {

    @Autowired
    ITestDao testDao;

    //注入redis
    @Autowired
    StringRedisTemplate stringRedisTemplate;    //操作kv都为字符串的
    //注入redis
    @Autowired
    RedisTemplate redisTemplate;                //操作kv都为对象的
    @Autowired
    RedisTemplate<Object, Object> myRedisTemplate;

    @Autowired
    RedisTemplate<Object,Object> redisAllTemplate;

    //消息队列自动注入
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;


    @Override
    public User getUser(String id) {
        return testDao.getUser(id);
    }

    @Override
    public List<User> getAllUser() {
        return testDao.getAllUser();
    }

    @Override
    public boolean insertUser(User user) {
        return testDao.insertUser(user) == 1;
    }



    @Override
    public boolean creatTicket(String id, String name, int num) {
        try {
            testDao.insertTicket(id,name,num);
        } catch (Exception e) {
            System.out.println("插入失败...");
            return false;
        }
        return true;
    }

    //使用缓存
    @Override
//    @Cacheable(cacheNames = "book",key = "#id+':num'")
    public Integer selectTicket(String id) {
        String key = "ticket:all:" + id;
        int ticket = -1;
        try {   //redisAllTemplate.opsForValue().set(key,ticket);
            ticket = (int) redisAllTemplate.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("redis里查不到......");
        }
        if(ticket==-1){
            try {
                Ticket ticket1 = testDao.getTicket(id);
                ticket = ticket1.getNum();
                System.out.println(ticket);
                redisAllTemplate.opsForValue().set(key, ticket);    //604800
                redisAllTemplate.expire(key,3000,TimeUnit.SECONDS);   //过期时间
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("查到的票为null，不存在......");
                return null;
            }
        }
        return ticket;
    }

    //正常情况下买票
    @Override
    public boolean buyTicketNo(String id, String testticketid, String userid) throws InterruptedException {
        boolean f = false;
        String[] s = {userid,testticketid};
        try {
            testDao.desTicketNum(s[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            testDao.insertOrder(s[0]+s[1],s[1],s[0]);
        } catch (Exception e) {
            e.printStackTrace();
            f = true;
        }
        if(f){  //说明订单插入不成功，也就是需要手动回滚事务
            testDao.incTicketNum(s[1]);
            return false;
        }else{
            System.out.println("模拟发送邮件信息等业务...");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //模拟发送验证码等业务
        Thread.sleep(20);
        return true;
    }

    //优化后的买票
    @Override
    public boolean buyTicket(String id, String testticketid, String userid) {

        String keyMax = "ticket:num:max:" + testticketid;
        String keyOn = "ticket:num:on:" + testticketid;
        int max = -1;
        int on = -1;
        try {
            max  = (int) redisAllTemplate.opsForValue().get(keyMax);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("重新获取最大值");
        }
        try {
            on = (int) redisAllTemplate.opsForValue().get(keyOn);
            System.out.println("2:"+on);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("重新获取当前值");
        }
        if(max == -1){
            try {
                max = (testDao.getTicket(testticketid)).getNum();
                redisAllTemplate.opsForValue().set(keyMax,max);
                redisAllTemplate.expire(keyMax,86400,TimeUnit.SECONDS);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            System.out.println("3:"+max);
        }
        if(on == -1){
            on = 1;
            redisAllTemplate.opsForValue().set(keyOn, on);
            redisAllTemplate.expire(keyOn,60,TimeUnit.SECONDS);
        }else try {
            redisAllTemplate.opsForValue().increment(keyOn);
            try {
                on = (int) redisAllTemplate.opsForValue().get(keyOn);
            } catch (Exception e) {}
        } catch (Exception e) {}

        System.out.println("max:"+max+"on:"+on);
        if(on>max) return false;

        //对象被默认初始化之后发送出去
        try {
            rabbitTemplate.convertAndSend("buytiket", userid+"-"+testticketid);
        } catch (AmqpException e) {
            e.printStackTrace();
            return false;
        }
        //说明成功进入消息队列，有希望成功

        return true;
    }

    @Override
    public boolean selectOrder(String id) {
        int re = -1;
        try {
            re = testDao.getOrder(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("re:"+re);
        if(re!=-1) return true;
        return false;
    }

}
