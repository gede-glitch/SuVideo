package com.su.feature.mediaplayer.ui.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.data.video.bean.ResVideoDetail;
import com.su.feature.mediaplayer.BR;
import com.su.feature.mediaplayer.R;
import com.su.feature.mediaplayer.adapter.SearchAdapter;
import com.su.feature.mediaplayer.databinding.ActivitySearchBinding;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

import java.util.List;


@Route(path = ARouterPath.Video.ACTIVITY_SEARCH)
public class SearchActivity extends BaseActivity<SearchViewModel, ActivitySearchBinding> {
    private SearchAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
        initProgressBar();
        initShow();
        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchAdapter();
        mAdapter.setListener(new SearchAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int id) {
                ARouter.getInstance().build(ARouterPath.Video.VIDEO_DETAIL)
                        .withInt(ARouterPath.Video.KEY_VIDEO_ID, id)
                        .navigation();
            }
        });
        mViewDataBinding.recyclerView.setAdapter(mAdapter);

        mViewDataBinding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    mViewModel.searchByKeyword();
                    return true;
                } else {
                    return false;
                }
            }
        });

        mViewModel.getSearchKeyword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mViewModel.update(s != null && !s.isEmpty());
            }
        });
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

    private void initShow() {
        if (mViewModel != null) {
            mViewModel.getShowLoading().observe(this, show -> {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            });
        }
    }

    @Override
    protected void initData() {
        mViewModel.getDatas().observe(this, new Observer<List<ResVideoDetail.ArchivesInfoDTO>>() {
            @Override
            public void onChanged(List<ResVideoDetail.ArchivesInfoDTO> archivesInfoDTOS) {
                mAdapter.setData(archivesInfoDTOS);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchViewModel getViewModel() {
        return new ViewModelProvider(this).get(SearchViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.searchViewModel;
    }
}