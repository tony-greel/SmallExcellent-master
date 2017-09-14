package com.enjoy.hyc.bean;

/**
 * 工资单信息类 Created by hyc on 2017/4/27 11:15
 */

public class Payroll {
    /**
     * 工作类型
     */
    private String type;
    /**
     * 工作日期
     */
    private String time;
    /**
     * 工作收入
     */
    private int income;
    /**
     * 工作地址
     */
    private String address;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
