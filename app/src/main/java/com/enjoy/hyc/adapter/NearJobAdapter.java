package com.enjoy.hyc.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.enjoy.R;
import com.enjoy.hyc.bean.Job;

import java.util.List;

/**
 * 地图模块中对应RecycleView 的适配器，主要用来显示附近所有的兼职工作，范围为一个市 Created by hyc on 2017/4/24 17:04
 */

public class NearJobAdapter extends BaseQuickAdapter<Job>{

    public void setOnNearJobItemClick(OnNearJobItemClick onNearJobItemClick) {
        this.onNearJobItemClick = onNearJobItemClick;
    }

    /**
     * 点击item的监听 一个item对应有三个点击方式
     * TYPE_LOCATION 是获取这个兼职的位置信息并通过mapView显示出来
     * TYPE_NAVIGATION 是进行导航的点击事件
     * TYPE_DETAILS 是进入工作详情的点击事件
     */
    public interface OnNearJobItemClick{
        int TYPE_LOCATION=0x11;
        int TYPE_NAVIGATION=0x12;
        int TYPE_DETAILS=0x13;
        void onClick(int type,Job job);
    }

    /**
     * item点击对象监听，可通过其将presenter和适配器的点击事件关联起来
     */
    private OnNearJobItemClick onNearJobItemClick;

    /**
     *
     * @param data RecycleView 的数据集合
     */
    public NearJobAdapter(List<Job> data) {
        super(R.layout.item_near_job,data);
    }

    /**
     * 与第一个适配器相同，不理解可以去了解一下RecycleView的用法和BaseQuickAdapter开源库的用法
     * @param baseViewHolder
     * @param job
     */
    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final Job job) {
        baseViewHolder.setText(R.id.item_job_name,job.getJobType());
        baseViewHolder.setText(R.id.item_job_area,job.getJobRegion());
        baseViewHolder.setText(R.id.item_job_number,job.getRecruitmentNumber()+"人");
        baseViewHolder.setText(R.id.item_salary,job.getJobSalary()+"/"+job.getBalanceMode().split("结")[0]);
        baseViewHolder.setOnClickListener(R.id.iv_near, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNearJobItemClick!=null){
                    onNearJobItemClick.onClick(OnNearJobItemClick.TYPE_LOCATION,job);
                }
            }
        });
        baseViewHolder.setOnClickListener(R.id.iv_navigation, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNearJobItemClick!=null){
                    onNearJobItemClick.onClick(OnNearJobItemClick.TYPE_NAVIGATION,job);
                }
            }
        });
        baseViewHolder.setOnClickListener(R.id.item_job_details, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNearJobItemClick!=null){
                    onNearJobItemClick.onClick(OnNearJobItemClick.TYPE_DETAILS,job);
                }
            }
        });

    }
}
