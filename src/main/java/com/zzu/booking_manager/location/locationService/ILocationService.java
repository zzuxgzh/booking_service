package com.zzu.booking_manager.location.locationService;

import com.zzu.booking_manager.location.LocationParam;
import com.zzu.entity.Location;

import java.util.List;

public interface ILocationService {

    public List<LocationParam> getLocationsTree();

    public String getFullNameById(String id);

    public Location getFullLocationById(String id);
}
