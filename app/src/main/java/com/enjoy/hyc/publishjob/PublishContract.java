package com.enjoy.hyc.publishjob;

import com.enjoy.hyc.bean.Job;

/**
 * Created by hyc on 2017/4/21 22:11
 */

public interface PublishContract {

    Job getNewJobInfo();

    boolean IsFillAllEditText();

    void showLoading();

    void loadComplete(boolean isSuccess);
}
