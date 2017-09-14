package com.enjoy.hyc.publishjob;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.bean.Job;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * activity of publish a new part-time information
 */
public class PublishActivity extends MvpActivity<PublishPresenter> implements PublishContract{
    /**
     * 城市名输入框
     */
    @Bind(R.id.et_city_name)
    EditText etCityName;
    /**
     * 发布者输入框
     */
    @Bind(R.id.et_publisher)
    EditText etPublisher;
    /**
     * 联系电话输入框
     */
    @Bind(R.id.et_contract_number)
    EditText etContractNumber;
    /**
     * 联系地址输入框
     */
    @Bind(R.id.et_contract_address)
    EditText etContractAddress;
    /**
     * 工作类型输入框
     */
    @Bind(R.id.et_job_type)
    EditText etJobType;
    /**
     * 工作全名输入框
     */
    @Bind(R.id.et_job_name)
    EditText etJobName;
    /**
     * 工作薪资输入框
     */
    @Bind(R.id.et_job_salary)
    EditText etJobSalary;
    /**
     * 招聘人数输入框
     */
    @Bind(R.id.et_recruitment_count)
    EditText etRecruitmentCount;
    /**
     * 工作区域输入框
     */
    @Bind(R.id.et_work_region)
    EditText etWorkRegion;
    /**
     * 报名截止时间输入框
     */
    @Bind(R.id.et_deadline)
    EditText etDeadline;
    /**
     * 工作描述输入框
     */
    @Bind(R.id.et_job_describe)
    EditText etJobDescribe;
    /**
     * 工作要求输入框
     */
    @Bind(R.id.et_job_demand)
    EditText etJobDemand;
    /**
     * 结算方式输入框
     */
    @Bind(R.id.et_balance_mode)
    EditText etBalanceMode;
    /**
     * 工作时段输入框
     */
    @Bind(R.id.et_work_interval)
    EditText etWorkInterval;
    /**
     * 工作时长输入框
     */
    @Bind(R.id.et_word_time)
    EditText etWordTime;
    /**
     * 提交按钮
     */
    @Bind(R.id.btn_commit_job)
    Button btnCommitJob;

    @Bind(R.id.rl_publish_success)
    RelativeLayout rlPublishSuccess;

    @Bind(R.id.sv_input)
    ScrollView svInput;

    /**
     * the Initialize of activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        mvpPresenter.attachView(this);
        setToolBar(R.id.tb_publish);
        initToolBarAsHome("发布新兼职");
    }

    /**
     * Initialize the Presenter
     * @return presenter
     */
    @Override
    protected PublishPresenter createPresenter() {
        return new PublishPresenter();
    }

    /**
     * event of the commit's button
     */
    @OnClick(R.id.btn_commit_job)
    public void onViewClicked() {
        mvpPresenter.commitANewJob();
    }

    /**
     *  get all edit information
     * @return need to publish a new part-time information
     */
    @Override
    public Job getNewJobInfo() {
        Job job=new Job();
        job.setCityName(getContentOfEditText(etCityName));
        job.setBalanceMode(getContentOfEditText(etBalanceMode));
        job.setContactAddress(getContentOfEditText(etContractAddress));
        job.setDeadline(getContentOfEditText(etDeadline));
        job.setContactNumber(getContentOfEditText(etContractNumber));
        job.setJobDemand(getContentOfEditText(etJobDemand));
        job.setJobDescribe(getContentOfEditText(etJobDescribe));
        job.setJobDetailName(getContentOfEditText(etJobName));
        job.setJobSalary(Integer.parseInt(getContentOfEditText(etJobSalary)));
        job.setJobType(getContentOfEditText(etJobType));
        job.setJobRegion(getContentOfEditText(etWorkRegion));
        job.setWorkDayTime(Integer.parseInt(getContentOfEditText(etWordTime)));
        job.setWorkInterval(getContentOfEditText(etWorkInterval));
        job.setPublisher(getContentOfEditText(etPublisher));
        job.setRecruitmentNumber(Integer.parseInt(getContentOfEditText(etRecruitmentCount)));
        return job;
    }

    /**
     * judge all information is filled
     * @return if filled up will return false
     */
    @Override
    public boolean IsFillAllEditText() {
        return TextUtils.isEmpty(getContentOfEditText(etBalanceMode))
                |TextUtils.isEmpty(getContentOfEditText(etCityName))
                |TextUtils.isEmpty(getContentOfEditText(etContractAddress))
                |TextUtils.isEmpty(getContentOfEditText(etContractNumber))
                |TextUtils.isEmpty(getContentOfEditText(etDeadline))
                |TextUtils.isEmpty(getContentOfEditText(etJobDemand))
                |TextUtils.isEmpty(getContentOfEditText(etJobDescribe))
                |TextUtils.isEmpty(getContentOfEditText(etJobName))
                |TextUtils.isEmpty(getContentOfEditText(etJobSalary))
                |TextUtils.isEmpty(getContentOfEditText(etRecruitmentCount))
                |TextUtils.isEmpty(getContentOfEditText(etPublisher))
                |TextUtils.isEmpty(getContentOfEditText(etWorkInterval))
                |TextUtils.isEmpty(getContentOfEditText(etWorkRegion))
                |TextUtils.isEmpty(getContentOfEditText(etJobType))
                |TextUtils.isEmpty(getContentOfEditText(etWordTime));
    }

    /**
     * show when commit the new part-time information to the server
     */
    @Override
    public void showLoading() {
        showProgressDialog("正在上传...");
    }

    /**
     * hide the dialog when the commit is complete and show the interface of success
     */
    @Override
    public void loadComplete(boolean IsSuccess) {
        dismissProgressDialog();
        if (IsSuccess){
            rlPublishSuccess.setVisibility(View.VISIBLE);
            svInput.setVisibility(View.GONE);
        }
    }

    /**
     * get the content of input box
     * @param editText  input box
     * @return  the content of input box
     */
    private String getContentOfEditText(EditText editText){
        return editText.getText().toString();
    }
}
