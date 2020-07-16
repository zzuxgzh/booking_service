package com.zzu.booking_manager.staff.staffservice;

import com.zzu.entity.Staff;

public interface IStaffService {

    public Staff doLogin(int id,String pwd);
}
