package com.enjoy.hyc.query;

import android.text.TextUtils;

import com.enjoy.base.BasePresenter;
import com.enjoy.base.LogUtils;
import com.enjoy.hyc.bean.Job;
import com.enjoy.hyc.util.JobUtil;

import java.util.List;

/**
 * Created by hyc on 2017/4/22 11:26
 */

public class QueryPresenter extends BasePresenter<QueryContract> {

    public JobUtil.JobQuery jobQuery;

    @Override
    public void attachView(QueryContract mvpView) {
        super.attachView(mvpView);
        if (jobQuery==null){
            JobUtil job=new JobUtil();
            jobQuery=job.new JobQuery();
        }
    }

    public void addNewJob(){
        if (mvpView!=null){
            mvpView.addNewJobInformation();
        }
    }


    public void addQueryCondition(int position){
        switch (position){
            case 0:
                mvpView.addTypeCondition();
                break;
            case 1:
                mvpView.addSalaryCondition();
                break;
            case 2:
                mvpView.addTimeCondition();
                break;
            case 3:
                mvpView.addPlaceCondition();
                break;
        }
    }

    public void query(String type,String salary,String time,String place,String query,boolean hasLimit){
        if (hasLimit&&!place.equals("定位中")){
            mvpView.showLoadingView();
            jobQuery.setCity(place);
            jobQuery.setTime(time.equals("不限")?"":time);
            jobQuery.setSalary(salary.equals("不限")?"":salary);
            jobQuery.setType(type.equals("不限")?"":type);
            jobQuery.query(new JobUtil.OnQueryJobsListener() {
                @Override
                public void success(List<Job> jobs) {
                    if (jobs.size()>0){
                        LogUtils.log("查询成功"+jobs.size());
                        mvpView.showLoadedData(jobs);
                    }else {
                        mvpView.showLoadFail("没有符合要求的兼职");
                    }
                }

                @Override
                public void fail(String error) {
                    mvpView.showLoadFail("查询出错,请检查网络");
                }
            });
        }else if (!place.equals("定位中")){
            if (TextUtils.isEmpty(query)){
                return;
            }
            mvpView.showLoadingView();
            JobUtil.getJobInfoByType(query, new JobUtil.OnGetJobInfoListener() {
                @Override
                public void success(List<Job> jobs) {
                    if (jobs.size()>0){
                        mvpView.showLoadedData(jobs);
                    }else {
                        mvpView.showLoadFail("没有符合要求的兼职");
                    }
                }

                @Override
                public void fail(String error) {
                    mvpView.showLoadFail("查询出错,请检查网络");
                }
            });
        }
    }

    public void continueToQuery(){
        mvpView.backQueryInterface();
    }


}
