package com.enjoy.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoy.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hyc on 2017/4/10 15:27
 */

public class BaseActivity extends AppCompatActivity {

    private static List<BaseActivity> activities;

    public boolean isSpreadsFinish=true;

    public Toolbar mToolbar;

    private CompositeSubscription mCompositeSubscription;

    public Activity mActivity;

    public boolean isNoStateColor=true;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mActivity = this;
        if (isNoStateColor){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.stateColor));
        }
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
        mActivity = this;

    }



    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void onUnsubscribe() {
        //取消注册，以避免内存泄露
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
            mCompositeSubscription.unsubscribe();
    }

    public void setToolBar(int toolId){
        Toolbar toolbar = (Toolbar) findViewById(toolId);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            this.mToolbar=toolbar;
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    public void initToolBarText(int tvId,String title) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            TextView toolbarTitle = (TextView) mToolbar.findViewById(tvId);
            toolbarTitle.setText(title);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    public void initToolBarAsHome(String title) {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setTitle(title);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void toastShow(int resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toastShow(String resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("加载中");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }

    public void logD(String msg){
        String TAG=mActivity==null?"TAG":mActivity.getLocalClassName();
        Log.d(TAG,msg);
    }

    public void logD(String TAG,String msg){
        Log.d(TAG,msg);
    }

    public void addActivity(BaseActivity baseActivity){
        LogUtils.log("添加activity"+baseActivity.getLocalClassName());
        if (activities==null){
            activities=new ArrayList<>();
        }
        activities.add(baseActivity);
    }

    public void finishAll(){
        LogUtils.log("存在的activity数目"+activities.size());
        for (BaseActivity activity:activities){
            if (activity!=null){
                activity.finish();
                LogUtils.log("finish activity"+activity.getLocalClassName());
            }
        }
    }
    private float downX;
    private float distance=0;
    private long downTime=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isSpreadsFinish){
            return super.onTouchEvent(event);
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX=event.getX();
                downTime=System.currentTimeMillis();
                return false;
            case MotionEvent.ACTION_MOVE:
                distance=event.getX()-downX;
                return event.getX() - downX > event.getY();

            case MotionEvent.ACTION_UP:
                if (distance>200&&(System.currentTimeMillis()-downTime)<300){
                    finish();
                    return true;
                }else {
                    distance=0;
                    return false;
                }
        }
        return super.onTouchEvent(event);
    }
}
