package com.su.data.video.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResCollection {

    /**
     * collectionList
     */
    @SerializedName("collectionList")
    private CollectionListDTO collectionList;
    /**
     * model
     */
    @SerializedName("model")
    private Object model;

    public CollectionListDTO getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(CollectionListDTO collectionList) {
        this.collectionList = collectionList;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public static class CollectionListDTO {
        /**
         * total
         */
        @SerializedName("total")
        private Integer total;
        /**
         * perPage
         */
        @SerializedName("per_page")
        private Integer perPage;
        /**
         * currentPage
         */
        @SerializedName("current_page")
        private Integer currentPage;
        /**
         * lastPage
         */
        @SerializedName("last_page")
        private Integer lastPage;
        /**
         * data
         */
        @SerializedName("data")
        private List<DataDTO> data;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getPerPage() {
            return perPage;
        }

        public void setPerPage(Integer perPage) {
            this.perPage = perPage;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getLastPage() {
            return lastPage;
        }

        public void setLastPage(Integer lastPage) {
            this.lastPage = lastPage;
        }

        public List<DataDTO> getData() {
            return data;
        }

        public void setData(List<DataDTO> data) {
            this.data = data;
        }

        public static class DataDTO {
            /**
             * id
             */
            @SerializedName("id")
            private Integer id;
            /**
             * type
             */
            @SerializedName("type")
            private String type;
            /**
             * aid
             */
            @SerializedName("aid")
            private Integer aid;
            /**
             * userId
             */
            @SerializedName("user_id")
            private Integer userId;
            /**
             * title
             */
            @SerializedName("title")
            private String title;
            /**
             * image
             */
            @SerializedName("image")
            private String image;
            /**
             * url
             */
            @SerializedName("url")
            private String url;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Integer getAid() {
                return aid;
            }

            public void setAid(Integer aid) {
                this.aid = aid;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
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

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }
        }
    }
}
