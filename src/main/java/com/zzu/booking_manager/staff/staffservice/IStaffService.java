package com.zzu.booking_manager.staff.staffservice;

import com.zzu.booking_manager.staff.StaffOutParam;
import com.zzu.entity.Staff;


import java.util.List;

public interface IStaffService {

    public Staff doLogin(int id,String pwd);
    public List<Staff> getAllAStaffs();
    String insertSelective(Staff staff);
    String updateSelective(Staff staff);
    String   deleteById(int id);
    public Staff getStaffById(int id);
    public List<Staff> getSomeStaff(String param);
    public List<StaffOutParam> getAllOutStaff();
    StaffOutParam getOutStaffById(int id);
    public List<StaffOutParam> getSomeOutStaff(String param);
}
