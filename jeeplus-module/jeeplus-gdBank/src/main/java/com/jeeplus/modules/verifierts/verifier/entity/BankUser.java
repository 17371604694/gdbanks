package com.jeeplus.modules.verifierts.verifier.entity;

public class BankUser {

    private String uid;

    public BankUser() {
    }

    public BankUser(String uid, String uname, String uloginName) {
        this.uid = uid;
        this.uname = uname;
        this.uloginName = uloginName;
    }

    private String uname;
    private String uloginName;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUloginName() {
        return uloginName;
    }

    @Override
    public String toString() {
        return "BankUser{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", uloginName='" + uloginName + '\'' +
                '}';
    }

    public void setUloginName(String uloginName) {
        this.uloginName = uloginName;
    }
}
