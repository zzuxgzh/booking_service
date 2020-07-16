package com.zzu.booking_manager.staff.staffservice;

import com.zzu.entity.Staff;

import java.util.List;

public interface IStaffService {

    public Staff doLogin(int id,String pwd);
    public List<Staff> getAllAStaffs();
}
