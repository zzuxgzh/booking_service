package com.zzu.booking_manager.user;

import com.zzu.booking_manager.user.userservice.IUserService;
import com.zzu.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User addUser(int id) {
        return null;
    }
}
