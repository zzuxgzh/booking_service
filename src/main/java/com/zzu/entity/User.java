package com.zzu.entity;

public class
User {

    private int userId;
    private String name;
    private String pwd;
    private String tel;
    private String IDCard;
    private int gender;
    private String company;
    private int status;

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public String getTel() {
        return tel;
    }

    public String getIDCard() {
        return IDCard;
    }

    public int getGender() {
        return gender;
    }

    public String getCompany() {
        return company;
    }

    public int getStatus() {
        return status;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", tel='" + tel + '\'' +
                ", IDCard='" + IDCard + '\'' +
                ", gender=" + gender +
                ", company='" + company + '\'' +
                ", status=" + status +
                '}';
    }
}
