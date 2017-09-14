package com.enjoy.hyc.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.account.AccountSafeActivity;
import com.enjoy.hyc.login.LoginActivity;
import com.enjoy.hyc.util.JobCacheUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * 设置界面
 */
public class SettingActivity extends MvpActivity<SettingPresenter> implements SettingContract {

    @Bind(R.id.tb_setting)
    Toolbar tbSetting;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.tv_about)
    TextView tvAbout;
    @Bind(R.id.tv_exit)
    TextView tvExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mvpPresenter.attachView(this);
        ButterKnife.bind(this);
        setToolBar(R.id.tb_setting);
        initToolBarAsHome("设置");
        addActivity(this);
    }

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @OnClick({R.id.tv_account, R.id.tv_about, R.id.tv_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account:
                mvpPresenter.enterInterface(0);
                break;
            case R.id.tv_about:
                mvpPresenter.enterInterface(1);
                break;
            case R.id.tv_exit:
                mvpPresenter.enterInterface(2);
                break;
        }
    }

    /**
     * 进入账号与安全界面
     */
    @Override
    public void enterAccountSafe() {
        startActivity(new Intent(this, AccountSafeActivity.class));
    }

    @Override
    public void enterAboutUs() {

    }

    /**
     * 退出当前账号并进入登录界面
     */
    @Override
    public void enterLogin() {
        BmobUser.logOut();
        JobCacheUtil.deleteAll();
        startActivity(new Intent(this,LoginActivity.class));
        finishAll();
    }
}
