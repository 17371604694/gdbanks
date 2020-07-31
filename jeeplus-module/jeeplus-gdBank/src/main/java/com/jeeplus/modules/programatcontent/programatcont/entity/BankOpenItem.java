package com.jeeplus.modules.programatcontent.programatcont.entity;


//是否在发稿的副栏目里显示的栏目

public class BankOpenItem {

    private String id;
    private String columnid;
    private String name;
    private Integer type;
    private Integer pageNo;
    private Integer pageSize;
    private Integer sort;

    @Override
    public String toString() {
        return "BankOpenItem{" +
                "columnid='" + columnid + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getColumnid() {
        return columnid;
    }

    public void setColumnid(String columnid) {
        this.columnid = columnid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
