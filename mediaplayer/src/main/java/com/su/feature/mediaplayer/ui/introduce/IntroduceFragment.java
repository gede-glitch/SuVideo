package com.su.feature.mediaplayer.ui.introduce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.data.video.bean.ResVideoDetail;
import com.su.feature.mediaplayer.BR;
import com.su.feature.mediaplayer.R;
import com.su.feature.mediaplayer.databinding.FragmentIntroduceBinding;
import com.su.feature.mediaplayer.ui.comment.report.CommentReportPopupWindow;
import com.su.feature.mediaplayer.ui.videodetail.VideoDetailViewModel;
import com.su.feature.mediaplayer.ui.videolist.VideoListFragment;
import com.su.library.base.BaseFragment;
import com.su.library.config.ARouterPath;

import android.os.Handler;
import android.util.Log;
import android.view.View;

@Route(path = ARouterPath.Video.INTRODUCE)
public class IntroduceFragment extends BaseFragment<VideoDetailViewModel, FragmentIntroduceBinding> {
    private static final String TAG = "IntroduceFragment";
    private CommentReportPopupWindow mPopupWindow;
    @Override
    protected int getBindingVariableId() {
        return BR.introduceViewModel;
    }

    @Override
    protected VideoDetailViewModel getViewModel() {
        return new ViewModelProvider(requireActivity()).get(VideoDetailViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_introduce;
    }

    @Override
    protected void initView() {
        VideoListFragment navigation = (VideoListFragment) ARouter.getInstance().build(ARouterPath.Video.FRAGMENT_VIDEO_LIST)
                .withInt(ARouterPath.Video.KEY_VIDEO_LIST_TYPE, ARouterPath.Video.VIDEO_LIST_FRAGMENT_RECOMMEND)
                .withBoolean(ARouterPath.Video.KEY_VIDEO_LIST_STYLE, true)
                .navigation();

        mDataBinding.ivComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomPopupWindow();
            }
        });

        getChildFragmentManager().beginTransaction().add(mDataBinding.fcv.getId(), navigation).commit();
    }

    private void showBottomPopupWindow() {
        if (mPopupWindow == null) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            mPopupWindow = new CommentReportPopupWindow(activity);
            mPopupWindow.setOnPopupInteractionListener(new CommentReportPopupWindow.OnPopupInteractionListener() {
                @Override
                public void onSendMessage(String message) {
                    mViewModel.sendComment(message);
                }
            });
        }
        mPopupWindow.showPopup(mDataBinding.getRoot());
    }

    @Override
    protected void initData() {

    }

    public void updateArchivesInfo(ResVideoDetail.ArchivesInfoDTO archivesInfoDTO) {
        ResVideoDetail.ArchivesInfoDTO archivesInfoDTO1 = archivesInfoDTO;
    }

    public void updateFragmentHeight() {
        new Handler().postDelayed(() -> {
            Log.i(TAG, "updateFragmentHeight: " + mDataBinding.getRoot().getHeight());
            mDataBinding.getRoot().requestLayout();
        }, 500);
    }
}
