package com.zzu.booking_service.buyticket;

import com.zzu.booking_service.bean.FlightAll;
import com.zzu.booking_service.bean.LocationCity;
import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.booking_service.bean.test.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IBuyTicketDao {

    public List<LocationCity> getAllCity();

    public List<FlightAll> getFlightByCityTime(Map<String,Object> map);

    public String getPro(@Param("code") String code);

    public String getCty(@Param("code") String code);

    public String getTwn(@Param("code") String code);

}
