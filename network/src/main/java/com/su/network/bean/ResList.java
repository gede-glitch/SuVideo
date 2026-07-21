package com.su.network.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResList<T> {
    @SerializedName("count")
    private Integer count;
    @SerializedName("list")
    private List<T> list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
