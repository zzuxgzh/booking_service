package com.zzu.dao;

import com.zzu.entity.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ILocationDao {
    List<Location> getAllLocations();
    Location getLocationByRegionCode(@Param("regionCode")String regionCode);
    List<Location> getSomeLocationByRegionCode(@Param("regionCode")String regionCode);//模糊匹配所有的地区，service里面的查找所有省之类的用
    List<Location> getPrvList();
    List<Location> getCityListByPrv(@Param("regionCode")String regionCode);
    List<Location> getTownListByCity(@Param("regionCode")String regionCode);
}
