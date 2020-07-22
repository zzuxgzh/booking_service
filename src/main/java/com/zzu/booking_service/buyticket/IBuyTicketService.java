package com.zzu.booking_service.buyticket;


import com.zzu.booking_service.bean.AnnounceShow;
import com.zzu.booking_service.bean.FlightAll;
import com.zzu.booking_service.bean.LocationCity;
import com.zzu.entity.User;
import org.apache.ibatis.annotations.Param;

import javax.xml.soap.SAAJResult;
import java.util.List;
import java.util.Map;

public interface IBuyTicketService {

    public List<LocationCity> getAllCity();


    public List<FlightAll> getFlightByCityTime(Map<String,Object> map);

    public boolean intoSingleInfo(int buyId,User user);

    //通过登陆的用户id，获取当前已录入的信息
    public List<User> getInfo(int buyId);

    //通过时间范围，查找公告通知
    public List<AnnounceShow> selectAnnounce( String startTime,String endTime,String searchString);

    //清空用户已录入信息
    public boolean flushInfo(int buyId);


    //购票
    public boolean buyTicket(int buyId, int flightId, String kind);

    //判断该用户对于该订单共购买了几次票
    public int getSingleTicketNumById(int id,int flightId);

    // 0 不存在 1 存在但是-1 2 存在
    public int getStatusByMobile(String tel);

    // 0 失败 1 成功
    public int insertUserRegister(User user);

    // 0 失败 1 成功
    public int selectUserExist( String tel,String pwd);

    // null 没查到
    public User getUserByMobile(String tel);
}
