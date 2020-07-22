package com.zzu.dao;

import com.zzu.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ITicketDao {
    Ticket getTicketById(@Param("id")int id);
    List<Ticket> getAllTickets();
    List<Ticket> getSomeTicket(@Param("param") int param);//模糊查询
    int insert(Ticket ticket);
    int insertSelective(Ticket ticket);
    int deleteById(@Param("id")int id);
    int update(Ticket ticket);
    int updateSelective(Ticket ticket);

    List<Ticket> getTicketByCus(@Param("customer") int customer);  // 使用customer查询
}
