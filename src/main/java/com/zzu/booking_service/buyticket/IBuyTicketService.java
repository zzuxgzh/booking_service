package com.zzu.booking_service.buyticket;

import com.zzu.booking_service.bean.FlightAll;
import com.zzu.booking_service.bean.LocationCity;
import com.zzu.booking_service.bean.test.User;

import java.util.List;
import java.util.Map;

public interface IBuyTicketService {

    public List<LocationCity> getAllCity();


    public List<FlightAll> getFlightByCityTime(Map<String,Object> map);

}
