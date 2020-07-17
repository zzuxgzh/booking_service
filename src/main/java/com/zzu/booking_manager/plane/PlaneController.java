package com.zzu.booking_manager.plane;

import com.zzu.booking_manager.plane.planeService.IPlaneService;
import com.zzu.entity.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/data/manager/plane/")
public class PlaneController {

    @Autowired
    private IPlaneService ips;

    @GetMapping("getPlaneListByAirdrome")
    @ResponseBody
    public List<PlaneOutParam> getPlaneListByAirdrome(int airId){
        return ips.getPlaneListByAirdrome(airId);
    }

    @GetMapping("getAllPlanes")
    @ResponseBody
    public List<PlaneOutParam> getAllPlanes(){
        return ips.getAllPlanes();
    }

    @PostMapping("insertPlane")
    @ResponseBody
    public String insertPlane(Plane plane){
        System.out.println("-----insertPlane----param->"+plane.toString());
        return ips.insertPlane(plane);
    }

    @GetMapping("deleteById")
    @ResponseBody
    public String deleteById(int id){
        return ips.deleteById(id);
    }

    @GetMapping("deletePlanes")
    @ResponseBody
    public String deletePlanes(int len,int[] msg){
        System.out.println("---deleteAirs----param----->"+msg.toString());
        return ips.deletePlanes(len,msg);
    }
}
