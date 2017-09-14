package com.enjoy.hyc.bean;

import cn.bmob.v3.BmobObject;

/**
 * 兼职合同类 Created by hyc on 2017/4/26 09:01
 */

public class Moonlighting extends BmobObject {
    /**
     * 对应的兼职工作
     */
    Job job;
    /**
     * 对应的报名用户
     */
    User user;

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
