package com.su.data.video.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResVideoDetail {

    /**
     * archivesInfo
     */
    @SerializedName("archivesInfo")
    private ArchivesInfoDTO archivesInfo;
    /**
     * commentList
     */
    @SerializedName("commentList")
    private List<ResComment> commentList;
    /**
     * token
     */
    @SerializedName("__token__")
    private String token;

    public ArchivesInfoDTO getArchivesInfo() {
        return archivesInfo;
    }

    public void setArchivesInfo(ArchivesInfoDTO archivesInfo) {
        this.archivesInfo = archivesInfo;
    }

    public List<ResComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<ResComment> commentList) {
        this.commentList = commentList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class ArchivesInfoDTO {
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
         * channelId
         */
        @SerializedName("channel_id")
        private Integer channelId;
        /**
         * channelIds
         */
        @SerializedName("channel_ids")
        private String channelIds;
        /**
         * modelId
         */
        @SerializedName("model_id")
        private Integer modelId;
        /**
         * specialIds
         */
        @SerializedName("special_ids")
        private String specialIds;
        /**
         * title
         */
        @SerializedName("title")
        private String title;
        /**
         * flag
         */
        @SerializedName("flag")
        private String flag;
        /**
         * style
         */
        @SerializedName("style")
        private String style;
        /**
         * image
         */
        @SerializedName("image")
        private String image;
        /**
         * images
         */
        @SerializedName("images")
        private String images;
        /**
         * videoFile
         */
        @SerializedName("video_file")
        private String videoFile;
        /**
         * seotitle
         */
        @SerializedName("seotitle")
        private String seotitle;
        /**
         * keywords
         */
        @SerializedName("keywords")
        private String keywords;
        /**
         * description
         */
        @SerializedName("description")
        private String description;
        /**
         * tags
         */
        @SerializedName("tags")
        private String tags;
        /**
         * price
         */
        @SerializedName("price")
        private String price;
        /**
         * outlink
         */
        @SerializedName("outlink")
        private String outlink;
        /**
         * views
         */
        @SerializedName("views")
        private Integer views;
        /**
         * comments
         */
        @SerializedName("comments")
        private Integer comments;
        /**
         * likes
         */
        @SerializedName("likes")
        private Integer likes;
        /**
         * dislikes
         */
        @SerializedName("dislikes")
        private Integer dislikes;
        /**
         * collection
         */
        @SerializedName("collection")
        private Integer collection;
        /**
         * diyname
         */
        @SerializedName("diyname")
        private String diyname;
        /**
         * isguest
         */
        @SerializedName("isguest")
        private Integer isguest;
        /**
         * iscomment
         */
        @SerializedName("iscomment")
        private Integer iscomment;
        /**
         * createtime
         */
        @SerializedName("createtime")
        private Integer createtime;
        /**
         * updatetime
         */
        @SerializedName("updatetime")
        private Integer updatetime;
        /**
         * publishtime
         */
        @SerializedName("publishtime")
        private Object publishtime;
        /**
         * memo
         */
        @SerializedName("memo")
        private String memo;
        /**
         * duration
         */
        @SerializedName("duration")
        private String duration;
        /**
         * content
         */
        @SerializedName("content")
        private String content;
        /**
         * islike
         */
        @SerializedName("islike")
        private Integer islike;
        /**
         * iscollection
         */
        @SerializedName("iscollection")
        private Integer iscollection;
        /**
         * user
         */
        @SerializedName("user")
        private UserDTO user;
        /**
         * channel
         */
        @SerializedName("channel")
        private ChannelDTO channel;
        /**
         * url
         */
        @SerializedName("url")
        private String url;
        /**
         * fullurl
         */
        @SerializedName("fullurl")
        private String fullurl;
        /**
         * likeratio
         */
        @SerializedName("likeratio")
        private String likeratio;
        /**
         * taglist
         */
        @SerializedName("taglist")
        private List<?> taglist;
        /**
         * createDate
         */
        @SerializedName("create_date")
        private String createDate;
        /**
         * ispaid
         */
        @SerializedName("ispaid")
        private Boolean ispaid;

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

        public Integer getChannelId() {
            return channelId;
        }

        public void setChannelId(Integer channelId) {
            this.channelId = channelId;
        }

        public String getChannelIds() {
            return channelIds;
        }

        public void setChannelIds(String channelIds) {
            this.channelIds = channelIds;
        }

        public Integer getModelId() {
            return modelId;
        }

        public void setModelId(Integer modelId) {
            this.modelId = modelId;
        }

        public String getSpecialIds() {
            return specialIds;
        }

        public void setSpecialIds(String specialIds) {
            this.specialIds = specialIds;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getVideoFile() {
            return videoFile;
        }

        public void setVideoFile(String videoFile) {
            this.videoFile = videoFile;
        }

        public String getSeotitle() {
            return seotitle;
        }

        public void setSeotitle(String seotitle) {
            this.seotitle = seotitle;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOutlink() {
            return outlink;
        }

        public void setOutlink(String outlink) {
            this.outlink = outlink;
        }

        public Integer getViews() {
            return views;
        }

        public String getStrViews() {
            return String.valueOf(views);
        }

        public void setViews(Integer views) {
            this.views = views;
        }

        public Integer getComments() {
            return comments;
        }

        public String getStrComment() {
            return String.valueOf(comments);
        }

        public void setComments(Integer comments) {
            this.comments = comments;
        }

        public Integer getLikes() {
            return likes;
        }

        public String getStrLikes() {
            return String.valueOf(likes);
        }

        public void setLikes(Integer likes) {
            this.likes = likes;
        }

        public Integer getDislikes() {
            return dislikes;
        }

        public void setDislikes(Integer dislikes) {
            this.dislikes = dislikes;
        }

        public Integer getCollection() {
            return collection;
        }

        public String getStrCollection() {
            return String.valueOf(collection);
        }

        public void setCollection(Integer collection) {
            this.collection = collection;
        }

        public String getDiyname() {
            return diyname;
        }

        public void setDiyname(String diyname) {
            this.diyname = diyname;
        }

        public Integer getIsguest() {
            return isguest;
        }

        public void setIsguest(Integer isguest) {
            this.isguest = isguest;
        }

        public Integer getIscomment() {
            return iscomment;
        }

        public void setIscomment(Integer iscomment) {
            this.iscomment = iscomment;
        }

        public Integer getCreatetime() {
            return createtime;
        }

        public void setCreatetime(Integer createtime) {
            this.createtime = createtime;
        }

        public Integer getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(Integer updatetime) {
            this.updatetime = updatetime;
        }

        public Object getPublishtime() {
            return publishtime;
        }

        public void setPublishtime(Object publishtime) {
            this.publishtime = publishtime;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getIslike() {
            return islike;
        }

        public void setIslike(Integer islike) {
            this.islike = islike;
        }

        public Integer getIscollection() {
            return iscollection;
        }

        public void setIscollection(Integer iscollection) {
            this.iscollection = iscollection;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public ChannelDTO getChannel() {
            return channel;
        }

        public void setChannel(ChannelDTO channel) {
            this.channel = channel;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFullurl() {
            return fullurl;
        }

        public void setFullurl(String fullurl) {
            this.fullurl = fullurl;
        }

        public String getLikeratio() {
            return likeratio;
        }

        public void setLikeratio(String likeratio) {
            this.likeratio = likeratio;
        }

        public List<?> getTaglist() {
            return taglist;
        }

        public void setTaglist(List<?> taglist) {
            this.taglist = taglist;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public Boolean getIspaid() {
            return ispaid;
        }

        public void setIspaid(Boolean ispaid) {
            this.ispaid = ispaid;
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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class ChannelDTO {
            /**
             * id
             */
            @SerializedName("id")
            private Integer id;
            /**
             * parentId
             */
            @SerializedName("parent_id")
            private Integer parentId;
            /**
             * name
             */
            @SerializedName("name")
            private String name;
            /**
             * image
             */
            @SerializedName("image")
            private String image;
            /**
             * diyname
             */
            @SerializedName("diyname")
            private String diyname;
            /**
             * items
             */
            @SerializedName("items")
            private Integer items;
            /**
             * url
             */
            @SerializedName("url")
            private String url;
            /**
             * fullurl
             */
            @SerializedName("fullurl")
            private String fullurl;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public Integer getParentId() {
                return parentId;
            }

            public void setParentId(Integer parentId) {
                this.parentId = parentId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getDiyname() {
                return diyname;
            }

            public void setDiyname(String diyname) {
                this.diyname = diyname;
            }

            public Integer getItems() {
                return items;
            }

            public void setItems(Integer items) {
                this.items = items;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getFullurl() {
                return fullurl;
            }

            public void setFullurl(String fullurl) {
                this.fullurl = fullurl;
            }
        }
    }
}
