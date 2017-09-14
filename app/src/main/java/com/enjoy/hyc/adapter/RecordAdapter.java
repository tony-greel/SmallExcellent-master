package com.enjoy.hyc.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.enjoy.R;
import com.enjoy.base.LogUtils;
import com.enjoy.hyc.bean.Moonlighting;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 兼职记录适配器 Created by hyc on 2017/4/26 20:51
 */

public class RecordAdapter extends BaseQuickAdapter<Moonlighting> {

    private int year;
    private int month;
    private int day;
    public RecordAdapter(List<Moonlighting> data) {
        super(R.layout.item_record,data);
        Calendar calendar=Calendar.getInstance();  //获取当前时间，作为图标的名字
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH)+1;
        day=calendar.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Moonlighting moonlighting) {
        String time=moonlighting.getJob().getDeadline();
        int yearTime=Integer.parseInt(time.split("/")[0]);
        int monthTime=Integer.parseInt(time.split("/")[1]);
        int dayTime=Integer.parseInt(time.split("/")[2]);
        int interval=moonlighting.getJob().getWorkDayTime();
        //截止日期大于当前日期即还未开始工作，所以标签为待审核
        if (year<yearTime || yearTime==yearTime && month<monthTime
                || yearTime==yearTime && monthTime == month && day <= dayTime){
            Glide.with(mContext)
                    .load(R.drawable.ic_hava_wait)
                    .into((ImageView) baseViewHolder.getView(R.id.iv_process_state));
        }else {
            long temp= System.currentTimeMillis() - interval*24L*60L*60L*1000L;
            String strDate=new SimpleDateFormat("yyyy/MM/dd").format(new Date(temp));
            //工作结束日期大于当前日期，所以标签为已完成
            if (!compareString(moonlighting.getJob().getDeadline(),strDate)){
                Glide.with(mContext)
                        .load(R.drawable.ic_have_over)
                        .into((ImageView) baseViewHolder.getView(R.id.iv_process_state));
            }else {
                Glide.with(mContext)
                        .load(R.drawable.ic_have_hand)
                        .into((ImageView) baseViewHolder.getView(R.id.iv_process_state));
            }
        }
        baseViewHolder.setText(R.id.tv_item_record_time,moonlighting.getJob().getDeadline());
        baseViewHolder.setText(R.id.tv_record_name,moonlighting.getJob().getJobDetailName());
        baseViewHolder.setText(R.id.tv_record_city,moonlighting.getJob().getCityName());
        baseViewHolder.setText(R.id.tv_record_interval,moonlighting.getJob().getWorkDayTime()+"天");

    }

    private boolean compareString(String date,String time){
        return  Integer.parseInt(date.split("/")[0])>Integer.parseInt(time.split("/")[0])
                || Integer.parseInt(date.split("/")[0])==Integer.parseInt(time.split("/")[0])
                && Integer.parseInt(date.split("/")[1])> Integer.parseInt(time.split("/")[1])
                || Integer.parseInt(date.split("/")[0])==Integer.parseInt(time.split("/")[0])
                && Integer.parseInt(date.split("/")[1])==Integer.parseInt(time.split("/")[1])
                && Integer.parseInt(date.split("/")[2])>=Integer.parseInt(time.split("/")[2]);
    }
}
