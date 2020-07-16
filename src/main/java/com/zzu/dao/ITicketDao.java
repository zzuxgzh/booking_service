package com.zzu.dao;

import com.zzu.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ITicketDao {
    Ticket getTicketById(@Param("id")int id);
    List<Ticket> getAllTickets();
    List<Ticket> getSomeTicket(@Param("param")String param);//模糊查询
    int insert(@Param("ticket") Ticket ticket);
    int insertSelective(@Param("ticket") Ticket ticket);
    int deleteById(@Param("id")int id);
    int update(@Param("ticket")Ticket ticket);
    int updateSelective(@Param("ticket")Ticket ticket);
}
