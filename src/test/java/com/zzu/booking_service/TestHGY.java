package com.zzu.booking_service;

import com.zzu.booking_service.buyticket.BuyTicketService;
import com.zzu.tool.SmsTool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class TestHGY {

    @Autowired
    BuyTicketService buyTicketService;

    public static void main(String[] args) {


//        String[] s = new String("user1 1").replace(" ","-").split("-");
//        System.out.println(s.length);

        String[] s = new String("user1 1").split(" ");
        System.out.println(s.length);

    }

    @Test
    public void test1(){
        buyTicketService.getAllCity();
    }

    @Test
    public void test2(){
        Map<String,Object> map = new HashMap<>();
        map.put("startTime","2020-7-19 00:00:00");
        map.put("endTime","2020-7-19 23:59:59");
        map.put("fromCity","410100");
        map.put("toCity","410100");
        System.out.println(map.toString());

//        buyTicketService.getFlightByCityTime(map);
    }

    @Test
    public void test3(){
        SmsTool.sendSms("15839125979","123466");
    }

    @Test
    public void test4(){

        Date date = new Date();
        DateFormat format = DateFormat.getDateTimeInstance();//可以精确到时分秒
        System.out.println(format.format(date));

    }

    @Test
    public void test5(){

        buyTicketService.buyTicket(22,1,"economy");

    }

}
