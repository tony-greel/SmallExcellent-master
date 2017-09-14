package com.enjoy.hyc.editresume;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.enjoy.base.BasePresenter;
import com.enjoy.base.SmallApplication;
import com.enjoy.hyc.bean.User;
import com.enjoy.hyc.util.ImageUtils;
import com.enjoy.hyc.util.JobUtil;
import com.enjoy.hyc.util.UserUtil;

import org.greenrobot.eventbus.EventBus;

import cn.bmob.v3.BmobUser;

/**
 * Created by hyc on 2017/4/26 10:59
 */

public class EditResumePresenter extends BasePresenter<EditResumeContract>{

    private String imagePath;

    /**
     * 导入当前用户的简历信息
     */
    public void loadOldResume(){
        User user= BmobUser.getCurrentUser(User.class);
        mvpView.showCurrentUserResume(user);
    }

    /**
     * 保存编辑后的用户简历信息
     */
    public void saveEditedResume(){
        UserUtil.updateUserResume(mvpView.getUpdateUser(), new JobUtil.OnDeleteJobListener() {
            @Override
            public void success() {
                mvpView.showEditSuccess();
            }

            @Override
            public void fail(String error) {
                mvpView.showEditFail();
            }
        });
    }

    /**
     * 更新用户头像图片
     */
    public void replaceHeadImage(){
        mvpView.callSystemPhotoAlbum();
    }

    /**
     * 设置上传用户新头像图片压缩并上传
     * @param path
     */
    public void setImagePath(String path) {
        this.imagePath = path;
        Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
        Bitmap zoomBitmap=ImageUtils.zoomImage(ImageUtils.splitBitmap(bitmap),120,120);
        this.imagePath=ImageUtils.saveBitmap(SmallApplication.getContext(),ImageUtils.saveBitmap(zoomBitmap,"IMG_"+"cache.png"));
        UserUtil.updateUserHeadImage(this.imagePath, new UserUtil.OnImageDealListener() {
            @Override
            public void success(String path) {
                mvpView.showZoomPhoto(path);
                Toast.makeText(SmallApplication.getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post("图片"+path);
            }

            @Override
            public void fail(String error) {
                mvpView.showZoomPhoto(imagePath);
                Toast.makeText(SmallApplication.getContext(), "上传失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
