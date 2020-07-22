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
    List<FlightOutParam> getFlightByStatus( int status,int source);
    List<FlightOutParam> getSomeFli( String param);
    List<FlightOutParam> getSomeFli2( String param);
    String update(Flight flight);

}
