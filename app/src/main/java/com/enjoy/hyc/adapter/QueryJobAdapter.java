package com.enjoy.hyc.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.enjoy.R;
import com.enjoy.hyc.bean.Job;
import com.enjoy.hyc.jobdetails.JobDetailsActivity;
import com.enjoy.hyc.jobdetails.JobDetailsPresenter;
import com.enjoy.hyc.util.JobCacheUtil;

import java.util.List;

/**
 * Created by hyc on 2017/4/23 14:20
 */

public class QueryJobAdapter extends BaseQuickAdapter<Job> {


    public QueryJobAdapter(List<Job> data) {
        super(R.layout.item_query_job,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final Job job) {
        try {
            baseViewHolder.setText(R.id.tv_name,job.getJobType());
            /**显示当前item兼职工作的薪资状况，通过split切割可以知道其属于月结还是日结*/
            baseViewHolder.setText(R.id.tv_money,job.getJobSalary()+
                    "/"+job.getBalanceMode().split("结")[0]);
            baseViewHolder.setText(R.id.tv_find_place,job.getCityName());
            baseViewHolder.getView(R.id.ll_job_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JobDetailsPresenter.jobContent=job;
                    JobCacheUtil.produceNewBrowse(job);//产生一个浏览记录
                    mContext.startActivity(new Intent(mContext, JobDetailsActivity.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            getData().remove(job);
        }
    }


}
