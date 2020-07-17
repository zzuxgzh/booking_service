package com.zzu.dao;

import com.zzu.booking_manager.plane.PlaneOutParam;
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
    PlaneOutParam getOutPlaneById(@Param("id") int id);
    List<PlaneOutParam> getAllOutPlanes();
    List<PlaneOutParam> getOutPlanesByAirdrome(@Param("id") int id);//根据机场id获取飞机
    int insert(Plane plane);
    int insertSelective(Plane plane);
    int deleteById(@Param("id")int id);
    int update(Plane plane);
    int updateSelective(Plane plane);
}
