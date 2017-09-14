package com.enjoy.hyc.footprint;

import com.enjoy.hyc.bean.JobCache;

import java.util.List;

/**
 * Created by hyc on 2017/4/26 15:50
 */

public interface FootprintContract {

    void initRecycleViewData(List<JobCache> browses);

    void noFootprintData();

    void isDeleteAllFootprint();
}
