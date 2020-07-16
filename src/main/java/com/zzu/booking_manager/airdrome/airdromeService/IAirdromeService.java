package com.zzu.booking_manager.airdrome.airdromeService;

import com.zzu.entity.Airdrome;

import java.util.List;

public interface IAirdromeService {
    public Airdrome getAirdromeById(int id);

    List<Airdrome> getAirByLocation(String location);

    String insertAirdrome(Airdrome air);

    String updateAirdromeById(Airdrome air);
}
