package com.zzu.dao;

import com.zzu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserDao {
    User getUserById(@Param("id") int id);
    List<User> getAllUsers();
    List<User> getSomeUser(@Param("param")String param);//模糊查询
    int insert(User user);
    int insertSelective(User user);
    int deleteById(@Param("id")int id);
    int update(User user);
    int updateSelective(User user);

    User getUserByTel(@Param("tel") String tel);    // 根据电话号码查询用户
}
