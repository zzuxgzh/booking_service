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
    int insert(Announce anno);
    int insertSelective(Announce anno);
    int deleteById(@Param("id")int id);
    int update(Announce anno);
    int updateSelective(Announce anno);
}
