package com.zzu.entity;

import java.math.BigDecimal;

public class Plane {
    private int planeId;
    private int type;
    private int economyClass;
    private int bussinessClass;
    private int firstClass;
    private String company;
    private int airdromeId;//当前所在机场
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "planeId=" + planeId +
                ", type=" + type +
                ", economyClass=" + economyClass +
                ", bussinessClass=" + bussinessClass +
                ", firstClass=" + firstClass +
                ", company='" + company + '\'' +
                ", airdromeId=" + airdromeId +
                ", status=" + status +
                '}';
    }
}
