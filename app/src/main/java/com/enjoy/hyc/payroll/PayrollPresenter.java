package com.enjoy.hyc.payroll;

import android.os.Handler;

import com.enjoy.base.BasePresenter;
import com.enjoy.base.LogUtils;
import com.enjoy.hyc.bean.Moonlighting;
import com.enjoy.hyc.bean.Payroll;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.util.MoonlightingUtil;
import com.enjoy.hyc.util.PayrollUtil;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by hyc on 2017/4/27 10:03
 */

public class PayrollPresenter extends BasePresenter<PayrollContract> {

    /**
     * 加载并显示工资单详情
     */
    public void showPayrollDetail(){
        mvpView.showLoadingView();
        User user= BmobUser.getCurrentUser(User.class);
        MoonlightingUtil.queryMoonlighting(user, new MoonlightingUtil.OnQueryMoonlighting() {
            @Override
            public void success(List<Moonlighting> list) {
                if (list.size()>0){
                    List<Payroll> payrolls=PayrollUtil.countPayrollByMoonlighting(list);
                    mvpView.loadComplete(payrolls);
                }else {
                        mvpView.noPayroll();
                }
            }

            @Override
            public void fail(String error) {
                LogUtils.log(error);
                mvpView.loadFail();
            }
        });


    }
}
