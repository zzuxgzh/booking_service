package com.zzu.booking_service.buyticket;


import com.zzu.booking_service.bean.AnnounceShow;
import com.zzu.booking_service.bean.FlightAll;
import com.zzu.booking_service.bean.LocationCity;
import com.zzu.entity.User;
import org.apache.ibatis.annotations.Param;

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

}
