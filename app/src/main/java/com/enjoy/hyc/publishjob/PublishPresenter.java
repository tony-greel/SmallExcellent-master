package com.enjoy.hyc.publishjob;

import android.widget.Toast;

import com.enjoy.base.BasePresenter;
import com.enjoy.base.SmallApplication;
import com.enjoy.hyc.util.JobUtil;

/**
 * Created by hyc on 2017/4/21 22:17
 */

public class PublishPresenter extends BasePresenter<PublishContract> {


    /**
     * commit a new part-time information into the server
     */
    public void commitANewJob(){
        if (!mvpView.IsFillAllEditText()){
            mvpView.showLoading();
            JobUtil.publishNewJob(mvpView.getNewJobInfo(), new JobUtil.OnPublishNewJobListener() {
                @Override
                public void success(String objectId) {
                    mvpView.loadComplete(true);
                }

                @Override
                public void fail(String error) {
                    mvpView.loadComplete(false);
                    Toast.makeText(SmallApplication.getContext(), "提交失败，稍后再试!", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            Toast.makeText(SmallApplication.getContext(), "请完善各项信息", Toast.LENGTH_SHORT).show();
        }
    }

}
