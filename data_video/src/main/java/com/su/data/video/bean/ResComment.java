package com.su.data.video.bean;

import com.google.gson.annotations.SerializedName;

public class ResComment {
    /**
     * id
     */
    @SerializedName("id")
    private Integer id;
    /**
     * userId
     */
    @SerializedName("user_id")
    private Integer userId;
    /**
     * pid
     */
    @SerializedName("pid")
    private Integer pid;
    /**
     * content
     */
    @SerializedName("content")
    private String content;
    /**
     * comments
     */
    @SerializedName("comments")
    private Integer comments;
    /**
     * createtime
     */
    @SerializedName("createtime")
    private Integer createtime;
    /**
     * user
     */
    @SerializedName("user")
    private UserDTO user;
    /**
     * createDate
     */
    @SerializedName("create_date")
    private String createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public static class UserDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * nickname
         */
        @SerializedName("nickname")
        private String nickname;
        /**
         * avatar
         */
        @SerializedName("avatar")
        private String avatar;
        /**
         * bio
         */
        @SerializedName("bio")
        private String bio;
        /**
         * email
         */
        @SerializedName("email")
        private String email;
        /**
         * url
         */
        @SerializedName("url")
        private String url;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
