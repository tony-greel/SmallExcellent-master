package com.enjoy.hyc.util;

import com.enjoy.hyc.bean.Job;
import com.enjoy.hyc.bean.JobCache;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 浏览记录工具类 Created by hyc on 2017/4/26 15:43
 */

public class JobCacheUtil {

    /**
     * 当用户浏览一个兼职工作详情后将产生一个浏览记录，对应个人界面中的足迹
     * @param job
     */
    public static void produceNewBrowse(Job job){
        JobCache jobCache=new JobCache(job);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String time =formatter.format(curDate);
        jobCache.setTime(time);
        jobCache.save();
    }

    /**
     * 删除所有足迹信息
     */
    public static void deleteAll(){
        DataSupport.deleteAll(JobCache.class);
    }
}
