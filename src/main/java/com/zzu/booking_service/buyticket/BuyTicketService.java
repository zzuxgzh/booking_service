package com.zzu.booking_service.buyticket;

import com.zzu.booking_service.bean.*;
import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.booking_service.test.ITestDao;
import com.zzu.booking_service.test.ITestService;
import com.zzu.config.RabbitMQConfig;
import com.zzu.entity.User;
import com.zzu.tool.CookieUtil;
import com.zzu.tool.SmsTool;
import com.zzu.tool.TicketStrategy;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
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
        /////////////暂时将时间延长，方便使用??????????????????????????
        redisAllTemplate.expire(key,1200,TimeUnit.SECONDS);   //刷新其过期时间为两分钟
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

    @Override
    public boolean flushInfo(int buyId) {
        try {
            redisAllTemplate.delete("buyinfo"+buyId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean buyTicket(int buyId, int flightId, String kind) {

        int type = 0;
        if (kind.equals("economy")) type = 1;
        if (kind.equals("business")) type = 2;
        if (kind.equals("first")) type = 3;
        BigDecimal price = buyTicketDao.getPriceById(flightId);
        System.out.println("price"+price);
        UserOn userOn = new UserOn(buyId,flightId,kind,type);
        List<BuyUserOn> list = new ArrayList<>();
        //查找购买者的身份
        int status = buyTicketDao.getStatusById(buyId);
        List<User> info = getInfo(buyId);
        for (User user : info) {
            BuyUserOn buyUserOn = new BuyUserOn();
            buyUserOn.setId(user.getUserId());
            buyUserOn.setMoney(TicketStrategy.getTicketPriceBySttus(price,status,user.getStatus()));
            list.add(buyUserOn);
            System.out.println(buyUserOn.toString());
        }
        //首先跟缓存中的票数对比，进行初步筛选
        String keyMax = "flight:num:max:"+kind+":"+ flightId;
        String keyOn = "flight:num:on:"+kind+":"+ flightId;
        int max = -1;
        int on = -1;
        try {
            max  = (int) redisAllTemplate.opsForValue().get(keyMax);
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("重新获取最大值");
        }
        try {
            on = (int) redisAllTemplate.opsForValue().get(keyOn);
            System.out.println("2:"+on);
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("重新获取当前值");
        }
        if(max == -1){
            try {
                max = buyTicketDao.getFlightNum(kind,flightId);
                redisAllTemplate.opsForValue().set(keyMax,max);
                redisAllTemplate.expire(keyMax,5,TimeUnit.SECONDS);
            } catch (Exception e) {
//                e.printStackTrace();
                return false;
            }
            System.out.println("3:"+max);
        }
        if(on == -1){
            on = 1;
            redisAllTemplate.opsForValue().set(keyOn, on);
            redisAllTemplate.expire(keyOn,10,TimeUnit.SECONDS);
        }else try {
            for (BuyUserOn buyUserOn : list) {
                redisAllTemplate.opsForValue().increment(keyOn);
            }
            try {
//                on = (int) redisAllTemplate.opsForValue().get(keyOn);
            } catch (Exception e) {}
        } catch (Exception e) {}

//        System.out.println("max:"+max+"on:"+on);
        if(on>max) return false;

        //现在将数据收集齐了，开始进入缓存
        String code = SmsTool.getCode();
        String keyUser = "ticket:buyuser:"+flightId+":"+buyId+":"+code;
        String keyList = "ticket:buylist:"+flightId+":"+buyId+":"+code;
        redisAllTemplate.opsForValue().set(keyUser,userOn);
        redisAllTemplate.expire(keyUser,120,TimeUnit.SECONDS);
        for (BuyUserOn buyUserOn : list) {
            System.out.println(keyList);
            redisAllTemplate.opsForList().leftPush(keyList,buyUserOn);
        }
        redisAllTemplate.expire(keyList,120,TimeUnit.SECONDS);
        String mqInfo = keyUser+"-"+keyList;
//        传入消息队列进行处理
        //暂时模拟成在本地处理
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.BUY_TICKET_QUEUE, mqInfo);
        } catch (AmqpException e) {
//            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public int getSingleTicketNumById(int id, int flightId) {
        int re = buyTicketDao.getSingleTicketNumById(id,flightId);
        System.out.println("service:re:"+re);
        return re;
    }

    // 0 不存在    1 存在但是-1   2 存在
    @Override
    public int getStatusByMobile(String tel) {
        int status = -1;
        try {
            status = buyTicketDao.getStatusByMobile(tel);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        if (status == -1) { //说明存在，需要删除掉对应数据
            return 1;
        }else return 2;
    }

    // 0 失败 1 成功
    @Override
    public int insertUserRegister(User user) {
        int re = 0;
        int status = -2;
        try {
            status = buyTicketDao.getStatusByMobile(user.getTel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("status:"+status);
        try {
            if (status == -1){//说明只需要修改就可以了
                re = buyTicketDao.updateUserRegister(user);
            }else {
                re = buyTicketDao.insertUserRegister(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        if (re == 1) return 1;
        else return 0;
    }

    // 0 失败 1 成功
    @Override
    public int selectUserExist(String tel, String pwd) {
        int re = 0;
        try {
            re = buyTicketDao.selectUserExist(tel,pwd);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        if (re == -1) return 0;
        else return 1;
    }

    @Override
    public User getUserByMobile(String tel) {
        try {
            return buyTicketDao.getUserByMobile(tel);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
