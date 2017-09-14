package com.enjoy.hyc.personal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enjoy.R;
import com.enjoy.base.LogUtils;
import com.enjoy.base.MvpFragment;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.footprint.FootprintActivity;
import com.enjoy.hyc.login.LoginActivity;
import com.enjoy.hyc.payroll.PayrollActivity;
import com.enjoy.hyc.record.RecordActivity;
import com.enjoy.hyc.resume.ResumeActivity;
import com.enjoy.hyc.setting.SettingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * the personal information fragment  Created by hyc on 2017/4/18 11:17
 */

public class PersonalFragment extends MvpFragment<PersonalPresenter> implements PersonalContract {


    @Bind(R.id.iv_background)
    ImageView ivBackground;
    @Bind(R.id.cv_my_head)
    CircleImageView cvMyHead;
    @Bind(R.id.tv_my_name)
    TextView tvMyName;
    @Bind(R.id.tv_medal)
    TextView tvMedal;
    @Bind(R.id.ll_medal)
    RelativeLayout llMedal;
    @Bind(R.id.iv_money)
    ImageView ivMoney;
    @Bind(R.id.rl_salary_bill)
    RelativeLayout rlSalaryBill;
    @Bind(R.id.iv_recode)
    ImageView ivRecode;
    @Bind(R.id.rl_work_record)
    RelativeLayout rlWorkRecord;
    @Bind(R.id.iv_my_footprint)
    ImageView ivMyFootprint;
    @Bind(R.id.rl_footprint)
    RelativeLayout rlFootprint;
    @Bind(R.id.iv_my_publish)
    ImageView ivMyPublish;
    @Bind(R.id.rl_my_publish)
    RelativeLayout rlMyPublish;
    @Bind(R.id.iv_personal_info)
    ImageView ivPersonalInfo;
    @Bind(R.id.rl_my_resume)
    RelativeLayout rlMyResume;
    @Bind(R.id.iv_personal_setting)
    ImageView ivPersonalSetting;
    @Bind(R.id.rl_setting)
    RelativeLayout rlSetting;

    /**
     * create the fragment view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        mvpPresenter = createPresenter();
        mvpPresenter.attachView(this);
        ButterKnife.bind(this, view);
        initView();

        return view;
    }

    private void initView() {
        User user = BmobUser.getCurrentUser(User.class);
        if (user == null) {
            llMedal.setVisibility(View.GONE);
            return;
        }
        String path = user.getHeadImageUrl();
        if (!TextUtils.isEmpty(path)) {
            Glide.with(getActivity())
                    .load(path)
                    .into(cvMyHead);
            Glide.with(getActivity())
                    .load(path)
                    .override(1080, 400)
                    .bitmapTransform(new BlurTransformation(getActivity(), 23, 4))
                    .into(ivBackground);
        }
        if (!TextUtils.isEmpty(user.getName())) {
            tvMyName.setText(user.getName());
        }
    }

    /**
     * initialize the presenter
     *
     * @return presenter
     */
    @Override
    protected PersonalPresenter createPresenter() {
        return new PersonalPresenter();
    }

    /**
     * destroy the view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    /**
     * EventBus的消息接收  当简历头像被改变时将接收到消息并进行头像和背景图的更新
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if (event.indexOf("图片")!=-1){
            if (TextUtils.isEmpty(event.split("片")[1])){
                return;
            }else {
                String path=event.split("片")[1];
                Glide.with(getActivity())
                        .load(path)
                        .into(cvMyHead);
                Glide.with(getActivity())
                        .load(path)
                        .override(1080, 400)
                        .bitmapTransform(new BlurTransformation(getActivity(), 23, 4))
                        .into(ivBackground);
                }
            }
    }


    /**
     * event of controls's click
     *
     * @param view controls
     */
    @OnClick({R.id.rl_salary_bill, R.id.rl_work_record, R.id.rl_footprint, R.id.rl_my_publish, R.id.rl_my_resume, R.id.rl_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_salary_bill:
                startActivity(PayrollActivity.class);
                break;
            case R.id.rl_work_record:
                startActivity(RecordActivity.class);
                break;
            case R.id.rl_footprint:
                startActivity(FootprintActivity.class);
                break;
            case R.id.rl_my_publish:
                break;
            case R.id.rl_my_resume:
                startActivity(ResumeActivity.class);
                break;
            case R.id.rl_setting:
                startActivity(SettingActivity.class);
                break;
        }
    }

    /**
     * enter activity
     *
     * @param activity
     */
    @Override
    public void startActivity(Class<?> activity) {
        if (!mvpPresenter.isLogin()) {
            return;
        } else {
            getActivity().startActivity(new Intent(getActivity(), activity));
        }

    }

    /**
     * 如果用户没有进行登录提示其进行登录
     */
    @Override
    public void enterLogin() {
        new AlertDialog.Builder(mActivity)
                .setIcon(R.drawable.ic_alert)
                .setTitle("未登录")
                .setMessage("是否去登录?")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mActivity.startActivity(new Intent(mActivity, LoginActivity.class));
                            }
                        }).setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}
