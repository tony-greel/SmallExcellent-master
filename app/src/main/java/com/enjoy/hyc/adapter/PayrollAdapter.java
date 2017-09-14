package com.enjoy.hyc.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.enjoy.R;
import com.enjoy.hyc.bean.Payroll;

import java.util.List;

/**
 * Created by hyc on 2017/4/27 18:54
 */

public class PayrollAdapter extends BaseQuickAdapter<Payroll> {


    public PayrollAdapter(List<Payroll> data) {
        super(R.layout.item_payroll,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Payroll payroll) {
        baseViewHolder.setText(R.id.tv_payroll_type,payroll.getType());
        baseViewHolder.setText(R.id.tv_payroll_time,payroll.getTime());
        baseViewHolder.setText(R.id.tv_payroll_address,"地址："+payroll.getAddress());
        baseViewHolder.setText(R.id.tv_payroll_income,"+"+payroll.getIncome()+".00");
    }
}
