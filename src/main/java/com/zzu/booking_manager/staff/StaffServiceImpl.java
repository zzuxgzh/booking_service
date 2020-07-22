package com.zzu.booking_manager.staff;

import com.zzu.booking_manager.location.locationService.ILocationService;
import com.zzu.booking_manager.staff.staffservice.IStaffService;
import com.zzu.dao.IStaffDao;
import com.zzu.entity.Staff;
import com.zzu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements IStaffService {
    @Autowired
    private IStaffDao isd;

    @Autowired
    private ILocationService ils;

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

    @Override
    public List<Staff> getAllAStaffs() {
        return isd.getAllStaffs();
    }

    @Override
    public String insertSelective(Staff staff) {
        int a= isd.insertSelective(staff);
        if (a==1){
            return "添加成功";
        }else {
            return "添加失败，请重新添加";
        }

    }

    @Override
    public String updateSelective(Staff staff) {
        int a=isd.updateSelective(staff);
        if (a==1){
            return "修改成功";
        }else {
            return "修改失败，请重新修改";
        }
    }

    @Override
    public String deleteById(int id) {
        int a= isd.deleteById(id);
        if (a==1){
            return "删除成功";
        }else {
            return "删除失败，请重新删除";
        }
    }

    @Override
    public Staff getStaffById(int id) {
        return isd.getStaffById(id);
    }

    @Override
    public List<Staff> getSomeStaff(String param) {

        return isd.getSomeStaff("%"+param+"%");
    }

    @Override
    public List<StaffOutParam> getAllOutStaff() {
        List<StaffOutParam> staffOutParams = isd.getAllOutStaff();
        for (StaffOutParam po:staffOutParams) {
            po.setLocation(ils.getFullNameById(po.getLocation()));
        }
        return staffOutParams;
    }

    @Override
    public StaffOutParam getOutStaffById(int id) {
        StaffOutParam a=isd.getOutStaffById(id);
        a.setLocation(ils.getFullNameById(a.getLocation()));
        return a;
    }

    @Override
    public List<StaffOutParam> getSomeOutStaff(String param) {
        List<StaffOutParam> staffOutParams = isd.getSomeOutStaff("%"+param+"%");
        for (StaffOutParam po:staffOutParams) {
            po.setLocation(ils.getFullNameById(po.getLocation()));
        }
        return staffOutParams;
    }
}
