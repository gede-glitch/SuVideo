package com.su.library.list;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.su.library.base.BaseFragment;
import com.su.library.callback.EmptyCallback;
import com.su.library.callback.ErrorCallback;
import com.su.library.callback.LoadingCallback;
import com.su.library.config.ErrorStatusConfig;

import java.util.List;

public abstract class BaseListFragment<T, VM extends BaseListViewModel<T>, VDB extends ViewDataBinding> extends BaseFragment<VM, VDB> {
    private LoadService mLoadService;

    /**
     * 子类提供 SmartRefreshLayout 引用
     */
    protected abstract SmartRefreshLayout getSmartRefreshLayout();

    /**
     * 子类提供 RecyclerView 引用
     */
    protected abstract RecyclerView getRecyclerView();

    @Override
    protected void initView() {
        getSmartRefreshLayout().setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.requestData(true);
            }
        });
        getSmartRefreshLayout().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.requestData(false);
            }
        });

        mLoadService = LoadSir.getDefault().register(
                getSmartRefreshLayout(),
                (Callback.OnReloadListener) context -> mViewModel.requestData(true)
        );
    }

    @Override
    protected void initData() {
        mLoadService.showCallback(LoadingCallback.class);
        mViewModel.getErrorCode().observe(getViewLifecycleOwner(), errorCode -> {
            if (errorCode == null) {
                return;
            }
            if (errorCode == ErrorStatusConfig.ERROR_STATUS_EMPTY) {
                mLoadService.showCallback(EmptyCallback.class);
            } else {
                mLoadService.showCallback(ErrorCallback.class);
            }
        });

        mViewModel.getDataList().observe(getViewLifecycleOwner(), dataList -> {
            SmartRefreshLayout refreshLayout = getSmartRefreshLayout();
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh();
            }
            if (refreshLayout.isLoading()) {
                refreshLayout.finishLoadMore();
            }
            mLoadService.showSuccess();
            onDataChanged(dataList.getList());
        });

        mViewModel.getIsLoadMore().observe(getViewLifecycleOwner(), isLoadMore -> {
            getSmartRefreshLayout().setEnableLoadMore(isLoadMore);
            if (!isLoadMore) {
                Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_LONG).show();
            }
        });

        mViewModel.requestData(true);
    }

    protected abstract void onDataChanged(List<T> dataList);
}
