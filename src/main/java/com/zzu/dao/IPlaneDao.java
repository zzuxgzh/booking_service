package com.zzu.dao;

import com.zzu.entity.Plane;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IPlaneDao {
    Plane getPlaneById(@Param("id") int id);
    List<Plane> getAllPlanes();
    List<Plane> getSomePlane(@Param("param")String param);//模糊查询
    List<Plane> getPlanesByAirdrome(@Param("id") int id);//根据机场id获取飞机
    int insert(@Param("plane") Plane plane);
    int insertSelective(@Param("plane") Plane plane);
    int deleteById(@Param("id")int id);
    int update(@Param("plane")Plane plane);
    int updateSelective(@Param("plane")Plane plane);
}
