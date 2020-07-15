package com.zzu.entity;

public class Airdrome {
    private int airId;
    private String name;
    private String location;

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

    @Override
    public String toString() {
        return "Airdrome{" +
                "airId=" + airId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
