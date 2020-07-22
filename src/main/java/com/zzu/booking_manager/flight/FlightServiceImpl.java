package com.zzu.booking_manager.flight;

import com.zzu.booking_manager.flight.flightService.IFlightService;
import com.zzu.booking_manager.location.locationService.ILocationService;
import com.zzu.dao.IAirdromeDao;
import com.zzu.dao.IAnnounceDao;
import com.zzu.dao.IFlightDao;
import com.zzu.dao.IPlaneDao;
import com.zzu.entity.Airdrome;
import com.zzu.entity.Announce;
import com.zzu.entity.Flight;
import com.zzu.entity.Plane;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightDao ifd;

    @Autowired
    private IPlaneDao ipd;

    @Autowired
    private IAnnounceDao iad;

    @Autowired
    private IAirdromeDao idd;

    @Autowired
    private ILocationService ils;

    @Override
    public String insertFlight(Flight flight) {
        //校验数据 验证是否冲突
        if (flight.getSource() == flight.getTarget()) {
            return "出发地和目的地不能一致！！";
        }
        if (flight.getStarttime() == null || flight.getPreendtime() == null) {
            return "预计到达时间和起飞时间不能为空！！";
        }
        if (flight.getStarttime().after(flight.getPreendtime())) {
            return "预计到达时间和起飞时间冲突！！";
        }
        //完善数据
        Plane plane = ipd.getPlaneById(flight.getPlaneId());
        flight.setBusinessClass(plane.getBussinessClass());
        flight.setEconomyClass(plane.getEconomyClass());
        flight.setFirstClass(plane.getFirstClass());
        flight.setStatus(0);
        //添加航班
        if (ifd.insert(flight) != 1) {
            return "添加失败！！";
        }
        //修改飞机状态
        plane.setStatus(1);
        if (1 != ipd.changePlaneStatus(plane)) {
            return "系统出大错！！！请查看数据库";
        }
        //发布航班公告
        Announce announce = new Announce();
        Airdrome source = idd.getAirdromeById(flight.getSource());
        Airdrome target = idd.getAirdromeById(flight.getTarget());
        announce.setContent("编号为" + plane.getPlaneId() + "的飞机已经在" + source.getName() + "安排到" +
                target.getName() + "的新航班！！");
        announce.setDate(new Date());
        announce.setStafId(0);
        announce.setStatus(0);
        announce.setDromId(flight.getSource());
        if (iad.insert(announce) != 1) {
            return "航班安排成功，但是公告发布失败，请补发公告！";
        }
        return "航班安排成功！！！";
    }

    @Override
    public List<FlightOutParam> getAllFlights() {
        List<FlightOutParam> flightOutParams = ifd.getAllFli();
        for (FlightOutParam flightOutParam : flightOutParams) {

//            isstatus(ifd.getFlightById(flightOutParam.getFlightId()));
            Airdrome source = idd.getAirdromeById(flightOutParam.getSource());
            Airdrome target = idd.getAirdromeById(flightOutParam.getTarget());
            flightOutParam.setsName(source.getName());
            flightOutParam.setSloName(ils.getFullNameById(source.getLocation()));
            flightOutParam.settName(target.getName());
            flightOutParam.setTloName(ils.getFullNameById(target.getLocation()));

        }
        return flightOutParams;
    }

    @Override
    public List<FlightOutParam> getFlightsByAirdrome(int airdromeID) {
        List<FlightOutParam> flightOutParams = ifd.getSourceFlisByAirdome(airdromeID);
        flightOutParams.addAll(ifd.getTargetFlisByAirdome(airdromeID));
        for (FlightOutParam flightOutParam : flightOutParams) {
            Airdrome source = idd.getAirdromeById(flightOutParam.getSource());
            Airdrome target = idd.getAirdromeById(flightOutParam.getTarget());
            flightOutParam.setsName(source.getName());
            flightOutParam.setSloName(ils.getFullNameById(source.getLocation()));
            flightOutParam.settName(target.getName());
            flightOutParam.setTloName(ils.getFullNameById(target.getLocation()));
        }
        System.out.println("------>" + flightOutParams.toString());
        return flightOutParams;
    }


    @Override
    public String changeFlightStatus(Flight flight, String reason) {
        int a = flight.getStatus();
        Flight sflight = ifd.getFlightById(flight.getFlightId());
        int b = sflight.getStatus();
//        if(b==0){
//            if(sflight.getStarttime().after(new Date()));
//
//
//        }

        if (sflight == null) {
            return "航班不存在！！";
        }
        if (a == 0) {//错误
            return "您的操作有误，请重试！！";
        } else if (a == 1) {
            //记得校验当前航班状态和飞机状态 起飞，修改飞机的状态和航班的状态 发布公告
            if (b == 0) {
                if (ifd.changeFlightStatusById(flight) == 1) {
                    Plane plane = new Plane();
                    plane.setPlaneId(sflight.getPlaneId());
                    plane.setStatus(2);
                    if (1 == ipd.changePlaneStatus(plane)) {
                        Announce announce = new Announce();
                        Airdrome source = idd.getAirdromeById(sflight.getSource());
                        Airdrome target = idd.getAirdromeById(sflight.getTarget());
                        announce.setContent("从" + source.getName() + "飞往" + target.getName() + "的" + sflight.getPlaneId() + "号飞机" +
                                "即将起飞，请抓紧办理登机手续！！！");
                        announce.setDate(new Date());
                        announce.setStafId(0);
                        announce.setStatus(0);
                        announce.setDromId(sflight.getSource());
                        if (iad.insert(announce) != 1) {
                            return "操作成功，但是公告发布失败，请补发公告！";
                        } else {
                            return "操作成功！！！！";
                        }
                    } else {
                        return "操作故障，请重试！！！";
                    }
                } else {
                    return "操作失败，请重试！！！";
                }
            } else if (b == 1) {
                return "当前飞机以起飞，不能进行该操作";
            } else if (b == 2) {
                return "当前飞机已经到达，不能进行该操作";
            } else if (b == 4) {
                return "当前飞机已经取消，不能进行该操作";
            }

        } else if (a == 2) {
            //记得校验当前航班状态和飞机状态 到达，修改飞机的状态和location和航班状态
            if (b == 1) {
                if (ifd.changeFlightStatusById(flight) == 1) {
                    Plane plane = new Plane();
                    plane.setStatus(0);
                    plane.setPlaneId(sflight.getPlaneId());
                    plane.setAirdromeId(sflight.getTarget());
                    plane.setType(10);
                    if (ipd.updateSelective(plane) == 1) {
                        return "操作成功！！！！";
                    } else {
                        return "操作故障，请重试！！！";
                    }
                } else {
                    return "操作失败，请重试！！！";
                }
            } else if (b == 0) {
                return "飞机未起飞，不能进行该操作";
            } else if (b == 2) {
                return "飞机已经到达，不能进行该操作";
            } else if (b == 3) {
                return "飞机延误，不能进行该操作";
            } else if (b == 4) {
                return "飞机已经取消，不能进行该操作";
            }
        }
//        else if(a==3){//记得校验当前航班状态和飞机状态 延误，修改航班状态，发布公告
//            if(b==0){
//                if()
//
//            }else if(b==1){
//                return "飞机已经出发，不能进行该操作";
//            }else if(b==2){
//                return "飞机已经到达，不能进行该操作";
//            }else if(b==3){
//                return "飞机已经延误，不能进行该操作";
//            }else if(b==4){
//                return "飞机已经取消，不能进行该操作";
//            }

//        }
        else if (a == 4) {
            //记得校验当前航班状态和飞机状态 取消，修改飞机状态为未安排，修改航班状态，发布公告
            if (b == 0) {
                if (reason == null || reason.equals("") || reason.equals(" ")) {
                    return "请输入航班取消原因后再取消！！";
                }
                if (1 == ifd.changeFlightStatusById(flight)) {
                    Plane plane = new Plane();
                    plane.setPlaneId(sflight.getPlaneId());
                    plane.setStatus(0);
                    if (1 == ipd.changePlaneStatus(plane)) {
                        Announce announce = new Announce();
                        Airdrome source = idd.getAirdromeById(sflight.getSource());
                        Airdrome target = idd.getAirdromeById(sflight.getTarget());
                        announce.setContent("从" + source.getName() + "飞往" + target.getName() + "的" + sflight.getPlaneId() + "号飞机" +
                                "因为" + reason + "已取消航班，请旅客及时换乘！！");
                        announce.setDate(new Date());
                        announce.setStafId(0);
                        announce.setStatus(0);
                        announce.setDromId(sflight.getSource());
                        if (iad.insert(announce) != 1) {
                            return "操作成功，但是公告发布失败，请补发公告！";
                        } else {
                            return "操作成功！！！！";
                        }
                    } else {
                        return "操作故障，请重试！！！";
                    }
                } else {
                    return "操作失败，请重试！！！";
                }
            } else if (b == 1) {
                return "飞机已经出发，不能进行该操作";
            } else if (b == 2) {
                return "飞机已经到达，不能进行该操作";
            } else if (b == 3) {
                return "飞机已经延误，不能进行该操作";
            } else if (b == 4) {
                return "飞机已经取消，不能进行该操作";
            }
        }
        return null;
    }


    @Override
    public List<FlightOutParam> getFlightByStatus(int status, int sourceId) {

        List<FlightOutParam> flightOutParams = ifd.getFlightByStatus(status, sourceId);
        flightOutParams.addAll(ifd.getTargetFlisByAirdome(sourceId));
        for (FlightOutParam flightOutParam : flightOutParams) {
//            isstatus(ifd.getFlightById(flightOutParam.getFlightId()));
            Airdrome source = idd.getAirdromeById(flightOutParam.getSource());
            Airdrome target = idd.getAirdromeById(flightOutParam.getTarget());
            flightOutParam.setsName(source.getName());
            flightOutParam.setSloName(ils.getFullNameById(source.getLocation()));
            flightOutParam.settName(target.getName());
            flightOutParam.setTloName(ils.getFullNameById(target.getLocation()));

        }
        return flightOutParams;
    }

    @Override
    public List<FlightOutParam> getSomeFli(String param) {
        List<FlightOutParam> flightOutParams = ifd.getSomeFli("%" + param + "%");
        for (FlightOutParam flightOutParam : flightOutParams) {
//            isstatus(ifd.getFlightById(flightOutParam.getFlightId()));
            Airdrome source = idd.getAirdromeById(flightOutParam.getSource());
            Airdrome target = idd.getAirdromeById(flightOutParam.getTarget());
            flightOutParam.setsName(source.getName());
            flightOutParam.setSloName(ils.getFullNameById(source.getLocation()));
            flightOutParam.settName(target.getName());
            flightOutParam.setTloName(ils.getFullNameById(target.getLocation()));

        }
        return flightOutParams;

    }

    @Override
    public List<FlightOutParam> getSomeFli2(String param) {
        List<FlightOutParam> flightOutParams = ifd.getSomeFli2("%" + param + "%");


        for (FlightOutParam flightOutParam : flightOutParams) {
//            isstatus(ifd.getFlightById(flightOutParam.getFlightId()));
            Airdrome source = idd.getAirdromeById(flightOutParam.getSource());
            Airdrome target = idd.getAirdromeById(flightOutParam.getTarget());
            flightOutParam.setsName(source.getName());
            flightOutParam.setSloName(ils.getFullNameById(source.getLocation()));
            flightOutParam.settName(target.getName());
            flightOutParam.setTloName(ils.getFullNameById(target.getLocation()));

        }
        return flightOutParams;
    }

    @Override
    public String update(Flight flight) {
        if(ifd.update(flight)==1){
            return "修改成功";
        }else{
            return "修改失败，请从新操作";
        }
    }


    boolean isstatus(Flight flight){
        boolean flag=false;
        int b=flight.getStatus();
        if(b==0&&flight.getStarttime().before(new Date())){
            flight.setStatus(3);
            if(  1==ifd.changeFlightStatusById(flight)){
                flag=true;
            }
        }else{
            flag= false;
        }
        return flag;
    }


}
