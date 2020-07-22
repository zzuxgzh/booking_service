package com.zzu.booking_service.ticket;

import com.zzu.booking_service.ticket.ticketservice.IticketService;
import com.zzu.entity.Flight;
import com.zzu.entity.Ticket;
import com.zzu.entity.TicketInfo;
import com.zzu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huan
 * @create 2020-07-19 10:36
 */
@Controller
@RequestMapping("/data/ticket")
public class TicketController {
    @Autowired
    IticketService iticketService;

    @PostMapping("/getTicketById")
    @ResponseBody
    public TicketInfo getTicketById(int id){
        return iticketService.getTicketById(id);
    }

    @PostMapping("/getTicketOfOne")    //  通过用户customer的id 获取机票ticket信息
    @ResponseBody
    public List<TicketInfo> getTicketOfOne(int customer){
        return iticketService.getTicketOfOne(customer);
    }

    @PostMapping("/getTicketByTel")    //  通过用户customer的id 获取机票ticket信息
    @ResponseBody
    public List<TicketInfo> getTicketByTel(String tel){
        return iticketService.getTicketByTel(tel);
    }

    @PostMapping("/printTicket")    //  取票，即修改机票的状态
    @ResponseBody
    public String printTicket(int id){
        return iticketService.printTicket(id);
    }

}

