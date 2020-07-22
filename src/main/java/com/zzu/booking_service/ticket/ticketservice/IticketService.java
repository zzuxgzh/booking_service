package com.zzu.booking_service.ticket.ticketservice;

import com.zzu.entity.*;

import java.util.List;

/**
 * @author huan
 * @create 2020-07-19 10:39
 */
public interface IticketService {

    public TicketInfo getTicketById(int id);    //根据机票id 查询机票详细信息

    public List<TicketInfo> getTicketOfOne(int customer);   // 根据 travel_agency 获取

    public List<TicketInfo> getTicketByTel(String tel);       // 根据电话号码查询用户拥有的机票

    public String getFullNameById(String id);      //

    public Location getFullLocationById(String id);

    public String printTicket(int id);      // 通过机票id 打印机票


}
