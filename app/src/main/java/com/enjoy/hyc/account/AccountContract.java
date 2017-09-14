package com.enjoy.hyc.account;

import cn.bmob.v3.BmobUser;

/**
 * Created by hyc on 2017/4/25 21:50
 */

public interface AccountContract {

    void setAccountData(BmobUser user, boolean isVerify);

    String getVerifyNumber();

    void verifySuccess();

    void verifyFail();

    String getAmendPassword();

    String getNewPassword();

    boolean isFill();

    void AmendSuccess();

    void AmendFail();

}
