package com.zzu.booking_service.buyticket;

import com.zzu.booking_service.bean.FlightAll;
import com.zzu.booking_service.bean.LocationCity;
import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.booking_service.bean.test.User;
import com.zzu.booking_service.test.ITestDao;
import com.zzu.booking_service.test.ITestService;
import com.zzu.config.RabbitMQConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class BuyTicketService implements IBuyTicketService {

    @Autowired
    IBuyTicketDao buyTicketDao;

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
    public List<LocationCity> getAllCity() {
        String key = "allCity";
        List<Object> citys =  redisAllTemplate.opsForList().range(key,0,-1);
        List<LocationCity> cities = null;
        if(citys != null && citys.size()!=0) {
            cities = new ArrayList<>();
            for (Object city : citys) {
                cities.add((LocationCity) city);
            }
        }
        if(cities == null){
            cities = buyTicketDao.getAllCity();
            for (LocationCity city : cities) {
                redisAllTemplate.opsForList().leftPush(key,city);
            }
//            redisAllTemplate.opsForValue().set(key,cities);    //604800
            redisAllTemplate.expire(key,60480,TimeUnit.SECONDS);   //过期时间
        }
        return cities;
    }

    @Override
    public List<FlightAll> getFlightByCityTime(Map<String, Object> map) {
        String [] strings = ((String)map.get("startTime")).split(" ");
        String key = "flight"+map.get("fromCity")+map.get("toCity")+strings[0];
        List<Object> flightAlls =  redisAllTemplate.opsForList().range(key,0,-1);
        List<FlightAll> flightAll = null;
        if(flightAlls != null && flightAlls.size()!=0) {
            flightAll = new ArrayList<>();
            for (Object flight : flightAlls) {
                flightAll.add((FlightAll) flight);
            }
        }
        if(flightAll == null){
            flightAll = buyTicketDao.getFlightByCityTime(map);
            for (FlightAll all : flightAll) {
                System.out.println(all.toString());
                String s10 = "";
                String s11 = buyTicketDao.getPro(all.getStartcode());
                String s12 = buyTicketDao.getCty(all.getStartcode());
                String s13 = buyTicketDao.getTwn(all.getStartcode());
                if(s11!=null) s10 = s10 + s11;
                if(s12!=null) s10 = s10 + s12;
                if(s13!=null) s10 = s10 + s13;
                all.setStartpos(s10);
                String s20 = "";
                String s21 = buyTicketDao.getPro(all.getEndcode());
                String s22 = buyTicketDao.getCty(all.getEndcode());
                String s23 = buyTicketDao.getTwn(all.getEndcode());
                if(s21!=null) s20 = s20 + s21;
                if(s22!=null) s20 = s20 + s22;
                if(s23!=null) s20 = s20 + s23;
                all.setEndpos(s20);
                redisAllTemplate.opsForList().leftPush(key,all);
            }
            redisAllTemplate.expire(key,30,TimeUnit.SECONDS);   //过期时间
        }
        return flightAll;
    }
}
