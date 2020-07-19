package com.zzu.booking_manager.announce;

import org.springframework.data.annotation.Transient;

import java.util.Date;

public class AnnOutParam {
    //数据库属性
    private int annId;
    private int stafId;
    private int dromId;
    private Date date;
    private String content;
    private int status;
    private String name;//机场名字，多表查询
    private String location;//location里面的regionCo，多表查询
    @Transient
    private String locationName;//location的全称，在service层补充
    @Transient
    private String staffName;//匹配stafId，在service层补充，如果ID为0，staffName=系统消息

    public int getAnnId() {
        return annId;
    }

    public void setAnnId(int annId) {
        this.annId = annId;
    }

    public int getStafId() {
        return stafId;
    }

    public void setStafId(int stafId) {
        this.stafId = stafId;
    }

    public int getDromId() {
        return dromId;
    }

    public void setDromId(int dromId) {
        this.dromId = dromId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    @Override
    public String toString() {
        return "AnnOutParam{" +
                "annId=" + annId +
                ", stafId=" + stafId +
                ", dromId=" + dromId +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", locationName='" + locationName + '\'' +
                ", staffName='" + staffName + '\'' +
                '}';
    }
}
