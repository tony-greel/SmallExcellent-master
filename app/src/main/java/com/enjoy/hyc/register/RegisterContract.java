package com.enjoy.hyc.register;

import com.enjoy.hyc.bean.User;

/**
 * Created by hyc on 2017/4/25 19:54
 */

public interface RegisterContract {

    User getRegisterUser();

    void registerSuccess();

    void registerFail();

    boolean isFill();

    boolean isConfirmPassword();
}
