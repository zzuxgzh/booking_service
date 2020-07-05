package com.zzu.booking_service.test;

import com.zzu.booking_service.bean.test.User;

public interface ITestService {

    public User getUser(String id);

    public boolean insertUser(User user);

}
