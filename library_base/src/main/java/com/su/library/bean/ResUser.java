package com.su.library.bean;

import com.google.gson.annotations.SerializedName;

public class ResUser {

    /**
     * user
     */
    @SerializedName("user")
    private UserInfo user;
    /**
     * fans
     */
    @SerializedName("fans")
    private Integer fans;
    /**
     * follow
     */
    @SerializedName("follow")
    private Integer follow;
    /**
     * medal
     */
    @SerializedName("medal")
    private Integer medal;

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }

    public Integer getMedal() {
        return medal;
    }

    public void setMedal(Integer medal) {
        this.medal = medal;
    }
}
