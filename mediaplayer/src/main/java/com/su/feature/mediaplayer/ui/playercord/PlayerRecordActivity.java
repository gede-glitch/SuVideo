package com.su.feature.mediaplayer.ui.playercord;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.feature.mediaplayer.BR;
import com.su.feature.mediaplayer.R;
import com.su.feature.mediaplayer.adapter.VideoHistoryAdapter;
import com.su.feature.mediaplayer.databinding.ActivityPlayerRecordBinding;
import com.su.feature.mediaplayer.db.VideoHistory;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

import java.util.List;

@Route(path = ARouterPath.Video.ACTIVITY_PLAYRECORD)
public class PlayerRecordActivity extends BaseActivity<PlayerRecordViewModel, ActivityPlayerRecordBinding> {
    private ProgressBar mProgressBar;
    private VideoHistoryAdapter mAdapter;

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2Views(mViewDataBinding.getRoot(), mViewDataBinding.ivBack, mViewDataBinding.tvTitle);
        initProgressBar();
        initShow();
        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VideoHistoryAdapter();
        mAdapter.setListener(new VideoHistoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int videoId) {
                //跳转到视频详情页
                ARouter.getInstance()
                        .build(ARouterPath.Video.VIDEO_DETAIL)
                        .withInt(ARouterPath.Video.KEY_VIDEO_ID, videoId)
                        .navigation();

            }

            @Override
            public void onDelSelect(VideoHistory videoHistory, boolean isSelect) {
                mViewModel.updateDelSelectDatas(videoHistory, isSelect);
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
        View root = mViewDataBinding.getRoot();
        if (!(root instanceof ConstraintLayout)) {
            return;
        }
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
        mViewModel.getDatas().observe(this, new Observer<List<VideoHistory>>() {
            @Override
            public void onChanged(List<VideoHistory> videoHistories) {
                mAdapter.setDatas(videoHistories);
            }
        });

        mViewModel.getSelectStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                mAdapter.updateSelectStatus(aBoolean);
            }
        });

        mViewModel.requestHistory();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_player_record;
    }

    @Override
    protected PlayerRecordViewModel getViewModel() {
        return new ViewModelProvider(this).get(PlayerRecordViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.recordViewModel;
    }
}