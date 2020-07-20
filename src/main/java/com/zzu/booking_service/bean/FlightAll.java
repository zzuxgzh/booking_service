package com.zzu.booking_service.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class FlightAll {

    private int id;
    private Timestamp starttime;
    private Timestamp preendtime;
    private int economyClass;
    private int businessClass;
    private int firstClass;
    private BigDecimal oprice;
    private String startname;
    private String endname;
    private String startcode;
    private String endcode;
    private String startpos;
    private String endpos;


    public FlightAll() {
    }

    @Override
    public String toString() {
        return "FlightAll{" +
                "id=" + id +
                ", starttime=" + starttime +
                ", preendtime=" + preendtime +
                ", economyClass=" + economyClass +
                ", businessClass=" + businessClass +
                ", firstClass=" + firstClass +
                ", oprice=" + oprice +
                ", startname='" + startname + '\'' +
                ", endname='" + endname + '\'' +
                ", startcode='" + startcode + '\'' +
                ", endcode='" + endcode + '\'' +
                ", startpos='" + startpos + '\'' +
                ", endpos='" + endpos + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStarttime() {
        return starttime;
    }

    public void setStarttime(Timestamp starttime) {
        this.starttime = starttime;
    }

    public Timestamp getPreendtime() {
        return preendtime;
    }

    public void setPreendtime(Timestamp preendtime) {
        this.preendtime = preendtime;
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

    public String getStartname() {
        return startname;
    }

    public void setStartname(String startname) {
        this.startname = startname;
    }

    public String getEndname() {
        return endname;
    }

    public void setEndname(String endname) {
        this.endname = endname;
    }

    public String getStartcode() {
        return startcode;
    }

    public void setStartcode(String startcode) {
        this.startcode = startcode;
    }

    public String getEndcode() {
        return endcode;
    }

    public void setEndcode(String endcode) {
        this.endcode = endcode;
    }

    public String getStartpos() {
        return startpos;
    }

    public void setStartpos(String startpos) {
        this.startpos = startpos;
    }

    public String getEndpos() {
        return endpos;
    }

    public void setEndpos(String endpos) {
        this.endpos = endpos;
    }
}
