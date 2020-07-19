package com.zzu.booking_manager.announce;

import com.zzu.booking_manager.announce.announceService.IAnnounceService;
import com.zzu.dao.IAnnounceDao;
import com.zzu.entity.Announce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnounceServiceImpl implements IAnnounceService {

    @Autowired
    private IAnnounceDao iad;

    @Override
    public int insertAnnounce(Announce announce) {
        return iad.insert(announce);
    }
}
