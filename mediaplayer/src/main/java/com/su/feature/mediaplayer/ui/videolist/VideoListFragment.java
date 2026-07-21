package com.su.feature.mediaplayer.ui.videolist;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import com.su.data.video.bean.ResVideo;
import com.su.feature.mediaplayer.adapter.VideoAdapter;
import com.su.library.base.databinding.LayoutFragmentSmartListBaseBinding;

import com.su.library.config.ARouterPath;
import com.su.library.list.BaseListFragment;

import java.util.List;

@Route(path = ARouterPath.Video.FRAGMENT_VIDEO_LIST)
public class VideoListFragment extends BaseListFragment<ResVideo, VideoListViewModel, LayoutFragmentSmartListBaseBinding>
        implements VideoAdapter.ItemClickListener {
    @Autowired(name = ARouterPath.Video.KEY_VIDEO_LIST_TYPE)
    public int mPageType;

    @Autowired(name = ARouterPath.Video.KEY_VIDEO_LIST_STYLE)
    public boolean mStyle;

    private VideoAdapter mAdapter;

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return mDataBinding.smartRefreshLayout;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mDataBinding.recyclerView;
    }

    @Override
    protected void onDataChanged(List<ResVideo> dataList) {
        mAdapter.setVideos(dataList);
    }

    @Override
    protected int getBindingVariableId() {
        return 0;
    }

    @Override
    protected VideoListViewModel getViewModel() {
        return new ViewModelProvider(this).get(VideoListViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return com.su.library.base.R.layout.layout_fragment_smart_list_base;
    }

    @Override
    protected void initView() {
        // 从 Arguments 中读取页面类型，替代 @Autowired
        Bundle args = getArguments();
        if (args != null) {
            int pageType = args.getInt(ARouterPath.Video.KEY_VIDEO_LIST_TYPE,
                    ARouterPath.Video.VIDEO_LIST_FRAGMENT_RECOMMEND);
            mViewModel.setPageType(pageType);
        }
        super.initView();

        getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new VideoAdapter(this);
        if (mStyle) {
            mAdapter.setItemWhite(true);
        }
        getRecyclerView().setAdapter(mAdapter);
    }

    @Override
    public void onVideoItemClick(int id) {
        ARouter.getInstance()
                .build(ARouterPath.Video.VIDEO_DETAIL)
                .withInt(ARouterPath.Video.KEY_VIDEO_ID, id)
                .navigation();
    }
}
