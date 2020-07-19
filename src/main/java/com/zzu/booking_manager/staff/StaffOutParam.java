package com.zzu.booking_manager.staff;

public class StaffOutParam {
    private int staffId;
    private String name;
    private String pwd;
    private int status;
    private int airdromeId;
    private String airdromeName;//机场名字
    private String location;//机场地址

    public String getAirdromeName() {
        return airdromeName;
    }

    public void setAirdromeName(String airdromeName) {
        this.airdromeName = airdromeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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
        return "StaffOutParam{" +
                "staffId=" + staffId +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", status=" + status +
                ", airdromeId=" + airdromeId +
                ", airdromeName='" + airdromeName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
