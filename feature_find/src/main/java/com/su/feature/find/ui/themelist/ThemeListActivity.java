package com.su.feature.find.ui.themelist;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.feature.find.R;
import com.su.feature.find.adapter.ThemeListAdapter;
import com.su.feature.find.databinding.ActivityThemeListBinding;
import com.su.library.base.BaseActivity;
import com.su.library.base.BaseViewModel;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

@Route(path = ARouterPath.Find.ACTIVITY_THEME_LIST)
public class ThemeListActivity extends BaseActivity<ThemeListViewModel, ActivityThemeListBinding> {
    private ProgressBar mProgressBar;
    private ThemeListAdapter mAdapter;

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2Views(mViewDataBinding.getRoot(), mViewDataBinding.ivBack, mViewDataBinding.tvTitle);
        initProgressBar();
        initShow();
        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ThemeListAdapter();
        mAdapter.setListener(new ThemeListAdapter.onItemClickListener() {
            @Override
            public void onVideoClick(int id) {
                ARouter.getInstance().build(ARouterPath.Video.VIDEO_DETAIL)
                        .withInt(ARouterPath.Video.KEY_VIDEO_ID, id)
                        .navigation();
            }
        });

        mViewDataBinding.recyclerView.setAdapter(mAdapter);
    }

    private void initShow() {
        if (mViewModel != null) {
            mViewModel.getShowLoading().observe(this, show -> {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            });
        }
    }

    private void initProgressBar() {
        mProgressBar = new ProgressBar(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setVisibility(View.GONE);
        ConstraintLayout constraintLayout = (ConstraintLayout) mViewDataBinding.getRoot();
        constraintLayout.addView(mProgressBar);
    }

    @Override
    protected void initData() {
        mViewModel.getData().observe(this, datas -> {
            mAdapter.setData(datas);
        });
        mViewModel.requestData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_theme_list;
    }

    @Override
    protected ThemeListViewModel getViewModel() {
        return new ViewModelProvider(this).get(ThemeListViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.themeListViewModel;
    }
}