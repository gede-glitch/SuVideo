package com.su.feature.find.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResThemeData {

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
     * desc
     */
    @SerializedName("desc")
    private String desc;
    /**
     * lists
     */
    @SerializedName("lists")
    private List<ListsDTO> lists;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ListsDTO> getLists() {
        return lists;
    }

    public void setLists(List<ListsDTO> lists) {
        this.lists = lists;
    }

    public static class ListsDTO {
        /**
         * type
         */
        @SerializedName("type")
        private String type;
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
         * playUrl
         */
        @SerializedName("play_url")
        private String playUrl;
        /**
         * duration
         */
        @SerializedName("duration")
        private String duration;
        /**
         * cover
         */
        @SerializedName("cover")
        private String cover;
        /**
         * tag
         */
        @SerializedName("tag")
        private String tag;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

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

        public String getPlayUrl() {
            return playUrl;
        }

        public void setPlayUrl(String playUrl) {
            this.playUrl = playUrl;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }
}
