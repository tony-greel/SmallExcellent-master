package com.enjoy.hyc.resume;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.editresume.EditResumeActivity;
import com.enjoy.hyc.view.SildingFinishLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hyc on 2017/4/26 10:54
 */

public class ResumeActivity extends MvpActivity<ResumePresenter> implements ResumeContract {


    @Bind(R.id.tv_exit_resume)
    ImageView tvExitResume;
    @Bind(R.id.username_txt)
    TextView usernameTxt;
    @Bind(R.id.tv_edit_resume)
    TextView tvEditResume;
    @Bind(R.id.cv_resume_head)
    CircleImageView cvResumeHead;
    @Bind(R.id.t1_txt)
    TextView t1Txt;
    @Bind(R.id.name_txt)
    TextView nameTxt;
    @Bind(R.id.sex_txt)
    TextView sexTxt;
    @Bind(R.id.age_txt)
    TextView ageTxt;
    @Bind(R.id.height_txt)
    TextView heightTxt;
    @Bind(R.id.city_txt)
    TextView cityTxt;
    @Bind(R.id.school_txt)
    TextView schoolTxt;
    @Bind(R.id.t2_txt)
    TextView t2Txt;
    @Bind(R.id.objective_txt)
    TextView objectiveTxt;
    @Bind(R.id.t3_txt)
    TextView t3Txt;
    @Bind(R.id.selfInfo_txt)
    TextView selfInfoTxt;
    @Bind(R.id.t4_txt)
    TextView t4Txt;
    @Bind(R.id.tv_word_experience)
    TextView tvWordExperience;
    @Bind(R.id.t5_txt)
    TextView t5Txt;
    @Bind(R.id.tv_contract_qq)
    TextView tvContractQq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNoStateColor = false;
        setContentView(R.layout.layout_resume);
        mvpPresenter.attachView(this);
        ButterKnife.bind(this);
        mvpPresenter.loadCurrentUserResume();
    }

    @Override
    protected ResumePresenter createPresenter() {
        return new ResumePresenter();
    }

    @OnClick({R.id.tv_exit_resume, R.id.tv_edit_resume})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_exit_resume:
                finish();
                break;
            case R.id.tv_edit_resume:
                startActivity(new Intent(this, EditResumeActivity.class));
                finish();
                break;
        }
    }

    /**
     * 显示当前用户的简历信息
     * @param user 当前用户
     */
    @Override
    public void showCurrentUserResume(User user) {
        if (!TextUtils.isEmpty(user.getHeadImageUrl())) {
            Glide.with(mActivity)
                    .load(user.getHeadImageUrl())
                    .into(cvResumeHead);
        }
        nameTxt.setText(TextUtils.isEmpty(user.getName()) ? "小优" : user.getName());
        sexTxt.setText(TextUtils.isEmpty(user.getSex()) ? "男" : user.getSex());
        ageTxt.setText(TextUtils.isEmpty(user.getAge()) ? "未填写" : user.getAge());
        heightTxt.setText(user.getHeight() != 0 ? user.getHeight() + "" : "未填写");
        cityTxt.setText(TextUtils.isEmpty(user.getCity()) ? "未填写" : user.getCity());
        schoolTxt.setText(TextUtils.isEmpty(user.getSchool()) ? "未填写" : user.getSchool());
        objectiveTxt.setText(TextUtils.isEmpty(user.getObjective()) ? "不限" : user.getObjective());
        selfInfoTxt.setText(TextUtils.isEmpty(user.getSelfInfo()) ? "未填写" : user.getSelfInfo());
        tvWordExperience.setText(TextUtils.isEmpty(user.getWorkExperience()) ? "暂无" : user.getWorkExperience());
        tvContractQq.setText(TextUtils.isEmpty(user.getQqNumber()) ? "未填写" : user.getQqNumber());

    }
}
