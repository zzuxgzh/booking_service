package com.zzu.booking_service.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.booking_service.bean.test.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/data/test")
public class TestController {

    @Autowired
    ITestService testService;

    //测试session
    @ResponseBody
    @RequestMapping(value = "/setSession")
    public Map<String, Object> getSession(HttpServletRequest request) {
        request.getSession().setAttribute("userName", "glmapper");
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        return map;
    }
    //测试session
    @ResponseBody
    @RequestMapping(value = "/getSession")
    public String get(HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        return userName;
    }



    //返回jsp页面 get方法
    @GetMapping("/hello")
    public String hello() {
        return "test/index";
    }

    //返回数据 post方法
    @ResponseBody
    @PostMapping(value = "/insert")
    public String insert(@RequestBody JsonNode jsonNode, HttpSession httpSession) throws JsonProcessingException {

        String id = jsonNode.path("id").toString();
        String myAge = jsonNode.path("myAge").toString();
        System.out.println(id+myAge);


        String s = (String) httpSession.getAttribute("name");
        if (s==null) httpSession.setAttribute("name",id);

        if(myAge=="1") return "成功";
        System.out.println(id+myAge);
        User user = new User();
        user.setId(id);
        user.setMyAge(Integer.valueOf(myAge));
        boolean re = testService.insertUser(user);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        if(re) result.put("success","成功");
        else result.put("success","失败");
        result.put("name", (String) httpSession.getAttribute("name"));
        return objectMapper.writeValueAsString(result);
    }

    //返回jsp渲染后的数据 post方法
    @PostMapping("/get")
    public String get(Model model,String id) {
        User user = testService.getUser(id);
        model.addAttribute( "user", user);
        return "test/select";
    }

    //返回jsp渲染后的数据 post方法
    @PostMapping("/getAll")
    public String getAll(Model model) {
        List<User> allUser = testService.getAllUser();
        model.addAttribute("allUser", allUser);
        return "test/selectAll";
    }

    // get方法
    @ResponseBody
    @GetMapping("/creatTicket")
    public String creatTicket(String id,String name,int num) {
        boolean re = testService.creatTicket(id,name,num);
        if(re) return "创建成功";
        else return "创建失败";
    }

    // get方法
    @ResponseBody
    @GetMapping("/selectTicket")
    public String selectTicket(String testticketid) {
        Integer ticket = testService.selectTicket(testticketid);
        System.out.println(ticket);
        if (ticket==null) return "票不存在";
        return String.valueOf(ticket);
    }

    // get方法
    @ResponseBody
    @GetMapping("/buyTicket")
    public String buyTicket(String userid,String testticketid) {
        boolean re = testService.buyTicket(userid+testticketid,testticketid,userid);
        if(re) return "正在抢票中...";
        else return "票已售完，请稍后再试";
    }

    // get方法
    @ResponseBody
    @GetMapping("/buyTicketNo")
    public String buyTicketNo(String userid,String testticketid) throws InterruptedException {
        boolean re = testService.buyTicketNo(userid+testticketid,testticketid,userid);
        if(re) return "已成功抢到，请在五分钟内付款";
        else return "票已售完，请稍后再试";
    }

    // get方法
    @ResponseBody
    @GetMapping("/selectOrder")
    public String selectOrder(String userid,String testticketid) {
        boolean re = testService.selectOrder(userid+testticketid);
        if(re) return "已成功抢到，请在五分钟内付款";
        else return "未抢到票，请稍后再试";
    }


}
