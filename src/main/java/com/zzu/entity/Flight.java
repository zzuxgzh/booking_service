package com.zzu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Flight {
    private int flightId;
    private int planeId;
    private int source;
    private int target;
    private Date starttime;
    private Date preendtime;
    private Date endtime;
    private int status;
    private int economyClass;
    private int businessClass;
    private int firstClass;
    private BigDecimal oprice;

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

    @Override
    public String toString() {
        return "Flight{" +
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
                '}';
    }
}
