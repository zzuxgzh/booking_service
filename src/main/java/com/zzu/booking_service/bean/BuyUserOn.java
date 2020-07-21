package com.zzu.booking_service.bean;

import java.math.BigDecimal;

public class BuyUserOn {

    private int id;

    private BigDecimal money;

    @Override
    public String toString() {
        return "BuyUserOn{" +
                "id=" + id +
                ", money=" + money +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
