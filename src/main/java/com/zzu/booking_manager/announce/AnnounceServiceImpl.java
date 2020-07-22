package com.zzu.booking_manager.announce;

import com.zzu.booking_manager.announce.announceService.IAnnounceService;
import com.zzu.booking_manager.location.locationService.ILocationService;
import com.zzu.booking_manager.staff.staffservice.IStaffService;
import com.zzu.dao.IAnnounceDao;
import com.zzu.entity.Announce;
import com.zzu.entity.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnounceServiceImpl implements IAnnounceService {

    @Autowired
    private IAnnounceDao iad;

    @Autowired
    private ILocationService ils;
    @Autowired
    private IStaffService iss;

    @Override
    public Announce getAnnounceById(int id) {
        return iad.getAnnounceById(id);
    }

    @Override
    public List<Announce> getAllAnnounces() {
        return iad.getAllAnnounces();
    }

    @Override
    public List<Announce> getLimitAnnounces(int size) {
        return iad.getLimitAnnounces(size);
    }

    @Override
    public AnnOutParam getAnnById(int id) {
        AnnOutParam a=iad.getAnnById(id);
        a.setLocationName(ils.getFullNameById(a.getLocation()));
        Staff s=iss.getStaffById(a.getStafId());
        a.setStaffName(s.getName());
        return a;
    }

    @Override
    public List<AnnOutParam> getAllAnn() {
            List<AnnOutParam> annOutParams=iad.getAllAnn();
            for (AnnOutParam po:annOutParams) {
                po.setLocationName((ils.getFullNameById(po.getLocation())));
                Staff s=iss.getStaffById(po.getStafId());
                po.setStaffName(s.getName());
            }
            return annOutParams;
    }

    @Override
    public List<AnnOutParam> getAnnByAir(int id) {
        List<AnnOutParam> annOutParams=iad.getAnnByAir(id);
        for (AnnOutParam po:annOutParams) {
            po.setLocationName((ils.getFullNameById(po.getLocation())));
            Staff s=iss.getStaffById(po.getStafId());
            po.setStaffName(s.getName());
        }
        return annOutParams;
    }


    @Override
    public List<AnnOutParam> getLimitAnn(int size) {
        List<AnnOutParam> annOutParams=iad.getLimitAnn(size);
        for (AnnOutParam po:annOutParams) {
            po.setLocationName((ils.getFullNameById(po.getLocation())));
            Staff s=iss.getStaffById(po.getStafId());
            po.setStaffName(s.getName());
        }
        return annOutParams;
    }

    @Override
    public List<AnnOutParam> getSomeAnn(String param) {
        List<AnnOutParam> annOutParams=iad.getSomeAnn("%"+param+"%");
        for (AnnOutParam po:annOutParams) {
            po.setLocationName((ils.getFullNameById(po.getLocation())));
            Staff s=iss.getStaffById(po.getStafId());
            po.setStaffName(s.getName());
        }
        return annOutParams;
    }

    @Override
    public String insert(Announce anno) {
        int a=iad.insert(anno);
        if(a==1)
        {
            return "添加成功";
        }else{
            return "添加失败，请重新添加";
        }
    }
    @Override
    public String insertSelective(Announce anno) {
        int a=iad.insertSelective(anno);
        if(a==1)
        {
            return "添加成功";
        }else{
            return "添加失败，请重新添加";
        }
    }

    @Override
    public String deleteById(int id) {
        int a=iad.deleteById(id);
        if(a==1)
        {
            return "删除成功";
        }else{
            return "删除失败，请重新删除";
        }
    }

    @Override
    public String update(Announce anno) {
        int a=iad.update(anno);
        if(a==1)
        {
            return "修改成功成功";
        }else{
            return "修改失败，请重新修改";
        }
    }

    @Override
    public String updateSelective(Announce anno) {
        int a=iad.updateSelective(anno);
        if(a==1)
        {
            return "修改成功成功";
        }else{
            return "修改失败，请重新修改";
        }
    }
}
