package com.zzu.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Ticket {
    private int ticketId;
    private int flight;
    private int customer;
    private int type;
    private BigDecimal price;
    private int travelAgency;
    private int status;
    private Date buyTime;

    public int getTicketId() {
        return ticketId;
    }

    public int getFlight() {
        return flight;
    }

    public int getCustomer() {
        return customer;
    }

    public int getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getTravelAgency() {
        return travelAgency;
    }

    public int getStatus() {
        return status;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public void setFlight(int flight) {
        this.flight = flight;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTravelAgency(int travelAgency) {
        this.travelAgency = travelAgency;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", flight=" + flight +
                ", customer=" + customer +
                ", type=" + type +
                ", price=" + price +
                ", travelAgency=" + travelAgency +
                ", status=" + status +
                ", buyTime=" + buyTime +
                '}';
    }
}
