package com.zzu.booking_manager.flight;

import com.zzu.booking_manager.flight.flightService.IFlightService;
import com.zzu.dao.IFlightDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightDao ifd;


}
