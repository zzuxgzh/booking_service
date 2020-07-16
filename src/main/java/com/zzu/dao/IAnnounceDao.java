package com.zzu.dao;

import com.zzu.entity.Announce;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAnnounceDao {
    Announce getAnnounceById(@Param("id") int id);
    List<Announce> getAllAnnounces();
    List<Announce> getLimitAnnounces(@Param("size") int size);
    int insert(@Param("anno") Announce anno);
    int insertSelective(@Param("anno") Announce anno);
    int deleteById(@Param("id")int id);
    int update(@Param("anno")Announce anno);
    int updateSelective(@Param("anno")Announce anno);
}
