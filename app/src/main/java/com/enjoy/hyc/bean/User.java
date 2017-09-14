package com.enjoy.hyc.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by hyc on 2017/4/25 19:02
 */

public class User extends BmobUser {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private String age;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 身高
     */
    private int   height;
    /**
     * 所在学校
     */
    private String school;
    /**
     * 求职意向
     */
    private String objective;
    /**
     * 自我介绍
     */
    private String selfInfo;
    /**
     * 工作经验
     */
    private String workExperience;
    /**
     * QQ号
     */
    private String qqNumber;
    /**
     * 头像图片链接
     */
    private String headImageUrl;
    /**
     * 性别
     */
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getSelfInfo() {
        return selfInfo;
    }

    public void setSelfInfo(String selfInfo) {
        this.selfInfo = selfInfo;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getHeadImageUrl() {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        this.headImageUrl = headImageUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
