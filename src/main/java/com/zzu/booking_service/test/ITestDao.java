package com.zzu.booking_service.test;

import com.zzu.booking_service.bean.test.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ITestDao {

    public User getUser(@Param("id") String id);

    public int insertUser(User user);

}
