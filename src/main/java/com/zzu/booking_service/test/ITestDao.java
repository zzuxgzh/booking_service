package com.zzu.booking_service.test;

import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.booking_service.bean.test.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ITestDao {

    public User getUser(@Param("id") String id);

    public List<User> getAllUser();

    public int insertUser(User user);

    //下面测试购票业务
    public int insertTicket(@Param("id") String id,@Param("name") String name,@Param("num") int num);

    public Ticket getTicket(@Param("id") String id);

    public int incTicketNum(@Param("id") String id);

    public int desTicketNum(@Param("id") String id);

    public int insertOrder(@Param("id") String id,@Param("testticketid") String testticketid,@Param("userid") String userid);

    public int getOrder(@Param("id") String id);

    public int buyTicketNo(@Param("id") String id,@Param("testticketid") String testticketid,@Param("userid") String userid);

}
