package com.zzu.booking_service.buyticket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zzu.booking_service.bean.FlightAll;
import com.zzu.booking_service.bean.test.SingleUser;
import com.zzu.booking_service.test.ITestService;
import com.zzu.booking_service.utl.POIUtils;
import com.zzu.entity.User;
import com.zzu.tool.CookieUtil;
import com.zzu.tool.SmsTool;
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
import java.util.*;

@Controller
@RequestMapping("/data/buyticket")
public class BuyTicketController {

    @Autowired
    CookieUtil cookie;

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
    @PostMapping("/getAnnounceList")
    public String getAnnounceList(Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;
        String startTime = getString("startTime");
        String endTime = getString("endTime");
        String searchString = getString("searchString");
        model.addAttribute("list",buyTicketService.selectAnnounce(startTime,endTime,searchString));
        return "buyticket/announceTableShow";
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
//        map.put("startTime","2020-7-18 00:00:00");
//        map.put("endTime","2020-7-19 23:59:59");
//        map.put("fromCity","410100");
//        map.put("toCity","410100");
        ///模拟一下之后删掉？》》》》》》？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？？
        List<FlightAll> flightByCityTime = buyTicketService.getFlightByCityTime(map);
        System.out.println(flightByCityTime.toString());
        System.out.println(flightByCityTime.size());
        if (flightByCityTime.size()==0) return "buyticket/searchTicketTableShowNo";
        Calendar calendar = Calendar.getInstance();
        for (FlightAll flightAll : flightByCityTime) {
            calendar.setTime(flightAll.getStarttime());
            calendar.add(Calendar.HOUR,-8);
            flightAll.setStarttime(calendar.getTime());
            calendar.setTime(flightAll.getPreendtime());
            calendar.add(Calendar.HOUR,-8);
            flightAll.setPreendtime(calendar.getTime());
        }
        model.addAttribute("flights",flightByCityTime);
        model.addAttribute("num",flightByCityTime.size());
        return "buyticket/searchTicketTableShow";
    }

    @ResponseBody
    @PostMapping("/insetSingleInfo")
    public String singleInfo(HttpServletRequest request,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;
        map = new HashMap<>();
        User user = new User();
        user.setName(getString("userinfoName"));
        user.setTel(getString("userinfoMobile"));
        user.setIDCard(getString("userinfoId"));
        user.setCompany(getString("userinfoCompane"));
        user.setGender(getInt("sex"));
        int buyId = 22; //模拟当前登陆的用户
         if(cookie.getValue("userId",request)!=null) buyId= (int) cookie.getValue("userId",request);
//        buyId = 22; //模拟当前登陆的用户
        boolean re = buyTicketService.intoSingleInfo(buyId,user);
        if (re) return "1";
        else return "0";
    }

    @PostMapping("/refreshInfoTable")
    public String refreshInfoTable(HttpServletRequest request,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;
        map = new HashMap<>();

        int buyId = 22; //模拟当前登陆的用户
        if(cookie.getValue("userId",request)!=null) buyId= (int) cookie.getValue("userId",request);
//        buyId = 22; //模拟当前登陆的用户
        List<User> list = buyTicketService.getInfo(buyId);
        model.addAttribute("list",list);
        if (list.size()==0)return "buyticket/luruInfoTableShowNo";
        else return "buyticket/luruInfoTableShow";
    }

    @ResponseBody
    @PostMapping("/getInfoNumber")
    public String getInfoNumber(HttpServletRequest request,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;
        map = new HashMap<>();
        int buyId = 22; //模拟当前登陆的用户
        if(cookie.getValue("userId",request)!=null) buyId= (int) cookie.getValue("userId",request);
//        buyId = 22; //模拟当前登陆的用户
        List<User> list = buyTicketService.getInfo(buyId);
        return String.valueOf(list.size());
    }

    //清空当前用户已录入的信息
    @ResponseBody
    @PostMapping("/flushInfo")
    public String flushInfo(HttpServletRequest request,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;
        map = new HashMap<>();
        int buyId = 22; //模拟当前登陆的用户
        if(cookie.getValue("userId",request)!=null) buyId= (int) cookie.getValue("userId",request);
//        buyId = 22; //模拟当前登陆的用户
        boolean re = buyTicketService.flushInfo(buyId);
        if (re) return "1";
        else return "0";
    }

