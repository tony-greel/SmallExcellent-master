package com.enjoy.hyc.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.main.MainActivity;
import com.enjoy.hyc.register.RegisterActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends MvpActivity<LoginPresenter> implements LoginContract{

    @Bind(R.id.et_login_username)
    EditText etLoginUsername;
    @Bind(R.id.et_login_password)
    EditText etLoginPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.tv_register)
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mvpPresenter.attachView(this);
        ButterKnife.bind(this);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mvpPresenter.login();
                break;
            case R.id.tv_register:
                mvpPresenter.register();
                break;
        }
    }

    /**
     * 获得登录User对象
     * @return
     */
    @Override
    public User getLoginUser() {
        btnLogin.setClickable(false);
        btnLogin.setText("登录中...");
        User user=new User();
        user.setUsername(etLoginUsername.getText().toString());
        user.setPassword(etLoginPassword.getText().toString());
        return user;
    }

    /**
     * 登录成功的界面提示
     */
    @Override
    public void loginSuccess() {
        btnLogin.setClickable(true);
        btnLogin.setText("登录成功");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 登录失败的界面提示
     */
    @Override
    public void loginFail() {
        btnLogin.setClickable(true);
        btnLogin.setText("登录");
        Toast.makeText(mActivity, "登录失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 进入注册界面
     */
    @Override
    public void enterRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    /**
     * 判断登录信息是否填写完整
     * @return 是否填写完整 true 是没有填写完毕  false 是填写完毕
     */
    @Override
    public boolean isFill() {
        return TextUtils.isEmpty(etLoginUsername.getText().toString())
                |TextUtils.isEmpty(etLoginPassword.getText().toString());
    }
}
