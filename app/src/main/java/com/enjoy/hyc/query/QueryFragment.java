package com.enjoy.hyc.query;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoy.R;
import com.enjoy.base.LogUtils;
import com.enjoy.base.MvpFragment;
import com.enjoy.hyc.adapter.QueryJobAdapter;
import com.enjoy.hyc.bean.Job;
import com.enjoy.hyc.main.MainPresenter;
import com.enjoy.hyc.publishjob.PublishActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * query part-time job fragment Created by hyc on 2017/4/18 12:17
 */

public class QueryFragment extends MvpFragment<QueryPresenter> implements QueryContract {
    /**
     * add a new part-time job options
     */
    @Bind(R.id.iv_add_publish)
    ImageView ivAddPublish;
    /**
     * input the information of query
     */
    @Bind(R.id.et_query_input)
    EditText etQueryInput;
    /**
     * choice the type condition
     */
    @Bind(R.id.rl_type_condition)
    RelativeLayout rlTypeCondition;
    /**
     * choice the balance mode condition
     */
    @Bind(R.id.rl_salary_balance)
    RelativeLayout rlSalaryBalance;
    /**
     * choice the time of work condition
     */
    @Bind(R.id.rl_work_time)
    RelativeLayout rlWorkTime;
    /**
     * choice the condition of work area
     */
    @Bind(R.id.rl_work_area)
    RelativeLayout rlWorkArea;
    /**
     * begin query job
     */
    @Bind(R.id.btn_query_job)
    Button btnQueryJob;
    /**
     * controls of choice type
     */
    @Bind(R.id.tv_type)
    TextView tvType;
    /**
     * controls of choice salary
     */
    @Bind(R.id.tv_salary)
    TextView tvSalary;
    /**
     * controls of choice work's time
     */
    @Bind(R.id.tv_time)
    TextView tvTime;
    /**
     * controls of choice work's place
     */
    @Bind(R.id.tv_place)
    TextView tvPlace;
    /**
     * loading view
     */
    @Bind(R.id.rl_loading)
    RelativeLayout rlLoading;
    /**
     * view of query result
     */
    @Bind(R.id.rv_query_result)
    RecyclerView rvQueryResult;
    /**
     * linear layout of query controls
     */
    @Bind(R.id.ll_query_controls)
    LinearLayout llQueryControls;
    /**
     * judge the mode of choose
     */
    @Bind(R.id.rl_query_data)
    RelativeLayout rlQueryData;
    @Bind(R.id.fb_query_back)
    FloatingActionButton fbQueryData;

