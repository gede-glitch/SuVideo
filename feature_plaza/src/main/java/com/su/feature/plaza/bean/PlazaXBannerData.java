package com.su.feature.plaza.bean;

import com.stx.xhb.androidx.entity.BaseBannerInfo;

public class PlazaXBannerData implements BaseBannerInfo {

    private String mTitle;
    private String mImgUrl;
    private String mDescription;

    public PlazaXBannerData(String mTitle, String mImgUrl, String mDescription) {
        this.mImgUrl = mImgUrl;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    @Override
    public String getXBannerUrl() {
        return mImgUrl;
    }

    @Override
    public String getXBannerTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
