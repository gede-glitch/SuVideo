package com.su.feature.plaza.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResPlaza {
    private String type;
    private List<PlazaDetail> lists;

    public String getType() {
        return type;
    }

    public List<PlazaDetail> getLists() {
        return lists;
    }

    public static class PlazaDetail implements Parcelable {

        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
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
         * icon
         */
        @SerializedName("icon")
        private String icon;
        /**
         * description
         */
        @SerializedName("description")
        private String description;
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
         * title
         */
        @SerializedName("title")
        private String title;
        /**
         * images
         */
        @SerializedName("images")
        private List<String> images;
        /**
         * author
         */
        @SerializedName("author")
        private String author;
        /**
         * avatar
         */
        @SerializedName("avatar")
        private String avatar;
        /**
         * cover
         */
        @SerializedName("cover")
        private String cover;

        protected PlazaDetail(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            name = in.readString();
            image = in.readString();
            icon = in.readString();
            description = in.readString();
            url = in.readString();
            fullurl = in.readString();
            title = in.readString();
            images = in.createStringArrayList();
            author = in.readString();
            avatar = in.readString();
            cover = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(id);
            }
            dest.writeString(name);
            dest.writeString(image);
            dest.writeString(icon);
            dest.writeString(description);
            dest.writeString(url);
            dest.writeString(fullurl);
            dest.writeString(title);
            dest.writeStringList(images);
            dest.writeString(author);
            dest.writeString(avatar);
            dest.writeString(cover);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<PlazaDetail> CREATOR = new Creator<PlazaDetail>() {
            @Override
            public PlazaDetail createFromParcel(Parcel in) {
                return new PlazaDetail(in);
            }

            @Override
            public PlazaDetail[] newArray(int size) {
                return new PlazaDetail[size];
            }
        };

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }

}
