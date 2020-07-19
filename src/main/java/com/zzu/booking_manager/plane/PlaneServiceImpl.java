package com.zzu.booking_manager.plane;

import com.zzu.booking_manager.location.locationService.ILocationService;
import com.zzu.booking_manager.plane.planeService.IPlaneService;
import com.zzu.dao.IPlaneDao;
import com.zzu.entity.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaneServiceImpl implements IPlaneService {

    @Autowired
    private IPlaneDao ipd;

    @Autowired
    private ILocationService ils;

    @Override
    public List<PlaneOutParam> getPlaneListByAirdrome(int airId) {
        List<PlaneOutParam> planeOutParams = ipd.getOutPlanesByAirdrome(airId);
        for (PlaneOutParam po:planeOutParams) {
            po.setLocationName(ils.getFullNameById(po.getLocation()));
        }
        return planeOutParams;
    }

    @Override
    public List<PlaneOutParam> getAllPlanes() {
        List<PlaneOutParam> planeOutParams = ipd.getAllOutPlanes();
        for (PlaneOutParam po:planeOutParams) {
            po.setLocationName(ils.getFullNameById(po.getLocation()));
        }
        return planeOutParams;
    }

    @Override
    public String insertPlane(Plane plane) {
        if(plane.getType()==0){
            plane.setEconomyClass(100);
            plane.setBussinessClass(10);
            plane.setFirstClass(5);
        }else if(plane.getType()==1){
            plane.setEconomyClass(130);
            plane.setBussinessClass(50);
            plane.setFirstClass(10);
        }else if(plane.getType()==2){
            plane.setEconomyClass(160);
            plane.setBussinessClass(70);
            plane.setFirstClass(60);
        }else{
            return "数据出错，请重新添加！！";
        }
        if(plane.getAirdromeId()!=0&&plane.getCompany()!=null&&!plane.getCompany().equals("")){
            if(ipd.insert(plane)==1){
                return "添加成功！！";
            }else{
                return "数据出错，请重新添加！！";
            }
        }else{
            return "数据出错，请重新添加！！";
        }
    }

    @Override
    public String deleteById(int id) {
        if(ipd.deleteById(id)==1){
            return "删除成功！！";
        }else {
            return "删除失败，请重试！！";
        }
    }

    @Override
    public String deletePlanes(int len, int[] msg) {
        for(int i=0;i<len;i++){
            int a =ipd.deleteById(msg[i]);
            if(a!=1){
                return "在删除第"+(i+1)+"个时出错，请重试！！";
            }
        }
        return "删除成功！！";
    }

    @Override
    public List<PlaneOutParam> getNoFlightPlaneListByAirdrome(int airId) {
        List<PlaneOutParam> planeOutParams = ipd.getNoFlightPlaneListByAirdrome(airId);
        for (PlaneOutParam po:planeOutParams) {
            po.setLocationName(ils.getFullNameById(po.getLocation()));
        }
        return planeOutParams;
    }
}
