package com.enjoy.hyc.personal;

import com.enjoy.base.BasePresenter;
import com.enjoy.hyc.bean.User;

import cn.bmob.v3.BmobUser;

/**
 * Created by hyc on 2017/4/22 09:57
 */

public class PersonalPresenter extends BasePresenter<PersonalContract> {
    /**
     * enter the corresponding activity
     */
    public void enterActivity(Class<?> activity){
        mvpView.startActivity(activity);
    }

    /**
     * 判断当前用户登录
     * @return
     */
    public boolean isLogin(){
        boolean isLogin=BmobUser.getCurrentUser(User.class)!=null;
        if (!isLogin){
            mvpView.enterLogin();
        }
        return isLogin;
    }
}
