package com.zzu.booking_manager.staff;

import com.zzu.booking_manager.staff.staffservice.IStaffService;
import com.zzu.dao.IStaffDao;
import com.zzu.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements IStaffService {
    @Autowired
    private IStaffDao isd;

    @Override
    public Staff doLogin(int id, String pwd) {
        Staff staff = null;
        staff = isd.getStaffById(id);
        System.out.println("----login---->"+staff.toString());
        if(staff==null) return null;
        if(staff.getPwd().equals(pwd)){
            return staff;
        }else{
            return null;
        }
    }
}
