package com.su.feature.find.ui.topic;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.su.feature.find.R;
import com.su.feature.find.adapter.TopicAdapter;
import com.su.feature.find.bean.ResTopic;
import com.su.feature.find.databinding.ActivityTopicBinding;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

import java.util.List;

@Route(path = ARouterPath.Find.ACTIVITY_TOPIC_INFO)
public class TopicActivity extends BaseActivity<TopicViewModel, ActivityTopicBinding> {
    private ProgressBar mProgressBar;
    private TopicAdapter mAdapter;
    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
        initProgressBar();
        initShow();
        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TopicAdapter();
        mViewDataBinding.recyclerView.setAdapter(mAdapter);
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
        mViewModel.getData().observe(this, new Observer<List<ResTopic>>() {
            @Override
            public void onChanged(List<ResTopic> resTopics) {
                mAdapter.setData(resTopics);
            }
        });
        mViewModel.requestData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_topic;
    }

    @Override
    protected TopicViewModel getViewModel() {
        return new ViewModelProvider(this).get(TopicViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.topicViewModel;
    }
}