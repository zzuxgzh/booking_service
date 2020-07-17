package com.zzu.booking_manager.plane.planeService;

import com.zzu.booking_manager.plane.PlaneOutParam;
import com.zzu.entity.Plane;

import java.util.List;

public interface IPlaneService {

    List<PlaneOutParam> getPlaneListByAirdrome(int airId);

    List<PlaneOutParam> getAllPlanes();

    String insertPlane(Plane plane);

    String deleteById(int id);

    String deletePlanes(int len, int[] msg);
}
