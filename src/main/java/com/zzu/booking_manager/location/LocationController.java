package com.zzu.booking_manager.location;

import com.zzu.booking_manager.location.locationService.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/data/manager/location/")
public class LocationController {

    @Autowired
    private ILocationService ils;

    @GetMapping("getLocationsTree")
    @ResponseBody
    public List<LocationParam> getLocationsTree(){
        return ils.getLocationsTree();
    }
}
