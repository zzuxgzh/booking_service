package com.zzu.booking_service.bean;


public class LocationCity {

    private String regionCode;
    private String cty;

    public LocationCity(String regionCode, String cty) {
        this.regionCode = regionCode;
        this.cty = cty;
    }

    public LocationCity() {
    }

    @Override
    public String toString() {
        return "LocationCity{" +
                "regionCode='" + regionCode + '\'' +
                ", cty='" + cty + '\'' +
                '}';
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCty() {
        return cty;
    }

    public void setCty(String cty) {
        this.cty = cty;
    }
}
