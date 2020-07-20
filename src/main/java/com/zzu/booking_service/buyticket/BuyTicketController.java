package com.zzu.booking_service.buyticket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zzu.booking_service.bean.FlightAll;
import com.zzu.booking_service.bean.test.SingleUser;
import com.zzu.booking_service.bean.test.User;
import com.zzu.booking_service.test.ITestService;
import com.zzu.booking_service.utl.POIUtils;
import org.apache.ibatis.annotations.ResultType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/data/buyticket")
public class BuyTicketController {

    @Autowired
    IBuyTicketService buyTicketService;

    JsonNode jsonNode = null;
    Map<String,Object> map = null;

    //返回jsp渲染后的数据 post方法
    @PostMapping("/getAllLocationCity")
    public String getAllLocationCity(Model model) {
        model.addAttribute("citys",buyTicketService.getAllCity());
        return "buyticket/allLocationCity";
    }

    //返回jsp渲染后的数据 post方法
    @PostMapping("/searchTicket")
    public String searchTicket(Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;
        map = new HashMap<>();

        setString("fromCity");
        setString("toCity");
        setString("javaDate");
        String [] swap = ((String)map.get("javaDate")).split(" ");
        map.put("startTime",swap[0]+" 00:00:00");
        map.put("endTime",swap[0]+" 23:59:59");
        map.remove("javaDate");
        System.out.println(map.toString());
        ///模拟一下之后删掉？》》》》》》？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
        map.put("startTime","2020-7-19 00:00:00");
        map.put("endTime","2020-7-19 23:59:59");
        map.put("fromCity","410100");
        map.put("toCity","410100");
        ///模拟一下之后删掉？》》》》》》？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
        List<FlightAll> flightByCityTime = buyTicketService.getFlightByCityTime(map);
        System.out.println(flightByCityTime.toString());
        System.out.println(flightByCityTime.size());
        if (flightByCityTime.size()==0) return "buyticket/searchTicketTableShowNo";
        model.addAttribute("flights",flightByCityTime);
        model.addAttribute("num",flightByCityTime.size());
        return "buyticket/searchTicketTableShow";
    }

    //返回jsp渲染后的数据 post方法
    @PostMapping("/demo")
    public String demo(Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;
        map = new HashMap<>();



        return "buyticket/demo";
    }


    public String getString(String key){
        return jsonNode.path(key).asText();
    }

    public void setString(String key){
        map.put(key,jsonNode.path(key).asText());
    }

    public int getInt(String key){
        return jsonNode.path(key).asInt();
    }

    public void setInt(String key){
        map.put(key,jsonNode.path(key).asInt());
    }

}
