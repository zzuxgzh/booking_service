package com.zzu.booking_service.buyticket;

import com.zzu.booking_service.bean.AnnounceShow;
import com.zzu.booking_service.bean.FlightAll;
import com.zzu.booking_service.bean.LocationCity;
import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.booking_service.test.ITestDao;
import com.zzu.booking_service.test.ITestService;
import com.zzu.config.RabbitMQConfig;
import com.zzu.entity.User;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    public boolean intoSingleInfo(int buyId,User user) {
        if (user.getTel() == null || user.getTel().trim().equals("")) return false;
        User user1 = buyTicketDao.getUserByTel(user.getTel());
        if (user1==null){   //不存在这个用户
            //创建新的用户
            if(user.getIDCard() == null || user.getName() == null) return false;
            System.out.println(user.getIDCard());
            System.out.println(user.getName());
            if(user.getIDCard().trim().equals("") || user.getName().trim().equals("")) return false;
            try {
                buyTicketDao.insertUser(user);
            } catch (Exception e) {
                System.out.println("创建失败");;
                return false;
            }
            user1 = buyTicketDao.getUserByTel(user.getTel());
        }
        String key = "buyinfo"+buyId;
        redisAllTemplate.opsForList().leftPush(key,user1);
        return true;
    }

    @Override
    public List<User> getInfo(int buyId) {
        String key = "buyinfo"+buyId;
        List<Object> listSwap = redisAllTemplate.opsForList().range(key,0,-1);
        List<User> list = new ArrayList<>();
        if (listSwap == null) return list;
        Set<String> set = new HashSet<>();
        for (Object o : listSwap) {
            User user = (User)o;
            //通过set集合将不重复的去除
            if(!set.contains(user.getTel())){
                list.add(user);
                set.add(user.getTel());
            }
        }
        return list;
    }

    @Override
    public List<AnnounceShow> selectAnnounce(String startTime, String endTime,String searchString) {
        List<AnnounceShow> announceShows = null;
        try {
            announceShows = buyTicketDao.selectAnnounce(startTime,endTime,searchString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (announceShows == null) announceShows = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (AnnounceShow announceShow : announceShows) {
            calendar.setTime(announceShow.getDate());
            calendar.add(Calendar.HOUR,-8);
            announceShow.setDate(calendar.getTime());
        }
        return announceShows;
    }
}
