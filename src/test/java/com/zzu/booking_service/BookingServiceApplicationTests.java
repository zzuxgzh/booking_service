package com.zzu.booking_service;

import com.zzu.booking_service.bean.test.User;
import com.zzu.booking_service.test.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BookingServiceApplicationTests {

    //注入redis
    @Autowired
    StringRedisTemplate stringRedisTemplate;    //操作kv都为字符串的
    //注入redis
    @Autowired
    RedisTemplate redisTemplate;                //操作kv都为对象的

    @Autowired
    RedisTemplate<Object,Object> myRedisTemplate;

    @Autowired
    RedisTemplate<Object,Object> redisAllTemplate;


    //消息队列自动注入
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    TestService testService;

    @Test
    void contextLoads() {

        System.out.println("测试一下");

    }

    /**
     * 字符串 列表 集合 散列 有序集合
     */
    @Test
    public void test01() {
        //给redis保存一个数据
        stringRedisTemplate.opsForValue().append("msg", "test1");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);

        stringRedisTemplate.opsForList().leftPush("mylist", "1");
        stringRedisTemplate.opsForList().leftPush("mylist", "2");
    }

    //测试redis保存对象
    @Test
    public void test02() {

        User user = testService.getUser("hgy");
        //默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
//        redisTemplate.opsForValue().set("id-01", user);
        //将数据以json的格式保存
        //将自己的
        myRedisTemplate.opsForValue().set("myid-01", user);

    }


    @Test
    public void creatExchange() {
        amqpAdmin.declareExchange(new DirectExchange("amqpAdmin.direct"));
        amqpAdmin.declareQueue(new Queue("amqpAdmin.queue", true));
        //创建绑定规则
        amqpAdmin.declareBinding(new Binding("amqpAdmin.queue", Binding.DestinationType.QUEUE, "amqpAdmin.direct", "amqp.jja", null));
        System.out.println("创建完成");
    }

    //测试消息队列发送消息
    @Test
    void sendMsg() {
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "这是第一个消息");
        map.put("data", Arrays.asList("helloWorld", 123, true));
        //对象被默认初始化之后发送出去
        rabbitTemplate.convertAndSend("amqpAdmin.direct", "amqp.jja", map);
    }

    //测试消息队列接收消息
    //那么如何传递json数据而不会序列化呢
    @Test
    public void receive() {
        Object o = rabbitTemplate.receiveAndConvert("amqpAdmin.queue");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    //redis配置
    @Test
    public void testRedis(){
//        testService.selectTicket("1");
        testService.buyTicket("hgy2","2","hgy");
        testService.buyTicket("hgy2","2","hgy");
        testService.buyTicket("hgy2","2","hgy");
        testService.buyTicket("hgy2","2","hgy");
    }

    //redis配置
    @Test
    public void testRedis1(){
        int max = 9;
        stringRedisTemplate.opsForValue().set("test1", String.valueOf(new Integer(max)),604800);
        System.out.println(stringRedisTemplate.opsForValue().get("test1"));
    }

    //测试redisAll模板
    @Test
    public void testRedis2(){
//        User wll = testService.getUser("wll");
//        redisAllTemplate.opsForValue().set("wll",wll);
        redisAllTemplate.opsForValue().set("wllnum1",2);
//        User to = (User) redisAllTemplate.opsForValue().get("wll");
        int wllnum = (int) redisAllTemplate.opsForValue().get("wllnum1");
//        System.out.println(to.getMyAge() + "/" + wllnum);
        System.out.println("/" + wllnum);

    }


}
