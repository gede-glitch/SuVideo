package com.su.library.manager;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.su.library.base.BaseApplication;
import com.su.library.bean.ResUser;
import com.su.library.bean.UserInfo;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class UserManager {
    private static UserManager instance;
    private SharedPreferences mPreferences;
    private static final String FILE_NAME = "user_prefs";
    private static final String KEY_TOKE = "key_token";
    private static final String KEY_USER_ID = "key_user_id";
    private static final String KEY_NICK_NAME = "key_nick_name";
    private static final String KEY_USER_NAME = "key_user_name";
    private static final String KEY_BIO = "key_bio";
    private static final String KEY_AVATAR = "key_avatar";
    private static final String KEY_STATUS = "key_status";
    private static final String KEY_FOLLOW = "key_follow";
    private static final String KEY_FANS = "key_fans";
    private static final String KEY_MEDAL = "key_medal";

    private UserManager() {
        try {
            String masterAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            mPreferences = EncryptedSharedPreferences.create(
                    FILE_NAME, masterAlias, BaseApplication.getContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    public void saveToken(String token) {
        mPreferences.edit().putString(KEY_TOKE, token).apply();
    }

    public String getToken() {
        return mPreferences.getString(KEY_TOKE, null);
    }

    public void saveUserInfo(ResUser userInfo) {
        UserInfo user = userInfo.getUser();
        mPreferences.edit()
                .putString(KEY_USER_ID, user.getId())
                .putString(KEY_NICK_NAME, user.getNickname())
                .putString(KEY_USER_NAME, user.getUsername())
                .putString(KEY_AVATAR, user.getAvatar())
                .putString(KEY_BIO, user.getBio())
                .putString(KEY_STATUS, user.getStatus())
                .putInt(KEY_FOLLOW, userInfo.getFollow())
                .putInt(KEY_FANS, userInfo.getFans())
                .putInt(KEY_MEDAL, userInfo.getMedal())
                .apply();
    }

    public ResUser getUserInfo() {
        String userId = mPreferences.getString(KEY_USER_ID, null);
        String nickName = mPreferences.getString(KEY_NICK_NAME, null);
        String userName = mPreferences.getString(KEY_USER_NAME, null);
        String avatar = mPreferences.getString(KEY_AVATAR, null);
        String bio = mPreferences.getString(KEY_BIO, null);
        String status = mPreferences.getString(KEY_STATUS, null);
        int follow = mPreferences.getInt(KEY_FOLLOW, 0);
        int fans = mPreferences.getInt(KEY_FANS, 0);
        int medal = mPreferences.getInt(KEY_MEDAL, 0);

        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(nickName);
        userInfo.setUsername(userName);
        userInfo.setId(userId);
        userInfo.setAvatar(avatar);
        userInfo.setBio(bio);
        userInfo.setStatus(status);

        ResUser user = new ResUser();
        user.setFans(fans);
        user.setFollow(follow);
        user.setMedal(medal);
        user.setUser(userInfo);

        return user;
    }

    public void loginOut() {
        mPreferences.edit()
                .remove(KEY_TOKE)
                .remove(KEY_USER_ID)
                .remove(KEY_NICK_NAME)
                .remove(KEY_USER_NAME)
                .remove(KEY_AVATAR)
                .remove(KEY_BIO)
                .remove(KEY_STATUS)
                .remove(KEY_FOLLOW)
                .remove(KEY_FANS)
                .remove(KEY_MEDAL)
                .apply();
    }

    public boolean isLogin() {
        String token = getToken();
        return token != null && !token.isEmpty();
    }
    /**
     * 更新资料页 只能更新这三个信息
     *
     * @param avatarUrl
     * @param nickName
     * @param bio
     */

    public void updateUserInfo(String avatarUrl, String nickName, String bio) {
        mPreferences.edit()
                .putString(KEY_NICK_NAME, nickName)
                .putString(KEY_AVATAR, avatarUrl)
                .putString(KEY_BIO, bio)
                .apply();
    }
}
