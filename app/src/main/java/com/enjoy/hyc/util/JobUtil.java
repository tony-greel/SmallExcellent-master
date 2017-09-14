package com.enjoy.hyc.util;

import android.text.TextUtils;

import com.enjoy.base.LogUtils;
import com.enjoy.hyc.bean.Job;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 工作工具类 Created by hyc on 2017/4/21 19:38
 */

public class JobUtil {
    /**
     * 发布新兼职信息时的监听
     */
    public interface OnPublishNewJobListener{
        void success(String objectId);
        void fail(String error);
    }
    /**
     * 获取兼职信息时的监听
     */
    public interface OnGetJobInfoListener{
        void success(List<Job> jobs);
        void fail(String error);
    }
    /**
     * 删除兼职信息时的监听
     */
    public interface OnDeleteJobListener{
        void success();
        void fail(String error);
    }

    /**
     * 通过属性查询兼职信息时的监听
     */
    public interface OnQueryJobsListener{
        void success(List<Job> jobs);
        void fail(String error);
    }

    public class JobQuery{
        private String type="";
        private String salary="";
        private String time="";
        private String city="";
        public String getCity(){
            return city;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setCity(String city) {
            this.city = city;
        }


        public void query(OnQueryJobsListener listener){
            int minSalary;
            int maxSalary;
            String mode;
            if (!TextUtils.isEmpty(salary)){
                if (salary.split("-").length==3){
                    maxSalary=Integer.parseInt(salary.split("-")[2]);
                    minSalary=Integer.parseInt(salary.split("-")[1]);
                    mode=salary.split("-")[0];
                }else {
                    mode=salary.split("-")[0];
                    maxSalary=-1;
                    minSalary=salary.split("-").length==1?
                            -1:Integer.parseInt(salary.split("-")[1].split("以")[0]);
                }
            }else {
                minSalary=-1;
                maxSalary=-1;
                mode="";
            }
            int minTime=-1;
            int maxTime=-1;
            if (!TextUtils.isEmpty(time)){
                LogUtils.log(time);
                maxTime=time.indexOf("以上")!=-1?-1:Integer.parseInt(time.split("-")[1].split("天")[0]);
                minTime=time.indexOf("以上")!=-1?60:Integer.parseInt(time.split("-")[0]);
            }
            queryJobInfoByAttr(type,minSalary,maxSalary,mode,city,minTime,maxTime,listener);
        }
    }

    /**
     * 发布新兼职信息
     * @param job      需要发布的新兼职信息
     * @param listener 发布监听
     */
    public static void publishNewJob(Job job, final OnPublishNewJobListener listener){
        job.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    listener.success(s);
                }else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 通过城市名获取同城兼职信息
     * @param cityName 当前城市名
     * @param listener 查询监听
     */
    public static void getJobInfoByType(String cityName, final OnGetJobInfoListener listener){
        BmobQuery<Job> query=new BmobQuery<>();
        query.addWhereEqualTo("jobType",cityName);
        query.setLimit(50);
        query.findObjects(new FindListener<Job>() {
            @Override
            public void done(List<Job> list, BmobException e) {
                if (e==null){
                    listener.success(list);
                }else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    public static void getJobInfoByCityName(String cityName, final OnGetJobInfoListener listener){
        BmobQuery<Job> query=new BmobQuery<>();
        query.addWhereEqualTo("cityName",cityName);
        query.setLimit(50);
        query.findObjects(new FindListener<Job>() {
            @Override
            public void done(List<Job> list, BmobException e) {
                if (e==null){
                    listener.success(list);
                }else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 删除发布过得兼职信息
     * @param job      需要删除的兼职信息
     * @param listener 删除监听
     */
    public void deletePublishedJob(Job job, final OnDeleteJobListener listener){
        job.delete(job.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    listener.success();
                }else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 通过复合条件查询兼职信息
     * @param jobType       兼职工作类型
     * @param minJobSalary  最少薪资
     * @param maxJobSalary  最大薪资
     * @param balanceMode   薪资结算方式
     * @param city          工作地区即当前城市名
     * @param listener      查询监听
     * @param minWorkTime   最少工作时间（天）
     * @param maxWorkTime   最大工作时间（天）
     */
    public void queryJobInfoByAttr(String jobType, int minJobSalary, int maxJobSalary, String
            balanceMode, String city,int minWorkTime ,int maxWorkTime,final OnQueryJobsListener listener){
        //查询对象
        BmobQuery<Job> query=new BmobQuery<>();
        List<BmobQuery<Job>> attrQuery = new ArrayList<BmobQuery<Job>>();
        //当jobType不为空时添加查询条件
        if (!TextUtils.isEmpty(jobType)){
            BmobQuery<Job> query_1=new BmobQuery<>();
            query_1.addWhereEqualTo("jobType",jobType);
            attrQuery.add(query_1);
        }
        //当最少薪资被设置时添加此查询条件
        if (minJobSalary!=-1){
            BmobQuery<Job> query_2=new BmobQuery<>();
            query_2.addWhereGreaterThanOrEqualTo("jobSalary",minJobSalary);
            attrQuery.add(query_2);
        }
        //当最大薪资被设置时添加此查询条件
        if (maxJobSalary!=-1){
            BmobQuery<Job> query_3=new BmobQuery<>();
            query_3.addWhereLessThan("jobSalary",maxJobSalary);
            attrQuery.add(query_3);
        }
        //当结算方式被设置时添加此查询条件
        if (!TextUtils.isEmpty(balanceMode)){
            BmobQuery<Job> query_4=new BmobQuery<>();
            query_4.addWhereEqualTo("balanceMode",balanceMode);
            attrQuery.add(query_4);
        }
        //默认当前定位城市
        if (!TextUtils.isEmpty(city)){
            BmobQuery<Job> query_5=new BmobQuery<>();
            query_5.addWhereEqualTo("cityName",city);
            attrQuery.add(query_5);
        }
        if (minWorkTime!=-1){
            BmobQuery<Job> query_6=new BmobQuery<>();
            query_6.addWhereGreaterThanOrEqualTo("workDayTime",minWorkTime);
            attrQuery.add(query_6);
        }
        if (maxWorkTime!=-1){
            BmobQuery<Job> query_7=new BmobQuery<>();
            query_7.addWhereLessThan("workDayTime",maxWorkTime);
            attrQuery.add(query_7);
        }
        //设置查询次数50
        query.setLimit(50);
        query.and(attrQuery);
        query.findObjects(new FindListener<Job>() {
            @Override
            public void done(List<Job> list, BmobException e) {
                if (e==null){
                    listener.success(list);
                }else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }
}
