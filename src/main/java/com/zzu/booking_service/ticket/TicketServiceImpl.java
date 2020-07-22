package com.zzu.booking_service.ticket;


import com.zzu.booking_service.ticket.ticketservice.IticketService;
import com.zzu.dao.*;
import com.zzu.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author huan
 * @create 2020-07-19 10:44
 */
@Service
public class TicketServiceImpl implements IticketService{
    @Autowired
    private ITicketDao ticketDao;
    @Autowired
    private IUserDao iUserDao;
    @Autowired
    private IFlightDao iFlightDao;
    @Autowired
    private IAirdromeDao iAirdromeDao;
    @Autowired
     private ILocationDao iLocationDao;


   @Override         // 通过订单id 获取订单详细信息
    public TicketInfo getTicketById(int id) {
        TicketInfo ticketInfo = new TicketInfo();
        Ticket ticket = new Ticket();
        ticket = ticketDao.getTicketById(id);

       User user = new User();           // 机票上的user 即 customer信息
       Flight flight = new Flight();      // 对应的flight航班信息
       Airdrome source_airdrome = new Airdrome();     // 机票上对应的出发机场
       Airdrome target_airdrome = new Airdrome();     // 机票上对应的目的机场

       Date date = new Date();
       Calendar calendar = Calendar.getInstance();

       ticketInfo.setTicketId(ticket.getTicketId());        // id
       user = iUserDao.getUserById(ticket.getCustomer());     // 机票上对应的用户信息
       ticketInfo.setCustomer_name(user.getName());            // 用户名，机票的拥有者
       ticketInfo.setFlight(ticket.getFlight());             // 飞机 id 即航班号
       flight = iFlightDao.getFlightById(ticket.getFlight());             //  机票上对应的flight

       source_airdrome = iAirdromeDao.getAirdromeById(flight.getSource());        // 机票上对应的出发机场source_airdrome信息

       ticketInfo.setSource_name(source_airdrome.getName());       // 出发地点， name
       ticketInfo.setSource_location(getFullNameById(source_airdrome.getLocation()));   // 机票上出发地的全称location
       target_airdrome = iAirdromeDao.getAirdromeById(flight.getTarget());         // 机票上对应的目的机场tartget_airdrome信息
       ticketInfo.setTarget_name(target_airdrome.getName());    // 目的地点,name
       ticketInfo.setTarget_location(getFullNameById(target_airdrome.getLocation())); // 机票上目的地的全称location

       // 转换时间类型，控制格式并消除时间差8小时
       // 预计飞机起飞时间  ，
       date = flight.getStarttime();
       calendar.setTime(date);
       calendar.add(Calendar.HOUR,-8);

       DateFormat format = DateFormat.getDateTimeInstance();//可以精确到时分秒
       ticketInfo.setStrattime(format.format(calendar.getTime()));

       // 预计飞机到达时间
       date = flight.getPreendtime();
       calendar.setTime(date);
       calendar.add(Calendar.HOUR,-8);
       ticketInfo.setPreendtime(format.format(calendar.getTime()));
       //
       ticketInfo.setStatus(ticket.getStatus());     // 飞机状态

        return ticketInfo;
    }

    @Override              // 根据用户traven_agency的id 查询 获取机票ticket信息
    public List<TicketInfo> getTicketOfOne(int customer) {
        List<Ticket> ticketList = ticketDao.getSomeTicket(customer);       // 数据库里的ticket信息，是tarve_agency
        //List<Ticket> ticketList = ticketDao.getTicketOfOne(customer);       // 数据库里的ticket信息
        List<TicketInfo> ticketInfoList = new ArrayList<>();              //  ticket的详细信息list
        User user = new User();           // 机票上的user 即 customer信息
        Flight flight = new Flight();      // 对应的flight航班信息
        Airdrome source_airdrome = new Airdrome();     // 机票上对应的出发机场
        Airdrome target_airdrome = new Airdrome();     // 机票上对应的目的机场

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
         /*
                private int ticketId;     // 机票ticket的id
                private String customer_name;     // 用户名，机票的拥有者
                private int flight;           // 飞机 id 即航班号
                private String source_name;           // 出发地点， name
                private String source_location;        // 出发地点location 全称
                private String target_name;         // 目的地name
                private String target_location;      // 目的地，location
                private Date strattime;       // 预计飞机起飞时间
                private Date preendtime;      //  预计到达时间
                private int status;          // 机票状态
                 status
            * */
        for (Ticket ticket:ticketList) {
            TicketInfo ticketInfo = new TicketInfo();
            //System.out.println();
            ticketInfo.setTicketId(ticket.getTicketId());        // id
            user = iUserDao.getUserById(ticket.getCustomer());     // 机票上对应的用户信息
            ticketInfo.setCustomer_name(user.getName());            // 用户名，机票的拥有者
            ticketInfo.setFlight(ticket.getFlight());             // 飞机 id 即航班号
            flight = iFlightDao.getFlightById(ticket.getFlight());             //  机票上对应的flight

            source_airdrome = iAirdromeDao.getAirdromeById(flight.getSource());        // 机票上对应的出发机场source_airdrome信息
            ticketInfo.setSource_name(source_airdrome.getName());       // 出发地点， name
            ticketInfo.setSource_location(getFullNameById(source_airdrome.getLocation()));   // 机票上出发地的全称location
            target_airdrome = iAirdromeDao.getAirdromeById(flight.getTarget());         // 机票上对应的目的机场tartget_airdrome信息
            ticketInfo.setTarget_name(target_airdrome.getName());    // 目的地点,name
            ticketInfo.setTarget_location(getFullNameById(target_airdrome.getLocation())); // 机票上目的地的全称location

            date = flight.getStarttime();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR,-8);

            DateFormat format = DateFormat.getDateTimeInstance();//可以精确到时分秒
            ticketInfo.setStrattime(format.format(calendar.getTime()));

            // 预计飞机到达时间
            date = flight.getPreendtime();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR,-8);
            ticketInfo.setPreendtime(format.format(calendar.getTime()));
            //
            ticketInfo.setStatus(ticket.getStatus());     // 飞机状态

