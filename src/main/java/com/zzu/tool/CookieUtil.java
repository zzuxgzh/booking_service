package com.zzu.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;

@Component
public class CookieUtil {

    @Autowired
    RedisTemplate<Object,Object> redisAllTemplate;

    //创建一个cookie
    public String setCookie(HttpServletRequest request, HttpServletResponse response){
        String uuid = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("cookieId",uuid);
        cookie.setPath(request.getContextPath());
//        cookie.setMaxAge(60*60*24);    //1天   不设置则关闭浏览器就关闭
        response.addCookie(cookie);
        return uuid;
    }

    //仅仅根据id在redis创建对应的值
    public void setValueById(String cookieId,String key,Object value){
        redisAllTemplate.opsForValue().set("cookie:"+cookieId+":"+key,value);
    }

    //从cookie中获取一个值
    public Object getValue(String key,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("cookieId")){
                //说明存在cookie，要到redis中去获取对应的值
                String cookieId = cookie.getValue();
                Object o = redisAllTemplate.opsForValue().get("cookie:"+cookieId+":"+key);
                return o;
            }
        }
        return null;
    }

    //从cookie中存一个值
    public void setValue(String key,Object value,HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("cookieId")){
                //说明存在cookie，要到redis中去获取对应的值
                String cookieId = cookie.getValue();
                redisAllTemplate.opsForValue().set("cookie:"+cookieId+":"+key,value);
                return;
            }
        }
    }

}
