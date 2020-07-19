package com.zzu.dao;

import com.zzu.booking_manager.announce.AnnOutParam;
import com.zzu.entity.Announce;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IAnnounceDao {
    //java后台使用，用这几个
    Announce getAnnounceById(@Param("id") int id);
    List<Announce> getAllAnnounces();
    List<Announce> getLimitAnnounces(@Param("size") int size);
    //返回前端用这几个
    AnnOutParam getAnnById(@Param("id") int id);
    List<AnnOutParam> getAllAnn();
    List<AnnOutParam> getLimitAnn(@Param("size") int size);
    int insert(Announce anno);
    int insertSelective(Announce anno);
    int deleteById(@Param("id")int id);
    int update(Announce anno);
    int updateSelective(Announce anno);
}
