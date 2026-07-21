package com.su.feature.user.bean;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ResMobileLogin {

    /**
     * token
     */
    @SerializedName("token")
    private String token;
    /**
     * id
     */
    @SerializedName("id")
    private Integer id;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "ResMobileLogin{" +
                "token='" + token + '\'' +
                ", id=" + id +
                '}';
    }
}
