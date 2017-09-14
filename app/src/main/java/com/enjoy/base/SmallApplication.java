package com.enjoy.base;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;
import cn.bmob.v3.Bmob;

/**
 * Created by hyc on 2017/4/10 20:22
 */

public class SmallApplication extends Application{
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "0f0f549a85e06ef1fe438b03e73347ed");
        context=getApplicationContext();
        LitePal.initialize(this);
    }


}
