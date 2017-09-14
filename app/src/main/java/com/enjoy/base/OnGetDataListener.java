package com.enjoy.base;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by hyc on 2017/4/11 10:33
 */

public abstract class OnGetDataListener<T> extends QueryListener<T> {

    public abstract void successful(T object);

    public abstract void fail(String error);

    @Override
    public void done(T t, BmobException e) {
        if (e==null){
            successful(t);
        }else {
            fail(e.getMessage());
        }
    }
}
