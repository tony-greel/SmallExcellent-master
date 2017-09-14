package com.enjoy.base;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hyc on 2017/4/10 15:29
 */

public class BasePresenter<V> {
    public V mvpView;
    private CompositeSubscription mCompositeSubscription;

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }


    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }


    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
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
}
