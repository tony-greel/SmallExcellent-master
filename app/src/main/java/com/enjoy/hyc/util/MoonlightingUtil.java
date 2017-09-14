package com.enjoy.hyc.util;

import com.enjoy.hyc.bean.Job;
import com.enjoy.hyc.bean.Moonlighting;
import com.enjoy.hyc.bean.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 兼职合同工具类 Created by hyc on 2017/4/26 09:04
 */

public class MoonlightingUtil {

    public interface OnQueryMoonlighting{
        void success(List<Moonlighting> list);
        void fail(String error);
    }


    /**
     * 当user 报名参加一个兼职活动的时候创建一个新的兼职合同
     * @param moonlighting
     * @param listener
     */
    public static void addNewMoonlighting(Moonlighting moonlighting, final JobUtil.OnDeleteJobListener listener){
        moonlighting.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    listener.success();
                }else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 通过用户查询改用户报名参加的所有兼职信息
     * @param user
     * @param listener
     */
    public static void queryMoonlighting(User user, final OnQueryMoonlighting listener){
        BmobQuery<Moonlighting> query=new BmobQuery();
        query.addWhereEqualTo("user",user);
        query.setLimit(50);
        query.include("job");
        query.findObjects(new FindListener<Moonlighting>() {
            @Override
            public void done(List<Moonlighting> list, BmobException e) {
                if (e==null){
                    listener.success(list);
                }else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 通过工作查询当前用户是否申请了该兼职工作
     * @param job
     * @param listener
     */
    public static void queryUserIsApply(Job job, final JobUtil.OnDeleteJobListener listener){
        BmobQuery<Moonlighting> query=new BmobQuery<>();
        query.addWhereEqualTo("job",job);
        query.setLimit(50);
        query.include("user");
        query.findObjects(new FindListener<Moonlighting>() {
            @Override
            public void done(List<Moonlighting> list, BmobException e) {
                if (e==null){
                    if (list.size()>0){
                        for (Moonlighting moonlighting:list){
                            if(moonlighting.getUser().getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())){
                                listener.success();
                                return;
                            }
                        }
                        listener.fail("no");
                    }else {
                        listener.fail("no");
                    }
                }else {
                    listener.fail(e.getMessage());
                }

            }
        });
    }


}
