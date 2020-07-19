package com.zzu.booking_manager.flight;

import com.zzu.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/data/manager/flight/")
public class FlightController {

    @Autowired
    private FlightServiceImpl fsi;

    @ResponseBody
    @PostMapping("insertFlight")
    public String insertFlight(Flight flight){

        return null;
    }

}
