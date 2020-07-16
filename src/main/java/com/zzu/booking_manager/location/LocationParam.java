package com.zzu.booking_manager.location;

public class LocationParam {

    private String name;

    private String id;

    private String pId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "LocationParam{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                '}';
    }
}
