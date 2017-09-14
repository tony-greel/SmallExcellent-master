package com.enjoy.hyc.setting;

import com.enjoy.base.BasePresenter;

/**
 * Created by hyc on 2017/4/25 20:09
 */

public class SettingPresenter extends BasePresenter<SettingContract> {


    /**
     * 根据点击位置进入相应设置
     * @param position  0：账号安全设置   1：关于我们设置  2：退出当前账号
     */
    public void enterInterface(int position){
        switch (position){
            case 0:mvpView.enterAccountSafe();break;
            case 1:mvpView.enterAboutUs();break;
            case 2:mvpView.enterLogin();break;
        }
    }

}
