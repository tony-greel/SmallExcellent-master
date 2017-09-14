package com.enjoy.hyc.map;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.enjoy.base.BasePresenter;
import com.enjoy.base.LogUtils;
import com.enjoy.base.SmallApplication;
import com.enjoy.hyc.adapter.NearJobAdapter;
import com.enjoy.hyc.bean.Job;
import com.enjoy.hyc.util.JobUtil;

import java.util.List;

/**
 * Created by hyc on 2017/4/19 20:27
 */

public class MapPresenter extends BasePresenter<MapContract> implements AdapterView.OnItemClickListener{

    public static com.amap.api.maps.MapView mMapView=null;
    /**
     * 判断用户是否选择路线规划的目的地  true 选择了目的地可进行路线规划，false 不进行路线规划
     */
    private boolean isSelectedDestination=false;
    /**
     * 判断用户点击的类型是否是导航（路线规划）
     */
    public boolean isType_navigation=false;
    /**
     * 判断是否获取到了当前位置的城市名
     */
    public boolean isGetCityName=false;

    /**
     * 初始化地图View
     * @param name
     */
    public void initMapView(String name){
        mvpView.initMap();
        mvpView.getListView().setOnItemClickListener(this);
        if (isGetCityName=true){
            loadNearData(name);
        }
    }

    /**
     * listView的item点击事件
     * @param parent
     * @param view
     * @param position  点击item对应在数据集合中的位置
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getLocation(mvpView.getQueryResults().get(position));
    }

    /**
     * 进入路线规划界面
     */
    public void enterRoutePlan(){
        if (isSelectedDestination){
            mvpView.startRoutePlan();
        }
    }

    /**
     * 通过城市名加载附近兼职工作数据
     * @param name 当前城市名
     */
    public void loadNearData(String name){
        isGetCityName=true;
        mvpView.showDialog();
        JobUtil.getJobInfoByCityName(name, new JobUtil.OnGetJobInfoListener() {
            @Override
            public void success(List<Job> jobs) {
                if (jobs.size()>0){
                    mvpView.loadNearJobInformationComplete(jobs);
                }
                mvpView.dismissDialog();
            }

            @Override
            public void fail(String error) {
                mvpView.dismissDialog();
                Toast.makeText(SmallApplication.getContext(), "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 添加RecycleView item的点击事件监听  type为点击事件的类型
     */
    public void setListenerForAdapter(){
        mvpView.setItemClickListener(new NearJobAdapter.OnNearJobItemClick() {
            @Override
            public void onClick(int type,Job job) {
                switch (type){
                    case TYPE_DETAILS:
                        mvpView.startDetailsView(job);
                        break;
                    case TYPE_LOCATION:
                        getLocation(job.getJobRegion());
                        break;
                    case TYPE_NAVIGATION:
                        isType_navigation=true;
                        isSelectedDestination=true;
                        getLocation(job.getJobRegion());
                        break;
                }
            }
        });
    }

    /**
     * 获取当前位置的城市名
     * @param name 城市名
     */
    public void getLocation(String name){
        mvpView.showDialog();
        mvpView.getLocationByName(name);
        isSelectedDestination=true;
    }
}
