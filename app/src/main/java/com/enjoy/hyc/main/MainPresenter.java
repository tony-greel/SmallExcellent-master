package com.enjoy.hyc.main;

import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.enjoy.base.BasePresenter;
import com.enjoy.base.LogUtils;
import com.enjoy.base.SmallApplication;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by hyc on 2017/4/18 11:03
 */

public class MainPresenter extends BasePresenter<MainContract> implements AMapLocationListener {

    /**
     * 静态presenter对象用于其他界面获取定位信息
     */
    private static MainPresenter mainPresenter;
    /**
     * 当前定位城市名
     */
    public String currentCityName;

    /**
     * 获取静态presenter对象
     * @return
     */
    public synchronized static MainPresenter getMainPresenter(){
        if (mainPresenter==null){
            mainPresenter=new MainPresenter();
        }
        return mainPresenter;
    }

    /**
     * 初始化fragment
     */
    public void initFragment(){
        mvpView.getLocationMessage();
        mvpView.refreshImageSrc(0);
        mvpView.setDefaultFragment();
    }

    /**
     * 替换fragment
     * @param position
     */
    public void replaceFragment(int position){
        mvpView.refreshImageSrc(position);
        mvpView.replaceFragment(position);
    }

    /**
     * 位置改变监听
     * @param aMapLocation 位置信息对象
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode()==0){
            currentCityName=aMapLocation.getCity();
            Toast.makeText(SmallApplication.getContext(), "当前位置："+aMapLocation.getAddress(), Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post(aMapLocation.getCity());
            mvpView.positioningSuccess();
        }else {
            Toast.makeText(SmallApplication.getContext(), "定位失败,稍后再试", Toast.LENGTH_SHORT).show();
            mvpView.positioningFail();
        }
    }

    /**
     * 判断是否需要隐藏底部按钮  如果软键盘弹出则隐藏，未弹出则显示
     * @param is
     */
    public void isHideBottom(boolean is){
        mvpView.isVisibleBottomLayout(is);
    }

    /**
     * 获取位置信息
     */
    public void getLocation(){
        mvpView.getLocationMessage();
    }
}

