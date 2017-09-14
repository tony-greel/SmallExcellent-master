package com.enjoy.hyc.footprint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.adapter.FootprintAdapter;
import com.enjoy.hyc.bean.JobCache;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FootprintActivity extends MvpActivity<FootprintPresenter> implements FootprintContract {


    @Bind(R.id.tb_footprint)
    Toolbar tbFootprint;
    @Bind(R.id.tv_no_footprint)
    TextView tvNoFootprint;
    @Bind(R.id.pv_footprint)
    PullLoadMoreRecyclerView pvFootprint;
    private FootprintAdapter mFootprintAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_footprint);
        ButterKnife.bind(this);
        mvpPresenter.attachView(this);
        setToolBar(R.id.tb_footprint);
        initToolBarAsHome("足迹");
        mvpPresenter.initFootprintData();
    }


    @Override
    protected FootprintPresenter createPresenter() {
        return new FootprintPresenter();
    }

    /**
     * 初始化RecycleView的数据
     * @param jobs 数据集合
     */
    @Override
    public void initRecycleViewData(List<JobCache> jobs) {
        pvFootprint.setLinearLayout();
        pvFootprint.setPullRefreshEnable(false);
        pvFootprint.setPushRefreshEnable(false);
        mFootprintAdapter = new FootprintAdapter(jobs);
        pvFootprint.setAdapter(mFootprintAdapter);
    }

    /**
     * 没有足迹记录的界面提示
     */
    @Override
    public void noFootprintData() {
        pvFootprint.setVisibility(View.GONE);
        tvNoFootprint.setVisibility(View.VISIBLE);
    }

    /**
     * 提示用户是否删除所有足迹信息
     */
    @Override
    public void isDeleteAllFootprint() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_alert)
                .setTitle("提示")
                .setMessage("是否删除记录?")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mvpPresenter.deleteAll();
                            }
                        }).setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    /**
     * 创建菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_footprint,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 菜单的选择事件，即删除菜单的选项
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete:mvpPresenter.isDelete();break;
        }
        return super.onOptionsItemSelected(item);
    }
}
