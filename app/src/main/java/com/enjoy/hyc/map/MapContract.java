package com.enjoy.hyc.map;

import android.widget.ListView;

import com.enjoy.hyc.adapter.NearJobAdapter;
import com.enjoy.hyc.bean.Job;

import java.util.List;

/**
 * Created by hyc on 2017/4/19 20:23
 */

public interface MapContract {


    void initMap();

    ListView getListView();

    List<String> getQueryResults();

    void showDialog();

    void dismissDialog();

    void getLocationByName(final String name);

    void startRoutePlan();

    void loadNearJobInformationComplete(List<Job> jobs);

    void setItemClickListener(NearJobAdapter.OnNearJobItemClick onNearJobItemClick);

    void startDetailsView(Job job);
}
