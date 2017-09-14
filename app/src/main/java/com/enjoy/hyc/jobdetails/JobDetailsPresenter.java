package com.enjoy.hyc.jobdetails;

import android.widget.Toast;

import com.enjoy.base.BasePresenter;
import com.enjoy.base.SmallApplication;
import com.enjoy.hyc.bean.Job;
import com.enjoy.hyc.bean.Moonlighting;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.util.JobUtil;
import com.enjoy.hyc.util.MoonlightingUtil;

import org.litepal.tablemanager.Connector;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by hyc on 2017/4/24 15:58
 */

public class JobDetailsPresenter extends BasePresenter<JobDetailsContract> {
    /**
     * 用于显示的工作详情的数据对象
     */
    public static Job jobContent;

    public JobDetailsPresenter(JobDetailsContract jobDetailsContract){
        attachView(jobDetailsContract);
    }

    /**
     * 加载工作详情信息
     */
    public void loadJobContent(){
        if (jobContent!=null){
            User user=BmobUser.getCurrentUser(User.class);
            if (user==null){
                mvpView.notLogin();
                return;
            }else {
                Connector.getDatabase();
                mvpView.verifyHaveApply();
                mvpView.loadJobContent(jobContent);
                MoonlightingUtil.queryUserIsApply(jobContent, new JobUtil.OnDeleteJobListener() {
                    @Override
                    public void success() {
                        mvpView.haveApply();
                    }

                    @Override
                    public void fail(String error) {
                        if (error.equals("no")){
                            mvpView.haveNotApply();
                        }else {
                            mvpView.verifyError();
                        }
                    }
                });
            }
        }
    }

    /**
     * 申请工作
     */
    public void applyJob(){
        mvpView.applying();
        Moonlighting moonlighting=new Moonlighting();
        moonlighting.setJob(jobContent);
        User user=BmobUser.getCurrentUser(User.class);
        moonlighting.setUser(user);
        MoonlightingUtil.addNewMoonlighting(moonlighting, new JobUtil.OnDeleteJobListener() {
            @Override
            public void success() {
                mvpView.applySuccess();
            }

            @Override
            public void fail(String error) {
                mvpView.applyFail("");
            }
        });
    }

}
