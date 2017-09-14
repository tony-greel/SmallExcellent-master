package com.enjoy.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoy.R;

/**
 * Created by hyc on 2017/3/31 11:29
 */

public class MyToast {
    private Toast mToast;
    private MyToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_eplay_toast, null);
        TextView textView = (TextView) v.findViewById(R.id.textView1);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    public static MyToast makeText( CharSequence text) {
        return new MyToast(SmallApplication.getContext(), text, Toast.LENGTH_SHORT);
    }
    public static MyToast makeText(Context context, CharSequence text, int duration) {
        return new MyToast(context, text, duration);
    }
    public void show() {
        if (mToast != null) {
            mToast.cancel();
            mToast.show();
        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}