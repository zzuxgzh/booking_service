package com.zzu.booking_manager.airdrome;

import com.zzu.booking_manager.airdrome.airdromeService.IAirdromeService;
import com.zzu.booking_manager.location.locationService.ILocationService;
import com.zzu.dao.IAirdromeDao;
import com.zzu.dao.ILocationDao;
import com.zzu.entity.Airdrome;
import com.zzu.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirdromeServiceImpl implements IAirdromeService {

    @Autowired
    private IAirdromeDao idd;

    @Autowired
    private ILocationDao ild;

    @Autowired
    private ILocationService ils;

    @Override
    public Airdrome getAirdromeById(int id) {
        return idd.getAirdromeById(id);
    }

    @Override
    public List<Airdrome> getAirByLocation(String location) {
        Location loc = ild.getLocationByRegionCode(location);
        List<Airdrome> transNodeList;
        int a = loc.getStage();
        if(a==1) {
            transNodeList = idd.getAirByLocation(location.substring(0,2)+"%");
        }else if(a==2) {
            transNodeList = idd.getAirByLocation(location.substring(0, 4)+"%");
        }else{
            transNodeList = idd.getAirByLocation(location);
        }
        for (Airdrome air:transNodeList) {
            air.setLocationName(ils.getFullNameById(air.getLocation()));
        }
        System.out.println("-----getAirByLocation---->"+transNodeList.toString());
        return transNodeList;
    }

    @Override
    public String insertAirdrome(Airdrome air) {
        int a = idd.insert(air);
        if(a==1){
            return "添加成功！！";
        }else {
            return "添加失败请重试！！";
        }
    }

    @Override
    public String updateAirdromeById(Airdrome air) {
        int a = idd.updateSelective(air);
        if(a==1){
            return "修改成功！！";
        }else {
            return "修改失败请重试！！";
        }
    }
}
