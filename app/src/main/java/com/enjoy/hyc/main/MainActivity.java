package com.enjoy.hyc.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.enjoy.R;
import com.enjoy.base.LogUtils;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.map.MapDetailsFragment;
import com.enjoy.hyc.map.MapPresenter;
import com.enjoy.hyc.personal.PersonalFragment;
import com.enjoy.hyc.query.QueryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * 主界面 Create by hyc
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainContract {


    @Bind(R.id.iv_map)
    ImageView ivMap;
    @Bind(R.id.rl_location_fragment)
    RelativeLayout rlLocationFragment;
    @Bind(R.id.iv_query)
    ImageView ivQuery;
    @Bind(R.id.rl_query_part_time)
    RelativeLayout rlQueryPartTime;
    @Bind(R.id.iv_personal)
    ImageView ivPersonal;
    @Bind(R.id.rl_personal_fragment)
    RelativeLayout rlPersonalFragment;
    @Bind(R.id.ll_main_button)
    LinearLayout llMainButton;
    @Bind(R.id.fl_main_fragment)
    FrameLayout flMainFragment;
    @Bind(R.id.fb_main_refresh)
    FloatingActionButton fbMainRefresh;
    private List<Fragment> fragments;

    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mvpPresenter.attachView(this);
        mvpPresenter.initFragment();
        addActivity(this);
        isSpreadsFinish=false;

    }

    @Override
    protected MainPresenter createPresenter() {
        return MainPresenter.getMainPresenter();
    }

    /**
     * 进入activity默认加载第一个fragment
     */
    @Override
    public void setDefaultFragment() {
        fragments = new ArrayList<>();
        fragments.add(new MapDetailsFragment());
        fragments.add(new QueryFragment());
        fragments.add(new PersonalFragment());
        FragmentManager mainFragmentManager = getFragmentManager();
        FragmentTransaction transaction = mainFragmentManager.beginTransaction();
        transaction.replace(R.id.fl_main_fragment, fragments.get(0));
        transaction.commit();
        /**
         * 监听软键盘的弹出和收缩
         */
        flMainFragment.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int heightDiff = flMainFragment.getRootView()
                                .getHeight() - flMainFragment.getHeight();
                        if (heightDiff > 500) {
                            mvpPresenter.isHideBottom(false);
                        } else {
                            mvpPresenter.isHideBottom(true);
                        }
                    }
                });
    }

    /**
     * 替换当前显示的fragment
     * @param position 替换fragment在fragments集合中的位置
     */
    @Override
    public void replaceFragment(int position) {
        if (position != currentPosition) {
            if (position == 0) {
                fragments.remove(0);
                fragments.add(0, new MapDetailsFragment());
            } else if (currentPosition == 0 & MapPresenter.mMapView != null) {
                MapPresenter.mMapView.setVisibility(View.GONE);
                MapPresenter.mMapView.onDestroy();
                MapPresenter.mMapView = null;
            }
            FragmentManager mainFragmentManager = getFragmentManager();
            FragmentTransaction transaction = mainFragmentManager.beginTransaction();
            transaction.replace(R.id.fl_main_fragment, fragments.get(position));
            currentPosition = position;
            transaction.commit();
        }
    }

    /**
     * 替换fragment时底部按钮的颜色改变
     * @param position
     */
    @Override
    public void refreshImageSrc(int position) {
        ivMap.setImageResource(R.drawable.ic_map_normal);
        ivPersonal.setImageResource(R.drawable.ic_personal_normal);
        ivQuery.setImageResource(R.drawable.ic_query_normal);
        switch (position) {
            case 0:
                ivMap.setImageResource(R.drawable.ic_map_selected);
                break;
            case 1:
                ivQuery.setImageResource(R.drawable.ic_query_selected);
                break;
            case 2:
                ivPersonal.setImageResource(R.drawable.ic_personal_selected);
                break;
        }
    }

    /**
     * 获取当前位置信息
     */
    @Override
    public synchronized void getLocationMessage() {
        LogUtils.log("开始定位");
        AMapLocationClient mLocationClient = null;
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        //设置为高精度
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置默认返回地址
        locationClientOption.setNeedAddress(true);
        //设置是否只定位一次
        locationClientOption.setOnceLocation(true);
        if (locationClientOption.isOnceLocation()) {
            locationClientOption.setOnceLocationLatest(true);
        }
        //设置是否强制刷新WiFi
        locationClientOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        locationClientOption.setMockEnable(true);
        //设置定位间隔,单位毫秒,默认为2000ms
        locationClientOption.setInterval(2000);
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //为定位进行设置
        mLocationClient.setLocationOption(locationClientOption);
        //设置定位回调监听
        mLocationClient.setLocationListener(mvpPresenter);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 判断底部按钮布局是否显示
     * @param isShow true 即显示  false 隐藏
     */
    @Override
    public void isVisibleBottomLayout(boolean isShow) {
        llMainButton.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * 定位失败则显示刷新位置信息的按钮
     */
    @Override
    public void positioningFail() {
        fbMainRefresh.setVisibility(View.VISIBLE);
    }

    /**
     * 通过按钮定位成功后则隐藏刷新位置按钮
     */
    @Override
    public void positioningSuccess() {
        fbMainRefresh.setVisibility(View.GONE);
    }

    @OnClick({R.id.rl_location_fragment, R.id.rl_query_part_time, R.id.rl_personal_fragment, R.id.fb_main_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_location_fragment:
                mvpPresenter.replaceFragment(0);
                break;
            case R.id.rl_query_part_time:
                mvpPresenter.replaceFragment(1);
                break;
            case R.id.rl_personal_fragment:
                mvpPresenter.replaceFragment(2);
                break;
            case R.id.fb_main_refresh:
                mvpPresenter.getLocation();
                break;
        }
    }
}
