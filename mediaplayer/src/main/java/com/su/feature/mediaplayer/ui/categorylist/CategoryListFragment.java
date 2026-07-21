package com.su.feature.mediaplayer.ui.categorylist;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.su.data.video.bean.ResCategoryVideoDetail;
import com.su.feature.mediaplayer.adapter.CategoryVideosAdapter;
import com.su.library.base.BaseFragment;
import com.su.library.base.BaseViewModel;
import com.su.library.config.ARouterPath;
import com.su.library.list.BaseCategoryFragment;
import com.su.library.list.BaseListFragment;
import com.su.library.list.BaseListViewModel;

import java.util.List;

@Route(path = ARouterPath.Video.FRAGMENT_CATEGORY_LIST)
public class CategoryListFragment extends BaseCategoryFragment<ResCategoryVideoDetail> implements CategoryVideosAdapter.ItemClickListener {

    @Autowired(name = ARouterPath.Video.KEY_CATEGORY_TYPE)
    public int mType;

    @Autowired(name = ARouterPath.Video.KEY_CATEGORY_ID)
    public int mChannelId;
    private CategoryVideosAdapter mAdapter;

    @Override
    public void onItemClick(int id) {
        ARouter.getInstance().build(ARouterPath.Video.VIDEO_DETAIL)
                .withInt(ARouterPath.Video.KEY_VIDEO_ID, id)
                .navigation();
    }

    @Override
    public void onLikeClick(int position) {
        CategoryListViewModel viewModel = (CategoryListViewModel) mViewModel;
        viewModel.onLikeClick(position);
    }

    @Override
    public void onCollectionClick(int position) {
        CategoryListViewModel viewModel = (CategoryListViewModel) mViewModel;
        viewModel.onCollectionClick(position);
    }

    @Override
    public void onCommentClick(int position) {

    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        mAdapter = new CategoryVideosAdapter();
        mAdapter.setListener(this);
        return mAdapter;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected void onDatasRequestSuccess(List<ResCategoryVideoDetail> list) {
        if (mAdapter != null) {
            mAdapter.setDatas(list);
        }
    }

    @Override
    protected CategoryListViewModel getViewModel() {
        return new ViewModelProvider(this).get(CategoryListViewModel.class);
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        CategoryListViewModel viewModel = (CategoryListViewModel) mViewModel;
        viewModel.setArgments(mType, mChannelId);
        super.initData();
    }


}
