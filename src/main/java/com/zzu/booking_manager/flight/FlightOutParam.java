package com.zzu.booking_manager.flight;

import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.Date;

public class FlightOutParam {
    private int flightId;//航班号
    private int planeId;//飞机编号
    private int source;//出发机场
    private int target;//目的机场
    private Date starttime;//起飞时间
    private Date preendtime;//预计到达时间
    private Date endtime;//实际到达时间
    private int status;//状态
    private int economyClass;//当前经济舱座位
    private int businessClass;//当前商务舱座位
    private int firstClass;//当前头等舱座位
    private BigDecimal oprice;//基础票价
    @Transient
    private String sName;//出发机场名字
    @Transient
    private String sloName;//出发机场地区
    @Transient
    private String tName;//目的机场名字
    @Transient
    private String tloName;//目的机场地区

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getPreendtime() {
        return preendtime;
    }

    public void setPreendtime(Date preendtime) {
        this.preendtime = preendtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(int economyClass) {
        this.economyClass = economyClass;
    }

    public int getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(int businessClass) {
        this.businessClass = businessClass;
    }

    public int getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(int firstClass) {
        this.firstClass = firstClass;
    }

    public BigDecimal getOprice() {
        return oprice;
    }

    public void setOprice(BigDecimal oprice) {
        this.oprice = oprice;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getSloName() {
        return sloName;
    }

    public void setSloName(String sloName) {
        this.sloName = sloName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getTloName() {
        return tloName;
    }

    public void setTloName(String tloName) {
        this.tloName = tloName;
    }

    @Override
    public String toString() {
        return "FlightOutParam{" +
                "flightId=" + flightId +
                ", planeId=" + planeId +
                ", source=" + source +
                ", target=" + target +
                ", starttime=" + starttime +
                ", preendtime=" + preendtime +
                ", endtime=" + endtime +
                ", status=" + status +
                ", economyClass=" + economyClass +
                ", businessClass=" + businessClass +
                ", firstClass=" + firstClass +
                ", oprice=" + oprice +
                ", sName='" + sName + '\'' +
                ", sloName='" + sloName + '\'' +
                ", tName='" + tName + '\'' +
                ", tloName='" + tloName + '\'' +
                '}';
    }
}
