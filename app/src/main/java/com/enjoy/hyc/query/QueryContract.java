package com.enjoy.hyc.query;

import com.enjoy.hyc.bean.Job;

import java.util.List;

/**
 * Created by hyc on 2017/4/22 11:25
 */

public interface QueryContract {

    void addNewJobInformation();

    void addTypeCondition();

    void addSalaryCondition();

    void addTimeCondition();

    void addPlaceCondition();

    void changeQueryType(boolean hasLimit);

    void showLoadingView();

    void showLoadedData(List<Job> jobs);

    void showLoadFail(String message);

    void backQueryInterface();

}
