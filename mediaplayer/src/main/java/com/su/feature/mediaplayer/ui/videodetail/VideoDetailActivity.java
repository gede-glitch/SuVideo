package com.su.feature.mediaplayer.ui.videodetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.data.video.bean.ResComment;
import com.su.data.video.bean.ResVideoDetail;
import com.su.feature.mediaplayer.BR;
import com.su.feature.mediaplayer.R;
import com.su.feature.mediaplayer.databinding.ActivityVideoDetailBinding;
import com.su.feature.mediaplayer.player.MediaPlayerManager;
import com.su.feature.mediaplayer.ui.comment.VideoCommentFragment;
import com.su.feature.mediaplayer.ui.introduce.IntroduceFragment;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.utils.StatusBarUtils;
import com.zhengsr.tablib.view.adapter.TabFlowAdapter;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.Video.VIDEO_DETAIL)
public class VideoDetailActivity extends BaseActivity<VideoDetailViewModel, ActivityVideoDetailBinding> {
    private static final String TAG = "VideoDetailActivity";

    @Autowired(name = ARouterPath.Video.KEY_VIDEO_ID)
    public int mVideoId;
    private ProgressBar mProgressBar;
    private IntroduceFragment mIntroduceFragment;
    private VideoCommentFragment mCommentFragment;
    private MediaPlayerManager mPlayerManager;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mVideoId = intent.getIntExtra(ARouterPath.Video.KEY_VIDEO_ID, 0);
        mViewModel.requestDetail(mVideoId);
        mViewDataBinding.nsl.scrollTo(0, 0);
    }

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
        initProgressBar();
        initShow();
        initViewPager();
        initTab();
        initPlayer();
    }

    private void initPlayer() {
        mPlayerManager = MediaPlayerManager.getInstance(this);
        mPlayerManager.bindPlayView(mViewDataBinding.playView);
    }

    private void initTab() {
        mViewDataBinding.tabLayout.setViewPager(mViewDataBinding.viewPager);
        ArrayList arrayList = new ArrayList();
        arrayList.add("简介");
        arrayList.add("评论");
        mViewDataBinding.tabLayout.setAdapter(new TabFlowAdapter(arrayList));
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
        if (!(root instanceof ConstraintLayout)){
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

    private void initViewPager() {
        mIntroduceFragment = (IntroduceFragment) ARouter.getInstance()
                .build(ARouterPath.Video.INTRODUCE)
                .navigation();
        mCommentFragment = (VideoCommentFragment) ARouter.getInstance()
                .build(ARouterPath.Video.VIDEO_COMMENT)
                .navigation();
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(mIntroduceFragment);
        fragments.add(mCommentFragment);

        mViewDataBinding.viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }
        });

        mViewDataBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    mIntroduceFragment.updateFragmentHeight();
                } else if (position == 1) {
                    mCommentFragment.updateFragmentHeight();
                }
            }
        });
    }

    @Override
    protected void initData() {
        mViewModel.requestDetail(mVideoId);

        mViewModel.getVideoInfo().observe(this, new Observer<ResVideoDetail.ArchivesInfoDTO>() {
            @Override
            public void onChanged(ResVideoDetail.ArchivesInfoDTO archivesInfoDTO) {
                mIntroduceFragment.updateArchivesInfo(archivesInfoDTO);
                String url = archivesInfoDTO.getVideoFile();
            }
        });

        mViewModel.getCommentList().observe(this, new Observer<List<ResComment>>() {
            @Override
            public void onChanged(List<ResComment> resComments) {
                mCommentFragment.updateComments(resComments);
            }
        });

        mViewModel.getVideoUrl().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String url) {
                if (url != null) {
                    mPlayerManager.play(url);
                }
            }
        });

        mViewModel.getErrorCode().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer errorCode) {
                if (errorCode == ErrorStatusConfig.ERROR_STATUS_NOT_LOGIN) {
                    ARouter.getInstance().build(ARouterPath.User.ACTIVITY_USER).navigation();
                }
            }
        });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected VideoDetailViewModel getViewModel() {
        return new ViewModelProvider(this).get(VideoDetailViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.detailViewModel;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPlayerManager.setPlayWhenReady(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayerManager.setPlayWhenReady(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerManager.release();
    }
}