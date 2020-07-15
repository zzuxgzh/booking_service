package com.zzu.entity;

public class Staff {
    private int staffId;
    private String name;
    private String pwd;
    private int status;
    private int airdromeId;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAirdromeId() {
        return airdromeId;
    }

    public void setAirdromeId(int airdromeId) {
        this.airdromeId = airdromeId;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", status=" + status +
                ", airdromeId=" + airdromeId +
                '}';
    }
}
