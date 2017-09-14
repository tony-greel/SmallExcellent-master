package com.enjoy.hyc.record;

import com.enjoy.base.BasePresenter;
import com.enjoy.hyc.bean.Moonlighting;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.util.MoonlightingUtil;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by hyc on 2017/4/26 18:52
 */

public class RecordPresenter extends BasePresenter<RecordContract> {

    /**
     * 获取兼职信息并通过activity显示
     */
    public void getRecord(){
        mvpView.showLoading();
        User user= BmobUser.getCurrentUser(User.class);
        MoonlightingUtil.queryMoonlighting(user, new MoonlightingUtil.OnQueryMoonlighting() {
            @Override
            public void success(List<Moonlighting> list) {
                mvpView.loadingSuccess(list);
            }

            @Override
            public void fail(String error) {
                mvpView.loadingFail();
            }
        });
    }
}
