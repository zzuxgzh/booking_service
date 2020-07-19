package com.zzu.booking_manager.staff;

import com.zzu.booking_manager.staff.staffservice.IStaffService;
import com.zzu.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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


    @GetMapping("getStaffById")
    @ResponseBody
    public Staff  getStaffById(int id){
        return ss.getStaffById(id);
    }

    @GetMapping("getOutStaffById")
    @ResponseBody
    public StaffOutParam  getOutStaffById(int id){
        return ss.getOutStaffById(id);
    }

    @GetMapping("getAllStaff")
    @ResponseBody
    public List<Staff> getAllStaff(){
        List<Staff> aa = null;
        aa=ss.getAllAStaffs();
        return aa;
    }

    @GetMapping("getAllOutStaff")
    @ResponseBody
    public List<StaffOutParam> getAllOutStaff(){
        List<StaffOutParam> aa = null;
        aa=ss.getAllOutStaff();
        return aa;
    }



    @RequestMapping(value="insertStaff",method= RequestMethod.POST)
//    @GetMapping("insertStaff")
    @ResponseBody
    public String insertStaff(Staff staff){
        return ss.insertSelective(staff);
    }

    @RequestMapping(value="updateStaff",method= RequestMethod.POST)
//    @GetMapping("updateStaff")
    @ResponseBody
    public String  updateStaff(Staff staff){
        return ss.updateSelective(staff);
    }

    @RequestMapping(value = "deleteStaffById",method = RequestMethod.POST)
//
//    @GetMapping("deleteStaffById")
    @ResponseBody
    public String   deleteStaffById(int id){
       return ss.deleteById(id);
    }


    @ResponseBody
    @RequestMapping(value = "deleteMore",method = RequestMethod.POST)
    public String deleteMore(String id){

        String[] a=id.split(",");
        int flag=0;
        for (int i = 0; i < a.length; i++) {
            String b = ss.deleteById(Integer.parseInt(a[i]));
            if(b.equals("删除失败，请重新删除")){
                flag=0;
            }else{
                flag=1;
            }
        }
        if(flag==1){
            return "删除成功";
        }else{
            return "删除失败，请重新删除";
        }
    }

    @GetMapping("getSomeStaff")
    @ResponseBody
    public List<Staff> getSomeStaff(String str){
        List<Staff> aa = null;
        aa=ss.getSomeStaff(str);
        return aa;
    }

    @GetMapping("getSomeOutStaff")
    @ResponseBody
    public List<StaffOutParam> getSomeOutStaff(String str){
        List<StaffOutParam> aa = null;
        aa=ss.getSomeOutStaff(str);
        return aa;
    }



}
