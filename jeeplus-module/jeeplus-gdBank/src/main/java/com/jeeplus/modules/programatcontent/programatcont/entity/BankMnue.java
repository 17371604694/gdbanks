package com.jeeplus.modules.programatcontent.programatcont.entity;

public class BankMnue {

    private String id;
    private String prantId;
    private String name;
    private String officeId;
    private Integer sort;
    public BankMnue() {
    }

    public BankMnue(String id, String prantId, String name) {
        this.id = id;
        this.prantId = prantId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "BankMnue{" +
                "id='" + id + '\'' +
                ", prantId='" + prantId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrantId() {
        return prantId;
    }

    public void setPrantId(String prantId) {
        this.prantId = prantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
