package com.zzu.booking_service.test;

import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.booking_service.bean.test.User;

import java.util.List;

public interface ITestService {

    public User getUser(String id);

    public List<User> getAllUser();

    public boolean insertUser(User user);

    //下面测试购票业务

    public boolean creatTicket(String id,String name,int num);

    public Integer selectTicket(String id);

    public boolean buyTicketNo(String id,String testticketid,String userid) throws InterruptedException;

    public boolean buyTicket(String id,String testticketid,String userid);

    public boolean selectOrder(String id);

}
