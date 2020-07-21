package com.zzu.booking_service.bean;

public class UserOn {

    private int id;

    private int flightId;

    private String kind;

    private int type;

    @Override
    public String toString() {
        return "UserOn{" +
                "id=" + id +
                ", flightId=" + flightId +
                ", kind='" + kind + '\'' +
                ", type=" + type +
                '}';
    }

    public UserOn() {
    }

    public UserOn(int id, int flightId, String kind, int type) {
        this.id = id;
        this.flightId = flightId;
        this.kind = kind;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
