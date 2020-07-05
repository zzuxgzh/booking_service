package com.zzu.booking_service.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zzu.booking_service.bean.test.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    ITestService testService;

    //返回jsp页面 get方法
    @GetMapping("/hello")
    public String hello() {
        return "test/index";
    }

    //返回数据 post方法
    @ResponseBody
    @PostMapping("/insert")
    public String insert(String id,int myAge) throws JsonProcessingException {

        User user = new User();
        user.setId(id);
        user.setMyAge(myAge);
        boolean re = testService.insertUser(user);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode();
        if(re) result.put("success",1);
        else result.put("success",0);
        return objectMapper.writeValueAsString(result);
    }

    //返回jsp渲染后的数据 post方法
    @PostMapping("/get")
    public String get(Model model,String id) {
        User user = testService.getUser(id);
        model.addAttribute("user", user);
        return "test/select";
    }


}
