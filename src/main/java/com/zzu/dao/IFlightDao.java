package com.zzu.dao;

import com.zzu.entity.Flight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IFlightDao {
    Flight getFlightById(@Param("id") int id);
    List<Flight> getSourceFlightsByAirdome(@Param("source") int source);
    List<Flight> getTargetFlightsByAirdome(@Param("target") int target);
    int insert(@Param("flight") Flight flight);
    int deleteById(@Param("id")int id);
    int update(@Param("flight")Flight flight);
    int updateSelective(@Param("flight")Flight flight);
}
