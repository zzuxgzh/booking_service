package com.zzu.booking_manager.staff;

import com.zzu.booking_manager.staff.staffservice.IStaffService;
import com.zzu.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/data/manager/staff/")
public class StaffController {
    @Autowired
    private IStaffService ss;

    @GetMapping("doLogin")
    @ResponseBody
    public Staff doLogin(int staffId, String pwd){
        return ss.doLogin(staffId,pwd);
    }
}
