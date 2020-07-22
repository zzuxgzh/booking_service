package com.zzu.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huan
 * @create 2020-07-20 20:47
 */
//用来表示机票的详细信息
public class TicketInfo {
    private int ticketId;     // 机票ticket的id
    private String customer_name;     // 用户名，机票的拥有者
    private int flight;           // 飞机 id 即航班号
    private String source_name;           // 出发地点， name
    private String source_location;        // 出发地点location 全称
    private String target_name;         // 目的地name
    private String target_location;      // 目的地，location
    private Date strattime;       // 预计飞机起飞时间
    private Date preendtime;      //  预计到达时间
    private int status;          // 机票状态

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getFlight() {
        return flight;
    }

    public void setFlight(int flight) {
        this.flight = flight;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getTarget_name() {
        return target_name;
    }

    public void setTarget_name(String target_name) {
        this.target_name = target_name;
    }

    public Date getStrattime() {
        return strattime;
    }

    public void setStrattime(Date strattime) {
        this.strattime = strattime;
    }

    public Date getPreendtime() {
        return preendtime;
    }

    public void setPreendtime(Date preendtime) {
        this.preendtime = preendtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSource_location() {
        return source_location;
    }

    public void setSource_location(String source_location) {
        this.source_location = source_location;
    }

    public String getTarget_location() {
        return target_location;
    }

    public void setTarget_location(String target_location) {
        this.target_location = target_location;
    }

    @Override
    public String toString() {
        return "TicketInfo{" +
                "ticketId=" + ticketId +
                ", customer_name='" + customer_name + '\'' +
                ", flight=" + flight +
                ", source_name='" + source_name + '\'' +
                ", source_location='" + source_location + '\'' +
                ", target_name='" + target_name + '\'' +
                ", target_location='" + target_location + '\'' +
                ", strattime=" + strattime +
                ", preendtime=" + preendtime +
                ", status=" + status +
                '}';
    }
}
