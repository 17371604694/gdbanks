package com.jeeplus.modules.programatcontent.programatcont.entity;

/***
 * 通过查询bank_distribute_verifier表
 * 统计审核人审核情况
 */
public class StateChenck {

    private int sumP;   //栏目的审核人数
    private int sumPrant; //副栏目的审核人数
    private int sumY1; //栏目的通过人数
    private int sumY2; //副栏目的通过人数
    private int sumN1;  //栏目的驳回人数
    private int sumN2;  //副栏目的驳回人数

    public int getSumP() {
        return sumP;
    }

    public void setSumP(int sumP) {
        this.sumP = sumP;
    }

    public int getSumPrant() {
        return sumPrant;
    }

    public void setSumPrant(int sumPrant) {
        this.sumPrant = sumPrant;
    }

    public int getSumY1() {
        return sumY1;
    }

    public void setSumY1(int sumY1) {
        this.sumY1 = sumY1;
    }

    public int getSumY2() {
        return sumY2;
    }

    public void setSumY2(int sumY2) {
        this.sumY2 = sumY2;
    }

    public int getSumN1() {
        return sumN1;
    }

    public void setSumN1(int sumN1) {
        this.sumN1 = sumN1;
    }

    public int getSumN2() {
        return sumN2;
    }

    public void setSumN2(int sumN2) {
        this.sumN2 = sumN2;
    }
}
