package com.su.feature.plaza.fragment.plaza;

import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.su.data.video.bean.ResFindCategory;
import com.su.feature.plaza.R;
import com.su.feature.plaza.adapter.PlazaAdapter;
import com.su.feature.plaza.bean.ResPlaza;
import com.su.feature.plaza.databinding.LayoutFragmentPlazaBinding;
import com.su.library.base.BaseFragment;
import com.su.library.callback.EmptyCallback;
import com.su.library.callback.ErrorCallback;
import com.su.library.callback.LoadingCallback;
import com.su.library.config.ARouterPath;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.utils.StatusBarUtils;

import java.util.List;

@Route(path = ARouterPath.Plaza.FRAGMENT_PLAZA)
public class PlazaFragment extends BaseFragment<PlazaViewModel, LayoutFragmentPlazaBinding> {
    private static final String TAG = "PlazaFragment";
    private PlazaAdapter mAdapter;
    private LoadService mLoadService;
    private SmartRefreshLayout mRefreshLayout;

    @Override
    protected int getBindingVariableId() {
        return 0;
    }

    @Override
    protected PlazaViewModel getViewModel() {
        return new ViewModelProvider(this).get(PlazaViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_plaza;
    }

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mDataBinding.getRoot());
        RecyclerView recyclerView = mDataBinding.layoutRecycleView.recyclerView;
        mRefreshLayout = mDataBinding.layoutRecycleView.smartRefreshLayout;
        SmartRefreshLayout smartRefreshLayout = mDataBinding.layoutRecycleView.smartRefreshLayout;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;               // 如果是第一行就独占一行
                } else {
                    return 1;
                }
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new PlazaAdapter();
        recyclerView.setAdapter(mAdapter);
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.requestDatas();
            }
        });

        // item 点击
        mAdapter.setItemListener(new PlazaAdapter.PlazaItemListener() {
            @Override
            public void onBannerClick(ResPlaza.PlazaDetail detail) {
                // FRAGMENT_CATEGORY_DETAIL页面需要ResFindCategory类型。因此在这里做数据类型转换
                ResFindCategory resFindCategory = new ResFindCategory();
                resFindCategory.setId(detail.getId());
                resFindCategory.setName(detail.getName());
                resFindCategory.setImage(detail.getImage());
                resFindCategory.setDescription(detail.getDescription());
                resFindCategory.setIcon(detail.getIcon());
                resFindCategory.setFullurl(detail.getFullurl());
                resFindCategory.setUrl(detail.getUrl());

                ARouter.getInstance().build(ARouterPath.Find.ACTIVITY_CATEGORY_DETAIL)
                        .withParcelable(ARouterPath.Find.KEY_CATEGORY_DATA, resFindCategory)
                        .navigation();
                Log.i(TAG, "onBannerClick: detail");
            }

            @Override
            public void onImageClick(ResPlaza.PlazaDetail detail) {
                ARouter.getInstance().build(ARouterPath.Plaza.IMAGE_ACTIVITY)
                        .withParcelable(ARouterPath.Plaza.KEY_IMAGE_DATA, (Parcelable) detail)
                        .navigation();
            }
        });

        mLoadService = LoadSir.getDefault().register(
                smartRefreshLayout,
                (Callback.OnReloadListener) context -> mViewModel.requestDatas()
        );


    }

    @Override
    protected void initData() {
        mLoadService.showCallback(LoadingCallback.class);
        mViewModel.getErrorCode().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null) {
                    return;
                }
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.finishRefresh();
                }
                if (integer == ErrorStatusConfig.ERROR_STATUS_EMPTY) {
                    mLoadService.showCallback(EmptyCallback.class);
                } else {
                    mLoadService.showCallback(ErrorCallback.class);
                }
            }
        });
        mViewModel.getDatas().observe(getViewLifecycleOwner(), new Observer<List<ResPlaza>>() {
            @Override
            public void onChanged(List<ResPlaza> resPlazas) {
                mAdapter.setData(resPlazas);
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.finishRefresh();
                }
                mLoadService.showSuccess();
            }
        });

        mViewModel.requestDatas();
    }
}
