package com.zzu.booking_manager.flight.flightService;

import com.zzu.booking_manager.flight.FlightOutParam;
import com.zzu.entity.Flight;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IFlightService {
    String insertFlight(Flight flight);

    List<FlightOutParam> getAllFlights();

    List<FlightOutParam> getFlightsByAirdrome(int airdromeID);

    String changeFlightStatus(Flight flight, String reason);

    List<FlightOutParam> getSomeFli( int source,String param);
    List<FlightOutParam> getSomeFli2( int source,String param);
    String update(Flight flight);

    List<FlightOutParam> getFlightsByTime(int status,int airdromeID);

}
