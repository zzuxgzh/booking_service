package com.zzu.booking_service.test;

import com.zzu.booking_service.bean.test.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService implements ITestService {

    @Autowired
    ITestDao testDao;

    @Override
    public User getUser(String id) {
        return testDao.getUser(id);
    }

    @Override
    public boolean insertUser(User user) {
        return testDao.insertUser(user) == 1;
    }
}
