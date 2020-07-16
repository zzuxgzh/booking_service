package com.zzu.entity;

import org.springframework.data.annotation.Transient;

public class Airdrome {
    private int airId;
    private String name;
    private String location;
    private String tel;
    private int status;
    @Transient
    private String locationName;

    public int getAirId() {
        return airId;
    }

    public void setAirId(int airId) {
        this.airId = airId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String setLocationName) {
        this.locationName = setLocationName;
    }

    @Override
    public String toString() {
        return "Airdrome{" +
                "airId=" + airId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", tel='" + tel + '\'' +
                ", status=" + status +
                ", setLocationName='" + locationName + '\'' +
                '}';
    }
}
