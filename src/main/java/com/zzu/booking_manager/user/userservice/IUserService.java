package com.zzu.booking_manager.user.userservice;

import com.zzu.entity.User;

import java.util.List;

public interface IUserService {
    public List<User> getAllUsers();

    public User getUserById(int id);
    String insertSelective(User user);
    String deleteById(int id);
    String updateSelective(User user);
    public List<User> getSomeUser(String param);




}
