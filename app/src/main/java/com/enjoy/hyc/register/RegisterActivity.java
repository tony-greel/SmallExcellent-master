package com.enjoy.hyc.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册界面
 */
public class RegisterActivity extends MvpActivity<RegisterPresenter> implements RegisterContract {


    @Bind(R.id.tb_register)
    Toolbar tbRegister;
    @Bind(R.id.et_register_username)
    EditText etRegisterUsername;
    @Bind(R.id.et_register_password)
    EditText etRegisterPassword;
    @Bind(R.id.et_confirm_password)
    EditText etConfirmPassword;
    @Bind(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mvpPresenter.attachView(this);
        ButterKnife.bind(this);
        setToolBar(R.id.tb_register);
        initToolBarAsHome("注册");
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        mvpPresenter.register();
    }

    /**
     * 获取注册User信息
     * @return
     */
    @Override
    public User getRegisterUser() {
        btnRegister.setClickable(false);
        btnRegister.setText("注册中...");
        User user = new User();
        user.setUsername(etRegisterUsername.getText().toString());
        user.setPassword(etRegisterPassword.getText().toString());
        return user;
    }

    /**
     * 注册成功的界面提示
     */
    @Override
    public void registerSuccess() {
        Toast.makeText(mActivity, "注册成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * 注册失败的界面提示
     */
    @Override
    public void registerFail() {
        Toast.makeText(mActivity, "注册失败,请稍后再试", Toast.LENGTH_SHORT).show();
        btnRegister.setText("注册");
        btnRegister.setClickable(true);
    }

    /**
     * 判断注册信息是否填写完整
     * @return
     */
    @Override
    public boolean isFill() {
        return !TextUtils.isEmpty(etRegisterUsername.getText().toString())
                &!TextUtils.isEmpty(etRegisterPassword.getText().toString())
                &!TextUtils.isEmpty(etConfirmPassword.getText().toString());
    }

    /**
     * 判断密码和确认密码是否一致
     * @return
     */
    @Override
    public boolean isConfirmPassword() {
        return etRegisterPassword.getText().toString().equals(etConfirmPassword.getText().toString());
    }
}
