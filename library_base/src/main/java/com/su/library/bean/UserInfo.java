package com.su.library.bean;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    /**
     * id
     */
    @SerializedName("id")
    private String id;
    /**
     * nickname
     */
    @SerializedName("nickname")
    private String nickname;
    /**
     * bio
     */
    @SerializedName("bio")
    private String bio;
    /**
     * avatar
     */
    @SerializedName("avatar")
    private String avatar;
    /**
     * status
     */
    @SerializedName("status")
    private String status;
    /**
     * username
     */
    @SerializedName("username")
    private String username;
    /**
     * url
     */
    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
