package com.zzu.booking_manager.user.userservice;

import com.zzu.entity.User;

import java.util.List;

public interface IUserService {
    public List<User> getAllUsers();
    public User addUser(int id);


}
