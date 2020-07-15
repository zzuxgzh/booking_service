package com.zzu.entity;

import java.math.BigDecimal;

public class Ticket {
    private int ticketId;
    private int flight;
    private int customer;
    private int type;
    private BigDecimal price;
    private int travelAgency;
    private int status;

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
                '}';
    }
}
