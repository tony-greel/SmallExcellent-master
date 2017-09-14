package com.enjoy.hyc.login;

import com.enjoy.hyc.bean.User;

/**
 * Created by hyc on 2017/4/25 19:00
 */

public interface LoginContract {

    User getLoginUser();

    void loginSuccess();

    void loginFail();

    void enterRegister();

    boolean isFill();

}
