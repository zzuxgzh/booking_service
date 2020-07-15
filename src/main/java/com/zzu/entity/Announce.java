package com.zzu.entity;

import java.util.Date;

public class Announce {
    private int annId;
    private int stafId;
    private int dromId;
    private Date date;
    private String content;
    private int status;

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

    @Override
    public String toString() {
        return "Announce{" +
                "annId=" + annId +
                ", stafId=" + stafId +
                ", dromId=" + dromId +
                ", date=" + date +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}
