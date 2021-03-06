package com.zzu.booking_service.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zzu.booking_service.bean.test.SingleUser;
import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.booking_service.bean.test.User;
import com.zzu.booking_service.utl.POIUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

//@CrossOrigin(allowCredentials ="true",allowedHeaders = "*")//设置是否允许客户端发送cookie信息。默认是false
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
    public String hello(HttpSession httpSession) {
        System.out.println(httpSession.getId());
        System.out.println(httpSession.getAttribute("yes"));
        httpSession.setAttribute("yes","1");
        return "test/index";
    }

    //返回数据 post方法
    @ResponseBody
    @GetMapping(value = "/insert")
    public String insert(HttpServletRequest request, HttpSession httpSession,String id,String  myAge) throws JsonProcessingException {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.print(cookie.getValue()+".");
        }

        System.out.println(request.getRequestedSessionId());
        System.out.println(httpSession.getId());
//        @RequestBody JsonNode jsonNode
//        String id = jsonNode.path("id").toString();
//        String myAge = jsonNode.path("myAge").toString();
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
    public String get(Model model,String id,HttpSession httpSession) {
        User user = testService.getUser(id);
        System.out.println(httpSession.getId());
        System.out.println(httpSession.getAttribute("yes"));
        httpSession.setAttribute("yes","1");
        model.addAttribute( "user", user);

        return "test/select";
    }

    //返回jsp渲染后的数据 post方法
    @PostMapping("/getSession")
    public String getSession(Model model,String id,HttpSession httpSession) {
        System.out.println(httpSession.getId());
        System.out.println(httpSession.getAttribute("yes"));
        httpSession.setAttribute("yes","1");
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

    //测试上传excel
    @ResponseBody
    @RequestMapping("/uploadExcel")
    public Map<String,Object> uploadExcel(@RequestParam(value = "file") MultipartFile excelFile, HttpServletRequest req, HttpServletResponse resp){
        Map<String, Object> param = new HashMap<String, Object>();
        List<SingleUser> list = new ArrayList<SingleUser>();
        try {
            List<String[]> rowList = POIUtils.readExcel(excelFile);
            for(int i=0;i<rowList.size();i++){
                String[] row = rowList.get(i);
                SingleUser info = new SingleUser();
                info.setName(row[0]);
                info.setAge(Integer.valueOf(row[1]));
                info.setMobile(row[2]);
                list.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
            param.put("dataStatus", 0);
            return param;
        }
        System.out.println(list.toString());
        ////////////后面写代码，给他们分别购票
        param.put("dataStatus", 1);
        return param;
    }

    //测试下载文件
    @RequestMapping("/downloadExcel")
    public void downloadTmpl(HttpServletRequest request,HttpServletResponse response){
     	try {
     		String fileName = "booking.xlsx";
    		String path = request.getSession().getServletContext().getRealPath("/file")+"/"+fileName;
    		path = path.replace("\\", "/");
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

    //测试cookie
    @ResponseBody
    @RequestMapping("/setCookie")
    public String setCookie(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie = new Cookie("onCode","123456");
        Cookie cookie1 = new Cookie("code","654321");

        //设置cookie 可访问的资源路径
        cookie.setPath(request.getContextPath()+"/data");
        cookie1.setPath(request.getContextPath()+"/data/test");

        //设置cookie 的有效期，值为一个整数值，单位为秒
        // >0 存放在客户端的硬盘
        // <=0  存储在浏览器的缓存中
        // 不设置 在 会话结束后关闭
        cookie.setMaxAge(60*60);    //1小时
        cookie1.setMaxAge(60);      //1分钟


        response.addCookie(cookie); //将cookie 加入 响应中，之后才能使用
        response.addCookie(cookie1);


        return "yes";
    }

    //测试cookie
    @ResponseBody
    @RequestMapping("/getCookie")
    public String getCookie(HttpServletRequest request,HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + "." + cookie.getValue());
            
        }

        return "yes";
    }

}
