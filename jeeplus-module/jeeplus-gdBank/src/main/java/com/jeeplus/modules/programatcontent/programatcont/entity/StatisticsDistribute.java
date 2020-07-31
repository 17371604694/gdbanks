package com.jeeplus.modules.programatcontent.programatcont.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 统计接受类
 */
public class StatisticsDistribute extends DataEntity<StatisticsDistribute> {

    private String officename;
    private String author;
    private String count;
    private String columnts;

    public String getOfficename() {
        return officename;
    }

    public void setOfficename(String officename) {
        this.officename = officename;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getColumnts() {
        return columnts;
    }

    public void setColumnts(String columnts) {
        this.columnts = columnts;
    }
}
