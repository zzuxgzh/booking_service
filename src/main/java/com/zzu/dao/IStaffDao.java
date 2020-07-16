package com.zzu.dao;

import com.zzu.entity.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IStaffDao {
    Staff getStaffById(@Param("id") int id);
    List<Staff> getAllStaffs();
    List<Staff> getSomeStaff(@Param("param")String param);//模糊查询
    int insert(@Param("staff") Staff staff);
    int insertSelective(@Param("staff") Staff staff);
    int deleteById(@Param("id")int id);
    int update(@Param("staff")Staff staff);
    int updateSelective(@Param("staff")Staff staff);
}
