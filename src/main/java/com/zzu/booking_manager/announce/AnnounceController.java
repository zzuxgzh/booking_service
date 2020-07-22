package com.zzu.booking_manager.announce;

import com.zzu.booking_manager.announce.announceService.IAnnounceService;
import com.zzu.entity.Announce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/data/manager/ann/")
public class AnnounceController {
   @Autowired
    private IAnnounceService ias;

  @RequestMapping(value="insertAnnounce",method= RequestMethod.POST)
   @ResponseBody
    public String insertAnnounce(Announce announce,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date start){
        announce.setDate(start);
        System.out.println("----insertAnnounce--->"+announce.toString());
        return ias.insert(announce);
    }

    @ResponseBody
    @GetMapping("getAllAnnounce")
    public List<AnnOutParam> getAllAnnounce(){
        return ias.getAllAnn();
    }


    @GetMapping("getSomeAnnounce")
    @ResponseBody
    public List<AnnOutParam> getSomeAnnounce(String param){
        return ias.getSomeAnn(param);
    }


    @ResponseBody
    @GetMapping("getAllAnnByAir")
    public List<AnnOutParam> getAllAnnByAir(int id ){
        return ias.getAnnByAir(id);
    }

    @ResponseBody
    @RequestMapping(value="delectMoreAnn",method= RequestMethod.POST)
    public String delectMoreAnn(String id ){
//        System.out.println("批量删除");
        String[] a=id.split(",");
        int c=0;
//        System.out.println("批量删除1");
        for (int i = 0; i < a.length; i++) {
            String b=ias.deleteById(Integer.parseInt(a[i]));
            if(b.equals("删除成功")){
                c=1;
            }else{
                c=0;
            }
        }
        if(c==0){
            return"删除失败，请重新操作";
        }else{
            return "删除成功";
        }
    }

    @ResponseBody
    @GetMapping("delectById")
    public String delectById(int id ){
        return ias.deleteById(id);
    }



//    @ResponseBody
//    @GetMapping("changeFlightStatus")
//    public String changeFlightStatus(Flight flight,String reason){
//        System.out.println("------>"+flight.toString());
//        return ifs.changeFlightStatus(flight,reason);
//    }


}
