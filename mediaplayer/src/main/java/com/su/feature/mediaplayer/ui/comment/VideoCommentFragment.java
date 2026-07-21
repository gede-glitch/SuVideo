package com.su.feature.mediaplayer.ui.comment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.su.data.video.bean.ResComment;
import com.su.feature.mediaplayer.BR;
import com.su.feature.mediaplayer.R;
import com.su.feature.mediaplayer.adapter.CommentAdapter;
import com.su.feature.mediaplayer.databinding.FragmentCommentBinding;
import com.su.feature.mediaplayer.ui.comment.delcomment.DeleteCommentDialog;
import com.su.feature.mediaplayer.ui.videodetail.VideoDetailViewModel;
import com.su.library.base.BaseFragment;
import com.su.library.config.ARouterPath;

import java.util.List;

@Route(path = ARouterPath.Video.VIDEO_COMMENT)
public class VideoCommentFragment extends BaseFragment<VideoDetailViewModel, FragmentCommentBinding> {
    private CommentAdapter mCommentAdapter;
    private static final String TAG = "VideoCommentFragment";

    @Override
    protected int getBindingVariableId() {
        return BR.commentViewModel;
    }

    @Override
    protected VideoDetailViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(VideoDetailViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void initView() {
        mDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mCommentAdapter = new CommentAdapter();
        mDataBinding.recyclerView.setAdapter(mCommentAdapter);
        mDataBinding.etChat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String text = mDataBinding.etChat.getText().toString().trim();
                    if (!text.isEmpty()) {
                        mViewModel.sendComment(text);
                        mDataBinding.etChat.getText().clear();
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                view.getWindowVisibleDisplayFrame(r);
                int screenHeight = view.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) { // 键盘显示
                    int navigationBarHeight = 0;
                    int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
                    if (resourceId > 0) {
                        navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
                    }
                    mDataBinding.clComment.setTranslationY(-(keypadHeight - navigationBarHeight));
                } else { // 键盘隐藏
                    mDataBinding.clComment.setTranslationY(0);
                }
            }
        });

        mDataBinding.getRoot().setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                View currentFocus = requireActivity().getCurrentFocus();
                if (currentFocus instanceof EditText) {
                    int[] location = new int[2];
                    currentFocus.getLocationOnScreen(location);
                    int x = (int) event.getRawX();
                    int y = (int) event.getRawY();
                    Rect editRect = new Rect(location[0], location[1],
                            location[0] + currentFocus.getWidth(),
                            location[1] + currentFocus.getHeight());
                    if (!editRect.contains(x, y)) {
                        InputMethodManager imm = (InputMethodManager) requireActivity()
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                        currentFocus.clearFocus();
                    }
                }
            }
            return false; // 不拦截事件，RecyclerView 仍可正常滚动
        });

        mCommentAdapter.setOnItemClickListener(new CommentAdapter.onItemClickListener() {
            @Override
            public void onItemLongClick(ResComment comment) {
                DeleteCommentDialog deleteCommentDialog = new DeleteCommentDialog();
                deleteCommentDialog.setComment(comment);
                deleteCommentDialog.show(getChildFragmentManager(), "DeleteCommentDialog");
            }
        });

        mDataBinding.smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mDataBinding.smartRefreshLayout.isRefreshing()) {
                    mDataBinding.smartRefreshLayout.finishRefresh();
                }
                mDataBinding.smartRefreshLayout.setEnableLoadMore(true);
                mViewModel.requestComments(true);
            }
        });

        mDataBinding.smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mDataBinding.smartRefreshLayout.isLoading()) {
                    mDataBinding.smartRefreshLayout.finishLoadMore();
                }
                mViewModel.requestComments(false);
            }
        });


    }

    @Override
    protected void initData() {
        mViewModel.getIsLoadMore().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoadMore) {
                if (isLoadMore) {
                    mDataBinding.smartRefreshLayout.setEnableLoadMore(true);
                } else {
                    mDataBinding.smartRefreshLayout.setEnableLoadMore(false);
                }
            }
        });
        mViewModel.getCommentList().observe(requireActivity(), new Observer<List<ResComment>>() {
            @Override
            public void onChanged(List<ResComment> commentList) {
                mCommentAdapter.setComments(commentList);
            }
        });
        mViewModel.requestComments(true);
    }

    public void updateFragmentHeight() {
        new Handler().postDelayed(() -> {
            Log.i(TAG, "updateFragmentHeight: " + mDataBinding.getRoot().getHeight());
            mDataBinding.getRoot().requestLayout();
        }, 500);
    }

    public void updateComments(List<ResComment> commentList) {
        for (ResComment resComment : commentList) {

        }

    }
}
