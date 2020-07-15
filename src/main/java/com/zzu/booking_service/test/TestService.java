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
        Integer ticket = null;
        try {
            ticket = Integer.valueOf(stringRedisTemplate.opsForValue().get(key));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("redis里查不到......");
        }
        if(ticket==null){
            try {
                Ticket ticket1 = testDao.getTicket(id);
                ticket = ticket1.getNum();
                System.out.println(ticket);
                int tic = ticket;
                stringRedisTemplate.opsForValue().set(key, String.valueOf(tic),604800);
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
        try {
            testDao.buyTicketNo(id,testticketid,userid);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("购买出错......");
            return false;
        }
        //模拟发送验证码等业务
        Thread.sleep(20);
        return true;
    }

    @Override
    public boolean buyTicket(String id, String testticketid, String userid) {




        String keyMax = "ticket:num:max:" + testticketid;
        String keyOn = "ticket:num:on:" + testticketid;
        Integer max = 0;
        Integer on = 0;
        String maxStr = null;
        String onStr = null;
        try {
            maxStr  =  stringRedisTemplate.opsForValue().get(keyMax);
            System.out.println(maxStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("重新获取最大值");
        }
        try {
            onStr =  stringRedisTemplate.opsForValue().get(keyOn);
            System.out.println(onStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("重新获取当前值");
        }
        if(maxStr == null){
            try {
                max = (testDao.getTicket(testticketid)).getNum();
                maxStr = String.valueOf(max);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            System.out.println(maxStr);
            stringRedisTemplate.opsForValue().set(keyMax, maxStr,604800);
        }
        max = Integer.valueOf(maxStr);
        if(onStr == null){
            stringRedisTemplate.opsForValue().set(keyOn, String.valueOf(new Integer(1)),60);
            on = 1;
        }else on = Integer.valueOf(onStr);
        if(on>max) return false;

        /**
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "正在购票......");
        map.put("info", Arrays.asList(userid, testticketid));
        //对象被默认初始化之后发送出去
        try {
            rabbitTemplate.convertAndSend("buytiket", map);
        } catch (AmqpException e) {
            e.printStackTrace();
            return false;
        }
        //说明成功进入消息队列，有希望成功

         */
        return true;
    }

    @Override
    public boolean selectOrder(String id) {
        return false;
    }

}
