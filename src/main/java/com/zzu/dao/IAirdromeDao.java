package com.zzu.dao;

import com.zzu.entity.Airdrome;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAirdromeDao {
    Airdrome getAirdromeById(@Param("id") int id);
    List<Airdrome> getAllAirdromes();
    List<Airdrome> getSomeAirdrome(@Param("param")String param);//模糊查询
    int insert(@Param("airdrome") Airdrome airdrome);
    int deleteById(@Param("id")int id);
    int update(@Param("airdrome")Airdrome airdrome);
    int updateSelective(@Param("airdrome")Airdrome airdrome);
}
