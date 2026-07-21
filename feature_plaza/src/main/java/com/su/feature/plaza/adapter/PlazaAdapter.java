package com.su.feature.plaza.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.stx.xhb.androidx.XBanner;
import com.su.feature.plaza.R;
import com.su.feature.plaza.bean.PlazaXBannerData;
import com.su.feature.plaza.bean.ResPlaza;
import com.su.feature.plaza.databinding.ItemBannerBinding;
import com.su.feature.plaza.databinding.ItemImageBinding;
import com.su.library.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;


public class PlazaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_BANNER = 1;
    private static final int ITEM_TYPE_IMAGE = 2;
    private List<ResPlaza.PlazaDetail> mLists;
    private ArrayList<PlazaXBannerData> mPlazaXBannerData;
    private PlazaItemListener mItemListener;
    private ResPlaza mBannerData;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == ITEM_TYPE_BANNER) {
            ItemBannerBinding itemBannerBinding = ItemBannerBinding.inflate(layoutInflater, parent, false);
            return new BannerViewHolder(itemBannerBinding);
        } else {
            ItemImageBinding itemImageBinding = ItemImageBinding.inflate(layoutInflater, parent, false);
            return new ImageViewHolder(itemImageBinding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE_BANNER : ITEM_TYPE_IMAGE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == ITEM_TYPE_BANNER) {
            BannerViewHolder viewHolder = (BannerViewHolder) holder;
            ItemBannerBinding bannerBinding = viewHolder.bannerBinding;
            bannerBinding.xbanner.setBannerPlaceholderImg(R.mipmap.ic_launcher, ImageView.ScaleType.CENTER_CROP);
            bannerBinding.xbanner.setIsClipChildrenMode(true);
            bannerBinding.xbanner.setBannerData(R.layout.item_banner_child, mPlazaXBannerData);
            bannerBinding.xbanner.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    ImageView imageView = view.findViewById(R.id.iv_image);
                    TextView titleView = view.findViewById(R.id.tv_title);
                    TextView descriptionView = view.findViewById(R.id.tv_label);

                    PlazaXBannerData plazaXBannerData = mPlazaXBannerData.get(position);
                    GlideUtils.loadImage(plazaXBannerData.getXBannerUrl(), imageView);
                    titleView.setText(plazaXBannerData.getXBannerTitle());
                    descriptionView.setText(plazaXBannerData.getDescription());
                }
            });

            bannerBinding.xbanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    bannerBinding.tvNumber.setText(String.valueOf(position + 1));
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } else {
            ImageViewHolder viewHolder = (ImageViewHolder) holder;
            ResPlaza.PlazaDetail detail = mLists.get(position - 1);
            viewHolder.imageBinding.setData(detail);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mLists != null && !mLists.isEmpty()) {
            count += mLists.size();
        }
        if (mPlazaXBannerData != null && !mPlazaXBannerData.isEmpty()) {
            count += 1;
        }
        return count;
    }

    public void setData(List<ResPlaza> resPlazas) {
        if (resPlazas != null && resPlazas.size() >= 2) {
            mBannerData = resPlazas.get(0);
            mPlazaXBannerData = converXBannerDatas(mBannerData);

            ResPlaza imageData = resPlazas.get(1);
            mLists = imageData.getLists();

            notifyDataSetChanged();
        }
    }

    private ArrayList<PlazaXBannerData> converXBannerDatas(ResPlaza mBannerData) {
        List<ResPlaza.PlazaDetail> lists = mBannerData.getLists();
        if (lists != null && !lists.isEmpty()) {
            ArrayList<PlazaXBannerData> plazaXBannerDatas = new ArrayList<>();
            for (int i = 0; i < lists.size(); i++) {
                ResPlaza.PlazaDetail detail = lists.get(i);
                PlazaXBannerData plazaXBannerData = new PlazaXBannerData(detail.getName(), detail.getImage(), detail.getDescription());
                plazaXBannerDatas.add(plazaXBannerData);
            }
            return plazaXBannerDatas;
        } else {
            return null;
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private final ItemImageBinding imageBinding;

        public ImageViewHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            imageBinding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener != null) {
                        int layoutPosition = getLayoutPosition();
                        ResPlaza.PlazaDetail plazaDetail = mLists.get(layoutPosition - 1);
                        mItemListener.onImageClick(plazaDetail);
                    }
                }
            });
        }

        public ItemImageBinding getImageBinding() {
            return imageBinding;
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {

        private final ItemBannerBinding bannerBinding;

        public BannerViewHolder(ItemBannerBinding binding) {
            super(binding.getRoot());
            bannerBinding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemListener != null) {
                        int bannerCurrentItem = binding.xbanner.getBannerCurrentItem();
                        itemClick(bannerCurrentItem);
                    }
                }
            });

            binding.xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                @Override
                public void onItemClick(XBanner banner, Object model, View view, int position) {
                    if (mItemListener != null) {
                        itemClick(position);
                    }
                }
            });
        }

        private void itemClick(int position) {
            List<ResPlaza.PlazaDetail> lists = mBannerData.getLists();
            ResPlaza.PlazaDetail detail = lists.get(position);
            mItemListener.onBannerClick(detail);
        }

        public ItemBannerBinding getBannerBinding() {
            return bannerBinding;
        }
    }

    public interface PlazaItemListener {
        /**
         * 头部广告点击
         */
        void onBannerClick(ResPlaza.PlazaDetail detail);

        /**
         * 底部图片点击
         */
        void onImageClick(ResPlaza.PlazaDetail detail);
    }

    public void setItemListener(PlazaItemListener mItemListener) {
        this.mItemListener = mItemListener;
    }
}
