package com.zzu.booking_manager.plane;

import org.springframework.data.annotation.Transient;

public class PlaneOutParam {
    private int planeId;
    private int type;
    private int economyClass;
    private int bussinessClass;
    private int firstClass;
    private String company;
    private int airdromeId;//当前所在机场
    private String name;//机场名字
    private String location;//机场地址
    @Transient
    private String locationName;//机场地址全称
    private int status;

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(int economyClass) {
        this.economyClass = economyClass;
    }

    public int getBussinessClass() {
        return bussinessClass;
    }

    public void setBussinessClass(int bussinessClass) {
        this.bussinessClass = bussinessClass;
    }

    public int getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(int firstClass) {
        this.firstClass = firstClass;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getAirdromeId() {
        return airdromeId;
    }

    public void setAirdromeId(int airdromeId) {
        this.airdromeId = airdromeId;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PlaneOutParam{" +
                "planeId=" + planeId +
                ", type=" + type +
                ", economyClass=" + economyClass +
                ", bussinessClass=" + bussinessClass +
                ", firstClass=" + firstClass +
                ", company='" + company + '\'' +
                ", airdromeId=" + airdromeId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", locationName='" + locationName + '\'' +
                ", status=" + status +
                '}';
    }
}
