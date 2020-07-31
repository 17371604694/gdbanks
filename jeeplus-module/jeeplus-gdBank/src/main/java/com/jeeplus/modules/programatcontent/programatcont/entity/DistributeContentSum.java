package com.jeeplus.modules.programatcontent.programatcont.entity;

/**
 * 定时统计数据表
 */
public class DistributeContentSum {

    private int id;
    private int type; //1,部门,2,活跃度,3,热度
    private int sum;   //数量
    private int nowMonth;  //当月1,历史2

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getNowMonth() {
        return nowMonth;
    }

    public void setNowMonth(int nowMonth) {
        this.nowMonth = nowMonth;
    }

    public String getOfficename() {
        return officename;
    }

    public void setOfficename(String officename) {
        this.officename = officename;
    }

    private String officename;  //部门名称


}
