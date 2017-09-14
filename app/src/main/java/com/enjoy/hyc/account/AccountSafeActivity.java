package com.enjoy.hyc.account;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoy.R;
import com.enjoy.base.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class AccountSafeActivity extends MvpActivity<AccountPresenter> implements AccountContract {


    @Bind(R.id.tb_account)
    Toolbar tbAccount;
    @Bind(R.id.tv_user_number)
    TextView tvUserNumber;
    @Bind(R.id.et_user_number)
    EditText etUserNumber;
    @Bind(R.id.btn_verify_number)
    Button btnVerifyNumber;
    @Bind(R.id.rl_verify)
    RelativeLayout rlVerify;
    @Bind(R.id.et_user_password)
    EditText etUserPassword;
    @Bind(R.id.et_user_new_number)
    EditText etUserNewNumber;
    @Bind(R.id.btn_amend_password)
    Button btnAmendPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        ButterKnife.bind(this);
        setToolBar(R.id.tb_account);
        initToolBarAsHome("账号与安全");
        mvpPresenter.attachView(this);
        mvpPresenter.initActivityData();
    }

    /**
     * 将presenter与activity绑定
     * @return
     */
    @Override
    protected AccountPresenter createPresenter() {
        return new AccountPresenter();
    }

    /**
     * 将用户账号手机绑定信息显示在界面中
     * @param user     当前用户
     * @param isVerify  是否验证了手机号码
     */
    @Override
    public void setAccountData(BmobUser user, boolean isVerify) {
        if (isVerify) {
            rlVerify.setVisibility(View.GONE);
            tvUserNumber.setText(user.getMobilePhoneNumber());
        } else {
            tvUserNumber.setText("去绑定");
            tvUserNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rlVerify.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    /**
     * 获取用户输入的手机号码用来验证
     * @return
     */
    @Override
    public String getVerifyNumber() {
        if (!TextUtils.isEmpty(etUserNumber.getText().toString())) {
            showProgressDialog();
        }
        return etUserNumber.getText().toString();
    }

    /**
     * 验证成功后的界面提示
     */
    @Override
    public void verifySuccess() {
        dismissProgressDialog();
        Toast.makeText(mActivity, "验证成功", Toast.LENGTH_SHORT).show();
        rlVerify.setVisibility(View.GONE);
        tvUserNumber.setText(BmobUser.getCurrentUser().getMobilePhoneNumber());
    }

    /**
     * 验证失败的界面提示
     */
    @Override
    public void verifyFail() {
        dismissProgressDialog();
        Toast.makeText(mActivity, "验证失败请重试", Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取用户想修改的旧密码
     * @return 旧密码
     */
    @Override
    public String getAmendPassword() {
        return etUserPassword.getText().toString();

    }

    /**
     * 获取用户想修改的新密码
     * @return 新密码
     */
    @Override
    public String getNewPassword() {
        return etUserNewNumber.getText().toString();
    }

    @Override
    public boolean isFill() {
        boolean isFill=TextUtils.isEmpty(etUserPassword.getText().toString())
                |TextUtils.isEmpty(etUserNewNumber.getText().toString());
        if (!isFill){
            showProgressDialog();
        }
        return isFill;
    }

    /**
     * 修改成功后的界面提示
     */
    @Override
    public void AmendSuccess() {
        dismissProgressDialog();
        etUserPassword.setText("");
        etUserNewNumber.setText("");
        Toast.makeText(mActivity, "修改成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 修改失败后的界面提示
     */
    @Override
    public void AmendFail() {
        dismissProgressDialog();
        Toast.makeText(mActivity, "修改失败,稍后再试", Toast.LENGTH_SHORT).show();
    }

    /**
     * 点击事件   btn_verify_number验证手机号码的按钮    btn_amend_password修改密码的按钮
     * @param view
     */
    @OnClick({R.id.btn_verify_number, R.id.btn_amend_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_verify_number:
                mvpPresenter.verifyMobileNumber();
                break;
            case R.id.btn_amend_password:
                mvpPresenter.amendPassword();
                break;
        }
    }
}
