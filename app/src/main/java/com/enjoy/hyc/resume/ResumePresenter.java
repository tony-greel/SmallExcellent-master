package com.enjoy.hyc.resume;

import com.enjoy.base.BasePresenter;
import com.enjoy.hyc.bean.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by hyc on 2017/4/26 10:55
 */

public class ResumePresenter extends BasePresenter<ResumeContract> {

    /**
     * 加载当前用户的简历信息并显示
     */
    public void loadCurrentUserResume(){
        User user= BmobUser.getCurrentUser(User.class);
        mvpView.showCurrentUserResume(user);
    }

}
