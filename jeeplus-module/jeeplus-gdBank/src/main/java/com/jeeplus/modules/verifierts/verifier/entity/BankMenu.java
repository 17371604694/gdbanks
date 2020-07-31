package com.jeeplus.modules.verifierts.verifier.entity;

public class BankMenu {

    private String id;
    private String parentId;
    private String pname;

    @Override
    public String toString() {
        return "BankMenu{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", pname='" + pname + '\'' +
                '}';
    }

    public BankMenu() {
    }

    public BankMenu(String id, String parentId, String pname) {
        this.id = id;
        this.parentId = parentId;
        this.pname = pname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
