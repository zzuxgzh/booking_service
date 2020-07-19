package com.zzu.booking_manager.flight;

import com.zzu.booking_manager.flight.flightService.IFlightService;
import com.zzu.dao.IAirdromeDao;
import com.zzu.dao.IAnnounceDao;
import com.zzu.dao.IFlightDao;
import com.zzu.dao.IPlaneDao;
import com.zzu.entity.Airdrome;
import com.zzu.entity.Announce;
import com.zzu.entity.Flight;
import com.zzu.entity.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightDao ifd;

    @Autowired
    private IPlaneDao ipd;

    @Autowired
    private IAnnounceDao iad;

    private IAirdromeDao idd;
    @Override
    public String insertFlight(Flight flight) {
        //校验数据 验证是否冲突
        if(flight.getSource()==flight.getTarget()){
            return "出发地和目的地不能一致！！";
        }
        if(flight.getStarttime().after(flight.getPreendtime())){
            return "预计到达时间和起飞时间冲突！！";
        }
        //完善数据
        Plane plane = ipd.getPlaneById(flight.getPlaneId());
        flight.setBusinessClass(plane.getBussinessClass());
        flight.setEconomyClass(plane.getEconomyClass());
        flight.setFirstClass(plane.getFirstClass());
        flight.setStatus(0);
        //添加航班
        if(ifd.insert(flight)!=1){
            return "添加失败！！";
        }
        //修改飞机状态
        plane.setStatus(1);
        if(1!=ipd.changePlaneStatus(plane)){
            return "系统出大错！！！请查看数据库";
        }
        //发布航班公告
        Announce announce = new Announce();
        Airdrome source = idd.getAirdromeById(flight.getSource());
        Airdrome target = idd.getAirdromeById(flight.getTarget());
        announce.setContent("编号为"+plane.getPlaneId()+"的飞机已经在"+source.getName()+"机场安排到" +
                target.getName()+"的新航班！！");
        announce.setDate(new Date());
        announce.setStafId(0);
        announce.setStatus(0);
        announce.setDromId(flight.getSource());
        if(iad.insert(announce)!=1){
            return "航班安排成功，但是公告发布失败，请补发公告！";
        }
        return "航班安排成功！！！";
    }
}
