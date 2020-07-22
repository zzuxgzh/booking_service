package com.zzu.booking_manager.flight;

import com.zzu.booking_manager.flight.flightService.IFlightService;
import com.zzu.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/data/manager/flight/")
public class FlightController {

    @Autowired
    private IFlightService ifs;
    @Autowired FlightServiceImpl fsi;

    @ResponseBody
    @PostMapping("insertFlight")
    public String insertFlight(Flight flight,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss ") Date start,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date preend){
        flight.setStarttime(start);
        flight.setPreendtime(preend);
        System.out.println("----insertFlight--->"+flight.toString());
        return ifs.insertFlight(flight);
    }

    @ResponseBody
    @PostMapping("updateFlight")
    public String updateFlight(Flight flight,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss ") Date start,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date preend){
        flight.setStarttime(start);
        flight.setPreendtime(preend);
        System.out.println("----upFlight--->"+flight.toString());
        return ifs.update(flight);
    }

    @ResponseBody
    @GetMapping("getAllFlights")
    public List<FlightOutParam> getAllFlights(){
        return ifs.getAllFlights();
    }

    @ResponseBody
    @GetMapping("getAllFlightsByStatus")
    public List<FlightOutParam> getAllFlightsByStatus(int status,int sourceId){

        return fsi.getFlightByStatus(status,sourceId);
    }

    @ResponseBody
    @GetMapping("getSomeFli")
    public List<FlightOutParam> getSomeFli(String param) {

        return ifs.getSomeFli(param);
    }


    @ResponseBody
    @GetMapping("getSomeFli2")
    public List<FlightOutParam> getSomeFli2(String param) {

        return ifs.getSomeFli2(param);
    }

    @ResponseBody
    @GetMapping("getFlightsByAirdrome")
    public List<FlightOutParam> getFlightsByAirdrome(int airdromeID){
        return ifs.getFlightsByAirdrome(airdromeID);
    }

    @ResponseBody
    @GetMapping("changeFlightStatus")
    public String changeFlightStatus(Flight flight,String reason){
        System.out.println("------>"+flight.toString());
        return ifs.changeFlightStatus(flight,reason);
    }
}
