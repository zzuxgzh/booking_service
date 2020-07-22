package com.zzu.booking_manager.user;

import com.zzu.booking_manager.user.userservice.IUserService;
import com.zzu.entity.Staff;
import com.zzu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/data/manager/user/")
public class UserController {
    @Autowired
    private IUserService ius;

    @GetMapping("getUserById")
    @ResponseBody
    public User getUserById(int id){
        return ius.getUserById(id);
    }


    @GetMapping("getAllUser")
    @ResponseBody
    public List<User> getAllUser(){
        List<User> aa = null;
        aa=ius.getAllUsers();
        return aa;
    }

    @RequestMapping(value="insertUser",method= RequestMethod.POST)
    @ResponseBody
    public String insertUser(User user){
        return ius.insertSelective(user);
    }

    @RequestMapping(value="updateUser",method= RequestMethod.POST)
    @ResponseBody
    public String updateUser(User user){
        return ius.updateSelective(user);
    }

    @RequestMapping(value = "deleteUserById",method = RequestMethod.POST)
    @ResponseBody
    public String   deleteUserById(int id){
        return ius.deleteById(id);
    }

    @ResponseBody
    @RequestMapping(value = "deleteMore",method = RequestMethod.POST)
    public String deleteMore(String id){

        String[] a=id.split(",");
        int flag=0;
        for (int i = 0; i < a.length; i++) {
            String b = ius.deleteById(Integer.parseInt(a[i]));
            if(b.equals("删除失败，请重新删除")){
                flag=0;
            }else{
                flag=1;
            }
        }
        if(flag==1){
            return "删除成功";
        }else{
            return "删除失败，请重新删除";
        }
    }

    @GetMapping("getSomeUser")
    @ResponseBody
    public List<User> getSomeUser(String str){
        List<User> aa = null;
        aa=ius.getSomeUser(str);
        return aa;
    }

}