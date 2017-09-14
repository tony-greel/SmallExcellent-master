package com.enjoy.hyc.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.enjoy.R;
import com.enjoy.hyc.bean.JobCache;
import com.enjoy.hyc.jobdetails.JobDetailsActivity;
import com.enjoy.hyc.jobdetails.JobDetailsPresenter;

import java.util.List;

/**
 * 足迹界面RecycleView的适配器 Created by hyc on 2017/4/26 16:19
 */

public class FootprintAdapter extends BaseQuickAdapter<JobCache> {


    public FootprintAdapter(List<JobCache> data) {
        super(R.layout.item_browse,data);
    }

    /**
     * 给当前显示的item加载数据
     * @param baseViewHolder 当先item的ViewHolder通过它可以获取item中的View对象 加载的方法如下所示
     * @param jobCache 当前item的数据对象
     */
    @Override
    protected void convert(BaseViewHolder baseViewHolder, final JobCache jobCache) {
        baseViewHolder.setText(R.id.tv_footprint_name,jobCache.getJobDetailName());
        baseViewHolder.setText(R.id.tv_footprint_time,jobCache.getTime());
        /**
         * 点击足迹进入兼职工作详情界面 JobDetailsPresenter.jobContent是一个静态对象，用来装载工作详情界面的数据对象
         */
        baseViewHolder.setOnClickListener(R.id.rl_item_footprint, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobDetailsPresenter.jobContent=jobCache.toJob();
                mContext.startActivity(new Intent(mContext, JobDetailsActivity.class));
            }
        });
    }
}
