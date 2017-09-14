package com.enjoy.hyc.payroll;

import com.enjoy.hyc.bean.Payroll;

import java.util.List;

/**
 * Created by hyc on 2017/4/27 10:04
 */

public interface PayrollContract {

    void showLoadingView();

    void loadComplete(List<Payroll> payrolls);

    void noPayroll();

    void loadFail();

}
