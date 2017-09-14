package com.enjoy.hyc.register;

import android.widget.Toast;

import com.enjoy.base.BasePresenter;
import com.enjoy.base.SmallApplication;
import com.enjoy.hyc.util.UserUtil;

/**
 * Created by hyc on 2017/4/25 19:54
 */

public class RegisterPresenter extends BasePresenter<RegisterContract> {

    /**
     * 用户注册
     */
    public void register(){
        if (mvpView.isFill()&&mvpView.isConfirmPassword()){
            UserUtil.register(mvpView.getRegisterUser(), new UserUtil.OnRegisterListener() {
                @Override
                public void success() {
                    mvpView.registerSuccess();
                }

                @Override
                public void registerFail(String error) {
                    mvpView.registerFail();
                }
            });
        }else if(!mvpView.isFill()){
            Toast.makeText(SmallApplication.getContext(), "请填写完信息", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(SmallApplication.getContext(), "两次密码输入不匹配", Toast.LENGTH_SHORT).show();
        }
    }
}