    //进行购票操作
    @ResponseBody
    @PostMapping("/buyTicket")
    public String buyTicket(HttpServletRequest request,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;

        String kind = getString("kind");
        String flightId = getString("flightId");
        if(kind.trim().equals("") || flightId.trim().equals("")) return "0";

        int buyId = 22; //模拟当前登陆的用户
        if(cookie.getValue("userId",request)!=null) buyId= (int) cookie.getValue("userId",request);
//        buyId = 22; //模拟当前登陆的用户
        int flight = 0;
        try {
            flight = Integer.valueOf(flightId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0";
        }
        boolean re = buyTicketService.buyTicket(buyId,flight,kind);
        if (re) return "1";
        else return "0";
    }

    //发送验证码
    @ResponseBody
    @PostMapping("/getStatusByMobile")
    public String getStatusByMobile(HttpServletRequest request,HttpServletResponse response,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;

        String tel = getString("tel");
        int re = buyTicketService.getStatusByMobile(tel);
        if(re != 2){
            String cookieId = cookie.setCookie(request,response); //建立cookie，当作session使用
            cookie.setValueById(cookieId,"onTel",tel);

            String registerCode = SmsTool.getCode();
            try {
//                SmsTool.sendSms(tel,registerCode);
            } catch (Exception e) {
                e.printStackTrace();
                re = 3; //3 代表验证码发送失败
            }
            System.out.println(registerCode);
            cookie.setValueById(cookieId,"registerCode",registerCode);
        }
        return String.valueOf(re);

    }

    //注册用户
    @ResponseBody
    @PostMapping("/insertUserRegister")
    public String insertUserRegister(HttpServletRequest request,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;

        String onCode = getString("onCode");
        String registerCode = null;
        try {
            registerCode = (String) cookie.getValue("registerCode",request);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        System.out.println(onCode + "." + registerCode);
        if (!onCode.equals(registerCode)) return "0"; //验证码不对，返回0

        User user = new User();
//        name,pwd,tel,IDCard,gender,company,status
        user.setName(getString("name"));
        user.setPwd(getString("pwd"));
        user.setTel((String) cookie.getValue("onTel",request));
        user.setIDCard(getString("IDCard"));
        user.setGender(getInt("gender"));
        user.setCompany(getString("company"));
        user.setStatus(getInt("status"));

        int re = buyTicketService.insertUserRegister(user);
        if(re == 1){//将信息存入cookie
            cookie.setValue("userId",user.getUserId(),request);
            cookie.setValue("tel",user.getTel(),request);
        }
        return String.valueOf(re);

    }

    //登陆用户 账号密码方式
    @ResponseBody
    @PostMapping("/loginByPwd")
    public String loginByPwd(HttpServletRequest request,HttpServletResponse response,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;

        String tel = getString("tel");
        String pwd = getString("pwd");

        int re = buyTicketService.selectUserExist(tel,pwd);
        if (re == 1) {
            User user = buyTicketService.getUserByMobile(tel);
            if (user == null) return "0";
            //数据存入session中
            String cookieId = cookie.setCookie(request,response);
            cookie.setValueById(cookieId,"tel",tel);
            cookie.setValueById(cookieId,"userId",user.getUserId());
            System.out.println(cookie);
        }
        return String.valueOf(re);

    }

    //登陆用户 验证码发送请求
    @ResponseBody
    @PostMapping("/sendLoginCode")
    public String sendLoginCode(HttpServletRequest request,HttpServletResponse response,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;

        String onTel = getString("tel");
        int re = buyTicketService.getStatusByMobile(onTel);
        if(re != 0){
            String cookieId = cookie.setCookie(request,response);
            cookie.setValueById(cookieId,"onTel",onTel);
            String loginCode = SmsTool.getCode();
            try {
                SmsTool.sendSms(onTel,loginCode);
            } catch (Exception e) {
                e.printStackTrace();
                re = 3; //3 代表验证码发送失败
            }
            cookie.setValueById(cookieId,"loginCode",loginCode);
        }
        return String.valueOf(re);

    }

    //登陆用户 验证码方式
    @ResponseBody
    @PostMapping("/loginByCode")
    public String loginByCode(HttpServletRequest request,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;

        String tel = (String) cookie.getValue("onTel",request);
        String onCode = getString("onCode");
        String loginCode = (String) cookie.getValue("loginCode",request);
        if (!onCode.equals(loginCode)) return "0"; //验证码不对，返回0
        User user = buyTicketService.getUserByMobile(tel);
        //信息存储在session中
        cookie.setValue("tel",tel,request);
        cookie.setValue("userId",user.getUserId(),request);
        return "1";

    }

    //上传excel
    @ResponseBody
    @RequestMapping("/uploadExcel")
    public String uploadExcel(HttpServletRequest request,HttpSession httpSession,@RequestParam(value = "file") MultipartFile excelFile, HttpServletRequest req, HttpServletResponse resp){
        Map<String, Object> param = new HashMap<String, Object>();
        List<User> list = new ArrayList<User>();
        try {
            List<String[]> rowList = POIUtils.readExcel(excelFile);
            for(int i=0;i<rowList.size();i++){
                String[] row = rowList.get(i);
                User info = new User();

                info.setTel(row[0]);
                info.setName(row[1]);
                if(row[2].equals("男")) info.setGender(1);
                else if(row[2].equals("女")) info.setGender(0);
                else return "0";
                info.setIDCard(row[3]);
                info.setCompany(row[4]);

                list.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        }
        System.out.println(list.toString());
        ////////////后面写代码，给他们分别购票
        int buyId = 22; //模拟当前登陆的用户
        if(cookie.getValue("userId",request)!=null) buyId= (int) cookie.getValue("userId",request);
//        buyId = 22; //模拟当前登陆的用户
        for (User user : list) {
            boolean f = buyTicketService.intoSingleInfo(buyId,user);
            if (!f) return "0";
        }
        return "1";
    }

    //查看当前用户的当前订单的数量
    @ResponseBody
    @PostMapping("/getSingleTicketNumById")
    public String getSingleTicketNumById(HttpServletRequest request,HttpSession httpSession,Model model,@RequestBody JsonNode jsonNode) {
        this.jsonNode = jsonNode;
        map = new HashMap<>();
        int buyId = 22; //模拟当前登陆的用户
        if(cookie.getValue("userId",request)!=null) buyId= (int) cookie.getValue("userId",request);
//        buyId = 22; //模拟当前登陆的用户
        int flightId = 0;
        try {
            flightId = getInt("flightId");
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        if (flightId == 0) return "0";
        int re = buyTicketService.getSingleTicketNumById(buyId,flightId);
        String result = "0";
        try {
            result = String.valueOf(re);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return result;
    }

    //下载excel模板文件
    @RequestMapping("/downloadExcel")
    public void downloadTmpl(HttpServletRequest request,HttpServletResponse response){
        try {
            String fileName = "bookingtemplet.xlsx";
//            String path = request.getSession().getServletContext().getRealPath("/file")+"/"+fileName;
            String path = request.getSession().getServletContext().getRealPath(File.separator+"file")+File.separator+fileName;
//            path = path.replace("\\", "/");
            File file = new File(path);
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

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
