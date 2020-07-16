package com.zzu.booking_manager.airdrome;

import com.zzu.booking_manager.airdrome.airdromeService.IAirdromeService;
import com.zzu.entity.Airdrome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/data/manager/airdrome")
public class AirdromeController {

    @Autowired
    private IAirdromeService ids;

    @GetMapping("/getAirById")
    @ResponseBody
    public Airdrome getAirdromeById(int id){
        return ids.getAirdromeById(id);
    }

    @GetMapping("/getAirByLocation")
    @ResponseBody
    public List<Airdrome> getAirByLocation(String location){
        return ids.getAirByLocation(location);
    }

    @RequestMapping(value = "insertAirdrome",method = RequestMethod.POST)
    @ResponseBody
    public String insertAirdrome(Airdrome air){
        System.out.println("---insertAirdrome----param----->"+air.toString());
        return ids.insertAirdrome(air);
    }

    @RequestMapping(value = "updateAirdromeById",method = RequestMethod.POST)
    @ResponseBody
    public String updateAirdromeById(Airdrome air){
        System.out.println("---updateAirdromeById----param----->"+air.toString());
        return ids.updateAirdromeById(air);
    }

    @GetMapping("deleteAirById")
    @ResponseBody
    public String deleteAirById(int id){
        System.out.println("---deleteAirById----param----->"+id);
        return null;
    }
}
