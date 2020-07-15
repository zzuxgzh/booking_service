package com.zzu.entity;

public class Location {
    private String RegionCode;
    private String Prv;
    private String cty;
    private String twn;
    private int stage;

    public String getRegionCode() {
        return RegionCode;
    }

    public void setRegionCode(String regionCode) {
        RegionCode = regionCode;
    }

    public String getPrv() {
        return Prv;
    }

    public void setPrv(String prv) {
        Prv = prv;
    }

    public String getCty() {
        return cty;
    }

    public void setCty(String cty) {
        this.cty = cty;
    }

    public String getTwn() {
        return twn;
    }

    public void setTwn(String twn) {
        this.twn = twn;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "Location{" +
                "RegionCode='" + RegionCode + '\'' +
                ", Prv='" + Prv + '\'' +
                ", cty='" + cty + '\'' +
                ", twn='" + twn + '\'' +
                ", stage=" + stage +
                '}';
    }
}
