package com.zzu.booking_service.buyticket;

import com.zzu.booking_service.bean.AnnounceShow;
import com.zzu.booking_service.bean.FlightAll;
import com.zzu.booking_service.bean.LocationCity;
import com.zzu.booking_service.bean.test.Ticket;
import com.zzu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface IBuyTicketDao {

    public List<LocationCity> getAllCity();

    public List<FlightAll> getFlightByCityTime(Map<String,Object> map);

    public String getPro(@Param("code") String code);

    public String getCty(@Param("code") String code);

    public String getTwn(@Param("code") String code);

    public User getUserByTel(@Param("tel") String tel);

    public String getIDCard(@Param("tel") String tel);

    public int insertUser(User user);

    public List<AnnounceShow> selectAnnounce(@Param("startTime") String startTime,@Param("endTime")String endTime,@Param("searchString")String searchString);

    public BigDecimal getPriceById(@Param("id") int id);

    public int getStatusById(@Param("id") int id);

    public int decFlightNum(@Param("kind") String kind,@Param("num") int num,@Param("flightId") int flightId);

    public int addFlightNum(@Param("kind") String kind,@Param("num") int num,@Param("flightId") int flightId);

    public int insetTicket(@Param("flightId") int flightId,@Param("buyUserId") int buyUserId,@Param("type") int type,@Param("price") BigDecimal price,@Param("userId") int userId,@Param("onTime") String onTime);

    public int getFlightNum(@Param("kind")String kind,@Param("id")int id);

    public int getSingleTicketNumById(@Param("id")int id,@Param("flightId")int flightId);


}
