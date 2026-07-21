package com.su.feature.find.bean;

import com.google.gson.annotations.SerializedName;

public class ResFindTopic {
    /**
     * id
     */
    @SerializedName("id")
    private Integer id;
    /**
     * title
     */
    @SerializedName("title")
    private String title;
    /**
     * type
     */
    @SerializedName("type")
    private String type;
    /**
     * image
     */
    @SerializedName("image")
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
