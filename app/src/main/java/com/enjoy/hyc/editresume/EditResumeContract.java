package com.enjoy.hyc.editresume;

import com.enjoy.hyc.bean.User;

/**
 * Created by hyc on 2017/4/26 11:00
 */

public interface EditResumeContract {

    User getUpdateUser();

    void showCurrentUserResume(User user);

    void showEditSuccess();

    void showEditFail();

    void callSystemPhotoAlbum();

    void showZoomPhoto(String path);
}
