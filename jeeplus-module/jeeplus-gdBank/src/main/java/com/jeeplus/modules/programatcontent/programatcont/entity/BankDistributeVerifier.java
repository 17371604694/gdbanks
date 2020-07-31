package com.jeeplus.modules.programatcontent.programatcont.entity;

public class BankDistributeVerifier {

    private int id;
    private String vid;  //审核人id
    private String vtime;
    private int vstate;  //审核状态
    private int vtype; //类型 栏目   或 副栏目
    private String distributeContentId; //distributeContent的id
    private String vname;

    public BankDistributeVerifier() {
    }

    public BankDistributeVerifier(int id, String vid, String vtime, int vstate, int vtype, String distributeContentId, String vname) {
        this.id = id;
        this.vid = vid;
        this.vtime = vtime;
        this.vstate = vstate;
        this.vtype = vtype;
        this.distributeContentId = distributeContentId;
        this.vname = vname;
    }

    @Override
    public String toString() {
        return "BankDistributeVerifier{" +
                "id=" + id +
                ", vid='" + vid + '\'' +
                ", vtime='" + vtime + '\'' +
                ", vstate=" + vstate +
                ", vtype=" + vtype +
                ", distributeContentId='" + distributeContentId + '\'' +
                ", vname='" + vname + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVtime() {
        return vtime;
    }

    public void setVtime(String vtime) {
        this.vtime = vtime;
    }

    public int getVstate() {
        return vstate;
    }

    public void setVstate(int vstate) {
        this.vstate = vstate;
    }

    public int getVtype() {
        return vtype;
    }

    public void setVtype(int vtype) {
        this.vtype = vtype;
    }

    public String getDistributeContentId() {
        return distributeContentId;
    }

    public void setDistributeContentId(String distributeContentId) {
        this.distributeContentId = distributeContentId;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }
}
