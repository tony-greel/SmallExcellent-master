package com.enjoy.hyc.record;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.adapter.RecordAdapter;
import com.enjoy.hyc.bean.Moonlighting;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 兼职记录界面
 */

public class RecordActivity extends MvpActivity<RecordPresenter> implements RecordContract {


    @Bind(R.id.tb_record)
    Toolbar tbRecord;
    @Bind(R.id.rv_loading_record)
    RelativeLayout rvLoadingRecord;
    @Bind(R.id.rv_record)
    RecyclerView rvRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);
        mvpPresenter.attachView(this);
        setToolBar(R.id.tb_record);
        initToolBarAsHome("兼职记录");
        mvpPresenter.getRecord();
    }

    @Override
    protected RecordPresenter createPresenter() {
        return new RecordPresenter();
    }

    /**
     * 加载中的界面提示
     */
    @Override
    public void showLoading() {
        rvLoadingRecord.setVisibility(View.VISIBLE);
        rvRecord.setVisibility(View.GONE);
    }

    /**
     * 加载失败的界面提示
     */
    @Override
    public void loadingFail() {
        rvLoadingRecord.setVisibility(View.GONE);
        Toast.makeText(mActivity, "网络不给力", Toast.LENGTH_SHORT).show();
    }

    /**
     * 加载成功后进行界面显示
     * @param list  加载的数据集合
     */
    @Override
    public void loadingSuccess(List<Moonlighting> list) {
        if (list.size()==0){
            Toast.makeText(mActivity, "无记录", Toast.LENGTH_SHORT).show();
            rvLoadingRecord.setVisibility(View.GONE);
            return;
        }
        rvLoadingRecord.setVisibility(View.GONE);
        rvRecord.setLayoutManager(new LinearLayoutManager(this));
        RecordAdapter mRecordAdapter=new RecordAdapter(list);
        rvRecord.setAdapter(mRecordAdapter);
        rvRecord.setVisibility(View.VISIBLE);
    }
}
