package com.enjoy.hyc.payroll;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.adapter.PayrollAdapter;
import com.enjoy.hyc.bean.Payroll;
import com.enjoy.hyc.util.PayrollUtil;
import com.mingle.widget.LoadingView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PayrollActivity extends MvpActivity<PayrollPresenter> implements PayrollContract {


    @Bind(R.id.tb_payroll)
    Toolbar tbPayroll;
    @Bind(R.id.lv_payroll)
    LoadingView lvPayroll;
    @Bind(R.id.tv_all_total)
    TextView tvAllTotal;
    @Bind(R.id.tv_today_income)
    TextView tvTodayIncome;
    @Bind(R.id.tv_leaflet_income)
    TextView tvLeafletIncome;
    @Bind(R.id.tv_psq_income)
    TextView tvPsqIncome;
    @Bind(R.id.tv_fraulein_income)
    TextView tvFrauleinIncome;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.tv_sales_income)
    TextView tvSalesIncome;
    @Bind(R.id.tv_artist_income)
    TextView tvArtistIncome;
    @Bind(R.id.tv_typing_income)
    TextView tvTypingIncome;
    @Bind(R.id.tv_service_income)
    TextView tvServiceIncome;
    @Bind(R.id.tv_net_income)
    TextView tvNetIncome;
    @Bind(R.id.tv_protocol_income)
    TextView tvProtocolIncome;
    @Bind(R.id.tv_other_income)
    TextView tvOtherIncome;
    @Bind(R.id.rv_income_detail)
    RecyclerView rvIncomeDetail;
    @Bind(R.id.nv_payroll_detail)
    NestedScrollView nvPayrollDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payroll);
        ButterKnife.bind(this);
        mvpPresenter.attachView(this);
        setToolBar(R.id.tb_payroll);
        initToolBarAsHome("工资单");
        mvpPresenter.showPayrollDetail();
    }

    @Override
    protected PayrollPresenter createPresenter() {
        return new PayrollPresenter();
    }

    /**
     * 加载中的提示界面
     */
    @Override
    public void showLoadingView() {
        lvPayroll.setVisibility(View.VISIBLE);
        nvPayrollDetail.setVisibility(View.GONE);
    }

    /**
     * 将加载完成的工资单显示出来
     * @param payrolls  每个兼职工作的信息
     */
    @Override
    public void loadComplete(List<Payroll> payrolls) {
        tvAllTotal.setText(PayrollUtil.countTotal(payrolls)+".00");
        tvTodayIncome.setText(PayrollUtil.countTodayIncome(payrolls)+".00");
        tvLeafletIncome.setText(PayrollUtil.countIncomeType("传单","发单人员",payrolls)+".00");
        tvPsqIncome.setText(PayrollUtil.countIncomeType("问卷","问卷调查",payrolls)+".00");
        tvFrauleinIncome.setText(PayrollUtil.countIncomeType("家教","家庭教师",payrolls)+".00");
        tvSalesIncome.setText(PayrollUtil.countIncomeType("促销","销售",payrolls)+".00");
        tvArtistIncome.setText(PayrollUtil.countIncomeType("美工","美术策划",payrolls)+".00");
        tvTypingIncome.setText(PayrollUtil.countIncomeType("打字","打字员",payrolls)+".00");
        tvServiceIncome.setText(PayrollUtil.countIncomeType("服务","服务员",payrolls)+".00");
        tvNetIncome.setText(PayrollUtil.countIncomeType("网络","刷单",payrolls)+".00");
        tvProtocolIncome.setText(PayrollUtil.countIncomeType("礼仪","礼仪小姐",payrolls)+".00");
        tvOtherIncome.setText(PayrollUtil.countOther(payrolls)+".00");
        rvIncomeDetail.setLayoutManager(new LinearLayoutManager(this));
        PayrollAdapter payrollAdapter=new PayrollAdapter(payrolls);
        rvIncomeDetail.setAdapter(payrollAdapter);
        lvPayroll.setVisibility(View.GONE);
        nvPayrollDetail.setVisibility(View.VISIBLE);
    }

    /**
     * 没有兼职工资记录的界面显示
     */
    @Override
    public void noPayroll() {
        lvPayroll.setVisibility(View.GONE);
        nvPayrollDetail.setVisibility(View.VISIBLE);
    }

    /**
     * 加载失败的界面提示
     */
    @Override
    public void loadFail() {
        lvPayroll.setVisibility(View.GONE);
        Toast.makeText(mActivity, "网络不给力", Toast.LENGTH_SHORT).show();
    }
}
