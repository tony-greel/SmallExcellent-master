package com.enjoy.hyc.login;

import android.widget.Toast;

import com.enjoy.base.BasePresenter;
import com.enjoy.base.SmallApplication;
import com.enjoy.hyc.util.UserUtil;

/**
 * Created by hyc on 2017/4/25 19:01
 */

public class LoginPresenter extends BasePresenter<LoginContract> {

    /**
     * 登录
     */
    public void login(){
        if (!mvpView.isFill()){
            UserUtil.login(mvpView.getLoginUser(), new UserUtil.OnLoginListener() {
                @Override
                public void success() {
                    mvpView.loginSuccess();
                }

                @Override
                public void loginFail(String error) {
                    mvpView.loginFail();
                }
            });
        }else {
            Toast.makeText(SmallApplication.getContext(), "信息没有填写完整", Toast.LENGTH_SHORT).show();
        }

    }

    public void register(){
        mvpView.enterRegister();
    }

}
