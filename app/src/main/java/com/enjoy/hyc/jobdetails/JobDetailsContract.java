package com.enjoy.hyc.jobdetails;

import com.enjoy.hyc.bean.Job;

/**
 * Created by hyc on 2017/4/24 16:00
 */

public interface JobDetailsContract {

    void loadJobContent(Job job);

    void applySuccess();

    void applyFail(String error);

    void applying();

    void notLogin();

    void haveApply();

    void verifyHaveApply();

    void haveNotApply();

    void verifyError();
}