            // 将机票详细信息存入list
            ticketInfoList.add(ticketInfo);
        }
        return ticketInfoList;
    }

    @Override           // 使用电话号码取票
    public List<TicketInfo> getTicketByTel(String tel) {
       User user = new User();      // 根据电话号码tel查出的user
       user = iUserDao.getUserByTel(tel);

       int customer=user.getUserId();
        List<Ticket> ticketList = ticketDao.getTicketByCus(customer);       // 数据库里的ticket信息
        List<TicketInfo> ticketInfoList = new ArrayList<>();              //  ticket的详细信息list
        //User user = new User();           // 机票上的user 即 customer信息
        Flight flight = new Flight();      // 对应的flight航班信息
        Airdrome source_airdrome = new Airdrome();     // 机票上对应的出发机场
        Airdrome target_airdrome = new Airdrome();     // 机票上对应的目的机场

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        //
        for (Ticket ticket:ticketList) {
            if(ticket.getStatus()!=0){  // 未出票的才能取票
                continue;           // 忽略其他状态的机票
            }
            //System.out.println("ticket:"+ticket);
            TicketInfo ticketInfo = new TicketInfo();
            //System.out.println();
            ticketInfo.setTicketId(ticket.getTicketId());        // id
            user = iUserDao.getUserById(ticket.getCustomer());     // 机票上对应的用户信息
            ticketInfo.setCustomer_name(user.getName());            // 用户名，机票的拥有者
            ticketInfo.setFlight(ticket.getFlight());             // 飞机 id 即航班号
            flight = iFlightDao.getFlightById(ticket.getFlight());             //  机票上对应的flight
            source_airdrome = iAirdromeDao.getAirdromeById(flight.getSource());        // 机票上对应的出发机场source_airdrome信息
            ticketInfo.setSource_name(source_airdrome.getName());       // 出发地点， name
            ticketInfo.setSource_location(getFullNameById(source_airdrome.getLocation()));   // 机票上出发地的全称location

            target_airdrome = iAirdromeDao.getAirdromeById(flight.getTarget());         // 机票上对应的目的机场tartget_airdrome信息

            ticketInfo.setTarget_name(target_airdrome.getName());    // 目的地点,name
            ticketInfo.setTarget_location(getFullNameById(target_airdrome.getLocation())); // 机票上目的地的全称location
            date = flight.getStarttime();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR,-8);

            DateFormat format = DateFormat.getDateTimeInstance();//可以精确到时分秒
            ticketInfo.setStrattime(format.format(calendar.getTime()));

            // 预计飞机到达时间
            date = flight.getPreendtime();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR,-8);
            ticketInfo.setPreendtime(format.format(calendar.getTime()));
            //
            ticketInfo.setStatus(ticket.getStatus());     // 飞机状态

            // 将机票详细信息存入list
            ticketInfoList.add(ticketInfo);
        }

        return ticketInfoList;
    }

    @Override                // 根据location的ReginCode获取全称
    public String getFullNameById(String id) {
        Location rg = getFullLocationById(id);
        StringBuffer sbname = new StringBuffer();
        if(rg.getStage()>0)
            sbname.append(rg.getPrv());
        if(rg.getStage()>1)
            sbname.append(" "+rg.getCty());
        if(rg.getStage()>2)
            sbname.append(" "+rg.getTwn());
        //System.out.println("全称："+sbname.toString());
        //System.out.println();
        return sbname.toString();
    }

    @Override
    public Location getFullLocationById(String id) {
        String id_tmp = "";
        Location rg_0 = null;
        Location ret_rg = iLocationDao.getLocationByRegionCode(id);
        if(ret_rg.getStage() == 3){
            id_tmp = id.substring(0, 4)+"00";
            rg_0 =  iLocationDao.getLocationByRegionCode(id_tmp);
            ret_rg.setCty(rg_0.getCty());
            id_tmp = id.substring(0, 2)+"0000";
            rg_0 =  iLocationDao.getLocationByRegionCode(id_tmp);
            ret_rg.setPrv(rg_0.getPrv());
        }
        else if(ret_rg.getStage() == 2){
            id_tmp = id.substring(0, 2)+"0000";
            rg_0 =  iLocationDao.getLocationByRegionCode(id_tmp);
            ret_rg.setPrv(rg_0.getPrv());
        }
        return ret_rg;
    }

    @Override
    public String printTicket(int id) {
        Ticket ticket = ticketDao.getTicketById(id);
        ticket.setStatus(1);      // 打印之后状态改为1，表示已出票
        ticketDao.update(ticket);
        return "打印成功，退出系统";
    }



}
