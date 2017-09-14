package com.enjoy.hyc.editresume;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enjoy.R;
import com.enjoy.base.MvpActivity;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.resume.ResumeActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hyc on 2017/4/26 10:59
 */

public class EditResumeActivity extends MvpActivity<EditResumePresenter> implements EditResumeContract {
    @Bind(R.id.iv_exit_edit)
    ImageView ivExitEdit;
    @Bind(R.id.tv_save_edit)
    TextView tvSaveEdit;
    @Bind(R.id.iv_old_head)
    ImageView ivOldHead;
    @Bind(R.id.iv_shooting)
    ImageView ivShooting;
    @Bind(R.id.et_edit_name)
    EditText etEditName;
    @Bind(R.id.et_edit_sex)
    EditText etEditSex;
    @Bind(R.id.et_edit_age)
    EditText etEditAge;
    @Bind(R.id.et_edit_height)
    EditText etEditHeight;
    @Bind(R.id.et_edit_province)
    EditText etEditProvince;
    @Bind(R.id.et_edit_city)
    EditText etEditCity;
    @Bind(R.id.et_edit_area)
    EditText etEditArea;
    @Bind(R.id.et_edit_school)
    EditText etEditSchool;
    @Bind(R.id.et_edit_qq)
    EditText etEditQq;
    @Bind(R.id.et_edit_self)
    EditText etEditSelf;
    @Bind(R.id.et_edit_objective)
    EditText etEditObjective;

    private static final int IMAGE = 0x018;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNoStateColor = false;
        setContentView(R.layout.layout_resume_edit);
        mvpPresenter.attachView(this);
        ButterKnife.bind(this);
        mvpPresenter.loadOldResume();
    }

    @Override
    protected EditResumePresenter createPresenter() {
        return new EditResumePresenter();
    }

    /**
     * 获取更新的用户对象
     * @return 需要更新的用户信息
     */
    @Override
    public User getUpdateUser() {
        showProgressDialog();
        User user = new User();
        user.setName(etEditName.getText().toString());
        user.setSex(etEditSex.getText().toString());
        user.setAge(etEditAge.getText().toString());
        if (TextUtils.isEmpty(etEditHeight.getText().toString())){
            user.setHeight(0);
        }else {
            user.setHeight(Integer.parseInt(etEditHeight.getText().toString()));
        }
        user.setCity(etEditProvince.getText().toString() + "-" + etEditCity.getText().toString() + "-" + etEditArea.getText().toString());
        user.setSchool(etEditSchool.getText().toString());
        user.setQqNumber(etEditQq.getText().toString());
        user.setSelfInfo(etEditSelf.getText().toString());
        user.setObjective(etEditObjective.getText().toString());
        return user;
    }

    /**
     * 显示当前用户简历信息
     * @param user 当前用户
     */
    @Override
    public void showCurrentUserResume(User user) {
        if (!TextUtils.isEmpty(user.getHeadImageUrl())) {
            Glide.with(mActivity)
                    .load(user.getHeadImageUrl())
                    .into(ivOldHead);
        }

        etEditName.setText(TextUtils.isEmpty(user.getName()) ? "" : user.getName());
        etEditSex.setText(TextUtils.isEmpty(user.getSex()) ? "" : user.getSex());
        etEditAge.setText(TextUtils.isEmpty(user.getAge()) ? "" : user.getAge());
        etEditHeight.setText(user.getHeight() == 0 ? "" : user.getHeight() + "");
        if (!TextUtils.isEmpty(user.getCity())) {
            etEditProvince.setText(TextUtils.isEmpty(user.getCity().split("-")[0]) ? "" : user.getCity().split("-")[0]);
            etEditCity.setText(TextUtils.isEmpty(user.getCity().split("-")[1]) ? "" : user.getCity().split("-")[1]);
            etEditArea.setText(TextUtils.isEmpty(user.getCity().split("-")[2]) ? "" : user.getCity().split("-")[2]);
        }
        etEditSchool.setText(TextUtils.isEmpty(user.getSchool()) ? "" : user.getSchool());
        etEditQq.setText(TextUtils.isEmpty(user.getQqNumber()) ? "" : user.getQqNumber());
        etEditSelf.setText(TextUtils.isEmpty(user.getSelfInfo()) ? "" : user.getSelfInfo());
        etEditObjective.setText(TextUtils.isEmpty(user.getObjective()) ? "" : user.getObjective());
    }

    /**
     * 修改成功的界面提示
     */
    @Override
    public void showEditSuccess() {
        dismissProgressDialog();
        Toast.makeText(mActivity, "修改成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ResumeActivity.class));
        finish();
    }

    /**
     * 修改失败的界面提示
     */
    @Override
    public void showEditFail() {
        dismissProgressDialog();
        Toast.makeText(mActivity, "修改失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 调用系统系统相册
     */
    @Override
    public void callSystemPhotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);
    }

    /**
     * 展示缩放图片
     * @param path 图片地址
     */
    @Override
    public void showZoomPhoto(String path) {
        Glide.with(mActivity)
                .load(path)
                .into(ivShooting);
        dismissProgressDialog();

    }

    /**
     * 点击事件
     * @param view iv_exit_edit退出界面按钮  tv_save_edit保存编辑后的控件  iv_shooting 修改头像控件
     */
    @OnClick({R.id.iv_exit_edit, R.id.tv_save_edit, R.id.iv_shooting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_exit_edit:
                startActivity(new Intent(this, ResumeActivity.class));
                finish();
                break;
            case R.id.tv_save_edit:
                mvpPresenter.saveEditedResume();
                break;
            case R.id.iv_shooting:
                mvpPresenter.replaceHeadImage();
                break;
        }
    }

    /**
     * 获取用户调用系统相册选择的图片进行头像更新
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            showProgressDialog();
            mvpPresenter.setImagePath(c.getString(columnIndex));
            c.close();
        }
    }


}
