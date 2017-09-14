package com.enjoy.hyc.main;


/**
 * Created by hyc on 2017/4/18 11:02
 */

public interface MainContract {

    void setDefaultFragment();

    void replaceFragment(int position);

    void refreshImageSrc(int position);

    void getLocationMessage();

    void isVisibleBottomLayout(boolean isShow);

    void positioningFail();

    void positioningSuccess();

}
