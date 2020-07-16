package com.zzu.booking_manager;

import com.zzu.dao.IUserDao;
import com.zzu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/data/manager/")
public class testCo {
    @Autowired
    private IUserDao userDao;

    @GetMapping("get")
    @ResponseBody
    public String getUserById(int id){
        User user = null;
        if(id!=0){
            user = userDao.getUserById(id);
        }
        return "lcx name:"+user.getName();
    }
}
