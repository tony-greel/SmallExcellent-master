package com.enjoy.hyc.record;

import com.enjoy.hyc.bean.Moonlighting;

import java.util.List;

/**
 * Created by hyc on 2017/4/26 18:52
 */

public interface RecordContract {

    void showLoading();

    void loadingFail();

    void loadingSuccess(List<Moonlighting> list);

}
