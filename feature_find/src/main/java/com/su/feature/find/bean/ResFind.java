package com.su.feature.find.bean;

import com.google.gson.annotations.SerializedName;
import com.su.data.video.bean.ResFindCategory;

import java.util.List;

public class ResFind {

    /**
     * category
     */
    @SerializedName("category")
    private List<ResFindCategory> category;         // 分类数据
    /**
     * anchor
     */
    @SerializedName("anchor")
    private List<ResFindAnchor> anchor;             // 主题播单
    /**
     * topic
     */
    @SerializedName("topic")
    private List<ResFindTopic> topic;               // 话题广场

    public List<ResFindCategory> getCategory() {
        return category;
    }

    public void setCategory(List<ResFindCategory> category) {
        this.category = category;
    }

    public List<ResFindAnchor> getAnchor() {
        return anchor;
    }

    public void setAnchor(List<ResFindAnchor> anchor) {
        this.anchor = anchor;
    }

    public List<ResFindTopic> getTopic() {
        return topic;
    }

    public void setTopic(List<ResFindTopic> topic) {
        this.topic = topic;
    }

}