    private boolean isLimit=false;
    private QueryJobAdapter mQueryJobAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        ButterKnife.bind(this, view);
        initLocation();
        initView();
        mvpPresenter = createPresenter();
        mvpPresenter.attachView(this);
        return view;
    }

    private void initView() {
        rvQueryResult.setLayoutManager(new LinearLayoutManager(mActivity));
        mQueryJobAdapter = new QueryJobAdapter(null);
        rvQueryResult.setAdapter(mQueryJobAdapter);
    }

    private void initLocation() {
        if (!TextUtils.isEmpty(MainPresenter.getMainPresenter().currentCityName)) {
            tvPlace.setText(MainPresenter.getMainPresenter().currentCityName);
        } else {
            tvPlace.setText("定位中");
        }
    }

    @Override
    protected QueryPresenter createPresenter() {
        return new QueryPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void addNewJobInformation() {
        getActivity().startActivity(new Intent(getActivity(), PublishActivity.class));
    }

    @Override
    public void addTypeCondition() {
        final String[] type = new String[]{"服务员", "传单派发", "家教", "美工", "钟点工", "促销员"
                , "打字员", "网络兼职", "礼仪模特", "其他工作"};
        new AlertDialog.Builder(mActivity).setTitle("请选择职业类型")
                .setIcon(R.drawable.ic_choice)
                .setSingleChoiceItems(type, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeQueryType(true);
                        dialog.dismiss();
                        tvType.setText(type[which]);
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void addSalaryCondition() {
        final String[] monthSalary = new String[]{"0-1000", "1000-2000", "2000-3000", "4000-5000", "5000以上"};
        final String[] daySalary = new String[]{"0-40", "40-50", "50-60", "60-70", "70-80", "80-90", "100以上"};
        final String[] mode = new String[]{"日结", "月结"};
        new AlertDialog.Builder(mActivity).setTitle("请选择结算方式")
                .setIcon(R.drawable.ic_choice)
                .setSingleChoiceItems(mode, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        changeQueryType(true);
                        dialog.dismiss();
                        tvSalary.setText(mode[which] + "-");
                        new AlertDialog.Builder(mActivity).setTitle("请选择薪资")
                                .setIcon(R.drawable.ic_choice)
                                .setSingleChoiceItems(which == 0 ? daySalary : monthSalary
                                        , 0, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which1) {
                                                dialog.dismiss();
                                                tvSalary.append(which == 0 ? daySalary[which1] : monthSalary[which1]);
                                            }
                                        }).setNegativeButton("取消", null).show();
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void addTimeCondition() {
        final String[] time = new String[]{"0-10", "11-20", "21-30", "30-40", "40-50", "50-60", "60天以上"};
        new AlertDialog.Builder(mActivity).setTitle("请选择工作天数")
                .setIcon(R.drawable.ic_choice)
                .setSingleChoiceItems(time, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeQueryType(true);
                        dialog.dismiss();
                        tvTime.setText(which == time.length - 1 ? time[which] : time[which] + "天");
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void addPlaceCondition() {
        final String[] city = new String[]{"株洲市", "长沙市", "衡阳市", "岳阳市", "永州市", "邵阳市", "怀化市", "湘潭市", "常德市", "郴州市"};
        new AlertDialog.Builder(mActivity).setTitle("请选择城市")
                .setIcon(R.drawable.ic_choice)
                .setSingleChoiceItems(city, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changeQueryType(true);
                        dialog.dismiss();
                        tvPlace.setText(city[which]);
                    }
                }).setNegativeButton("取消", null).show();
    }

    @Override
    public void changeQueryType(boolean hasLimit) {
        if (hasLimit) {
            btnQueryJob.setText("条件查询");
            isLimit = true;
        } else {
            btnQueryJob.setText("查询");
        }
    }

    @Override
    public void showLoadingView() {
        rlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadedData(List<Job> jobs) {
        if (mQueryJobAdapter.getItemCount()>0){
            mQueryJobAdapter=new QueryJobAdapter(null);
            rvQueryResult.setAdapter(mQueryJobAdapter);
        }
        LogUtils.log("添加数据"+jobs.size());
        mQueryJobAdapter.addData(jobs);
        rlLoading.setVisibility(View.GONE);
        llQueryControls.setVisibility(View.GONE);
        rlQueryData.setVisibility(View.VISIBLE);

    }

    @Override
    public void showLoadFail(String message) {
        rlLoading.setVisibility(View.GONE);
        toastShow(message);
    }

    @Override
    public void backQueryInterface() {
        rlQueryData.setVisibility(View.GONE);
        llQueryControls.setVisibility(View.VISIBLE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        tvPlace.setText(event);
    }


    @OnClick({R.id.iv_add_publish, R.id.rl_type_condition,  R.id.fb_query_back,R.id.rl_salary_balance, R.id.rl_work_time, R.id.rl_work_area, R.id.btn_query_job})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_publish:
                mvpPresenter.addNewJob();
                break;
            case R.id.rl_type_condition:
                mvpPresenter.addQueryCondition(0);
                break;
            case R.id.rl_salary_balance:
                mvpPresenter.addQueryCondition(1);
                break;
            case R.id.rl_work_time:
                mvpPresenter.addQueryCondition(2);
                break;
            case R.id.rl_work_area:
                mvpPresenter.addQueryCondition(3);
                break;
            case R.id.btn_query_job:
                mvpPresenter.query(getContentOfTextView(tvType), getContentOfTextView(tvSalary)
                        , getContentOfTextView(tvTime),getContentOfTextView(tvPlace), getContentOfTextView(etQueryInput),isLimit);
                break;
            case R.id.fb_query_back:
                mvpPresenter.continueToQuery();
                break;
        }
    }

    private String getContentOfTextView(TextView textView) {
        return textView.getText().toString();
    }


}
