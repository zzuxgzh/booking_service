package com.zzu.booking_manager.announce.announceService;

import com.zzu.booking_manager.announce.AnnOutParam;
import com.zzu.entity.Announce;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IAnnounceService {
    Announce getAnnounceById(int id);
    List<Announce> getAllAnnounces();
    List<Announce> getLimitAnnounces( int size);
    //返回前端用这几个
    AnnOutParam getAnnById( int id);
    List<AnnOutParam> getAllAnn();
    List<AnnOutParam> getAnnByAir(int id);
    List<AnnOutParam> getLimitAnn( int size);
    List<AnnOutParam> getSomeAnn( String param);
    String  insert(Announce anno);
    String insertSelective(Announce anno);
    String deleteById(int id);
    String  update(Announce anno);
    String  updateSelective(Announce anno);
}
