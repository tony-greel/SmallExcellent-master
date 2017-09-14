package com.enjoy.hyc.util;

import com.enjoy.base.SmallApplication;
import com.enjoy.hyc.bean.User;

import java.io.File;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 用户工具类 Created by hyc on 2017/4/25 19:13
 */

public class UserUtil {

    public interface OnLoginListener {
        void success();

        void loginFail(String error);
    }

    public interface OnRegisterListener {
        void success();

        void registerFail(String error);
    }

    public interface OnImageDealListener {
        void success(String path);

        void fail(String error);
    }

    /**
     * 登录
     * @param user 登录的User
     * @param listener 登录监听
     */
    public static void login(User user, final OnLoginListener listener) {
        user.login(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.success();
                } else {
                    listener.loginFail(e.getMessage());
                }
            }
        });
    }

    /**
     * 注册
     * @param user 注册的User
     * @param listener 注册监听
     */
    public static void register(User user, final OnRegisterListener listener) {
        user.signUp(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.success();
                } else {
                    listener.registerFail(e.getMessage());
                }
            }
        });
    }

    /**
     * 获取当前用户信息，暂无用
     * @return
     */
    public static User getCurrentUser(){
        return null;
    }

    /**
     * 验证手机号
     * @param number
     * @param listener
     */
    public static void verifyMobileNumber(String number, final JobUtil.OnDeleteJobListener listener) {
        User user = new User();
        user.setMobilePhoneNumber(number);
        user.setMobilePhoneNumberVerified(true);
        User cur = BmobUser.getCurrentUser(User.class);
        user.update(cur.getObjectId(), new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.success();
                } else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 修改当前用户的密码
     * @param oldPassword
     * @param newPassword
     * @param listener
     */
    public static void amendPassword(String oldPassword, String newPassword, final JobUtil.OnDeleteJobListener listener) {
        BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.success();
                } else {
                    listener.fail(e.getMessage());
                }
            }

        });
    }

    /**
     * 更新当前用户的简历信息
     * @param newUser
     * @param listener
     */
    public static void updateUserResume(User newUser, final JobUtil.OnDeleteJobListener listener) {
        User bmobUser = BmobUser.getCurrentUser(User.class);
        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    listener.success();
                } else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }

    /**
     * 更新当前用户的头像图片
     * @param path 图片储存路径
     * @param listener 更新监听
     */
    public static void updateUserHeadImage(String path, final OnImageDealListener listener) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    User newUser = new User();
                    newUser.setHeadImageUrl(bmobFile.getUrl());
                    User bmobUser = BmobUser.getCurrentUser(User.class);
                    newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                listener.success(bmobFile.getFileUrl());
                            } else {
                                listener.fail(e.getMessage());
                            }
                        }
                    });
                } else {
                    listener.fail(e.getMessage());
                }
            }
        });
    }
}
