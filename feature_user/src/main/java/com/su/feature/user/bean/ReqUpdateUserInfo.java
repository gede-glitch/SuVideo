package com.su.feature.user.bean;

import com.google.gson.annotations.SerializedName;

public class ReqUpdateUserInfo {
    private String avatar;
    private String nickname;
    private String bio;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
