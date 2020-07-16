package com.zzu.booking_manager.location;

import com.zzu.booking_manager.location.locationService.ILocationService;
import com.zzu.dao.ILocationDao;
import com.zzu.entity.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements ILocationService {

    @Autowired
    private ILocationDao ild;

    @Override
    public List<LocationParam> getLocationsTree() {
        List<Location> locations = ild.getAllLocations();
        List<LocationParam> locationParamList = new ArrayList<LocationParam>();
        for (Location l: locations) {
            LocationParam lp = new LocationParam();
            if(l.getStage()==1) {
                lp.setName(l.getPrv());
                lp.setpId("0");
                lp.setId(l.getRegionCode());
            }else if(l.getStage()==2) {
                lp.setName(l.getCty());
                lp.setpId(l.getRegionCode().substring(0, 2)+"0000");
                lp.setId(l.getRegionCode());
            }else if(l.getStage()==3) {
                lp.setName(l.getTwn());
                lp.setpId(l.getRegionCode().substring(0, 4)+"00");
                lp.setId(l.getRegionCode());
            }
            locationParamList.add(lp);
        }
        return locationParamList;
    }

    @Override
    public String getFullNameById(String id) {
        Location rg = getFullLocationById(id);
        StringBuffer sbname = new StringBuffer();
        if(rg.getStage()>0)
            sbname.append(rg.getPrv());
        if(rg.getStage()>1)
            sbname.append(" "+rg.getCty());
        if(rg.getStage()>2)
            sbname.append(" "+rg.getTwn());
        return sbname.toString();
    }

    @Override
    public Location getFullLocationById(String id) {
        String id_tmp = "";
        Location rg_0 = null;
        Location ret_rg = ild.getLocationByRegionCode(id);
        if(ret_rg.getStage() == 3){
            id_tmp = id.substring(0, 4)+"00";
            rg_0 = ild.getLocationByRegionCode(id_tmp);
            ret_rg.setCty(rg_0.getCty());
            id_tmp = id.substring(0, 2)+"0000";
            rg_0 = ild.getLocationByRegionCode(id_tmp);
            ret_rg.setPrv(rg_0.getPrv());
        }
        else if(ret_rg.getStage() == 2){
            id_tmp = id.substring(0, 2)+"0000";
            rg_0 = ild.getLocationByRegionCode(id_tmp);
            ret_rg.setPrv(rg_0.getPrv());
        }
        return ret_rg;
    }
}
