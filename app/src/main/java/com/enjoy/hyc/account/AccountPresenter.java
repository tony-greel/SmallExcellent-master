package com.enjoy.hyc.account;

import android.text.TextUtils;
import android.widget.Toast;

import com.enjoy.base.BasePresenter;
import com.enjoy.base.LogUtils;
import com.enjoy.base.SmallApplication;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.util.JobUtil;
import com.enjoy.hyc.util.UserUtil;

import cn.bmob.v3.BmobUser;

/**
 * 账户与安全界面 Created by hyc on 2017/4/25 21:50
 */

public class AccountPresenter extends BasePresenter<AccountContract> {

    /**
     * 初始化activity数据，判断当前用户是否绑定手机号码
     */
    public void initActivityData(){
        User user=BmobUser.getCurrentUser(User.class);
        if (user.getMobilePhoneNumberVerified()==null){
            mvpView.setAccountData(user,false);
        }else if (!user.getMobilePhoneNumberVerified()){
            mvpView.setAccountData(user,false);
        }else if(user.getMobilePhoneNumberVerified()){
            mvpView.setAccountData(user,true);
        }
    }

    /**
     * 验证当前用户的手机号码并绑定
     */
    public void verifyMobileNumber(){
        String number=mvpView.getVerifyNumber();
        if (!TextUtils.isEmpty(number)){
            UserUtil.verifyMobileNumber(number, new JobUtil.OnDeleteJobListener() {
                @Override
                public void success() {
                    mvpView.verifySuccess();
                }

                @Override
                public void fail(String error) {
                    mvpView.verifyFail();
                }
            });
        }else {
            Toast.makeText(SmallApplication.getContext(), "请输入电话号码", Toast.LENGTH_SHORT).show();
        }
        
    }

    /**
     * 修改密码
     */
    public void amendPassword(){
        if (!mvpView.isFill()){
            UserUtil.amendPassword(mvpView.getAmendPassword(), mvpView.getNewPassword(), new JobUtil.OnDeleteJobListener() {
                @Override
                public void success() {
                    mvpView.AmendSuccess();
                }

                @Override
                public void fail(String error) {
                    mvpView.AmendFail();
                }
            });
        }else {
            Toast.makeText(SmallApplication.getContext(), "请填写信息", Toast.LENGTH_SHORT).show();
        }
    }

}
