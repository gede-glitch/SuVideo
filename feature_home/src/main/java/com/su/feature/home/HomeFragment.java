package com.su.feature.home;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.su.feature.mediaplayer.ui.videolist.VideoListFragment;
import com.su.feature.home.databinding.LayoutFragmentHomeBinding;
import com.su.library.base.BaseFragment;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

import java.util.ArrayList;

@Route(path = ARouterPath.Home.FRAGMENT_HOME)
public class HomeFragment extends BaseFragment<HomeViewModel, LayoutFragmentHomeBinding> {
    private static final String TAG = "HomeFragment";

    @Override
    protected int getBindingVariableId() {
        return 0;
    }

    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_home;
    }

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mDataBinding.getRoot());
        mDataBinding.viewPager2.setAdapter(new FragmentStateAdapter(requireActivity()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                VideoListFragment  fragment = new VideoListFragment ();
                Bundle args = new Bundle();
                args.putInt(ARouterPath.Video.KEY_VIDEO_LIST_TYPE,
                        position == 0
                                ? ARouterPath.Video.VIDEO_LIST_FRAGMENT_RECOMMEND
                                : ARouterPath.Video.VIDEO_LIST_FRAGMENT_DAILY);
                fragment.setArguments(args);
                return fragment;
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        // 当用户在屏幕上用手指左右滑动切换页面时，告诉导航栏（RadioGroup）该高亮哪个按钮
        mDataBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            /**
             * 当 ViewPager 滑动完毕之后
             * @param position Position index of the new selected page.
             */
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        mDataBinding.rbRecommend.setChecked(true);
                        break;
                    case 1:
                        mDataBinding.rbDaily.setChecked(true);
                        break;
                    default:
                        Log.e(TAG, "onPageSelected: selected error fragment:" + position);
                        mDataBinding.rbRecommend.setChecked(true);
                        break;
                }
            }
        });

        // 当用户手动点击导航栏上的某个按钮时，告诉下面对应的页面（ViewPager2）该翻到哪一页
        mDataBinding.rgIndictor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == mDataBinding.rbRecommend.getId()) {
                    mDataBinding.viewPager2.setCurrentItem(ARouterPath.Video.VIDEO_LIST_FRAGMENT_RECOMMEND);
                } else if (checkedId == mDataBinding.rbDaily.getId()) {
                    mDataBinding.viewPager2.setCurrentItem(ARouterPath.Video.VIDEO_LIST_FRAGMENT_DAILY);
                } else {
                    Log.e(TAG, "onCheckedChanged: changed error bindId:" + checkedId);
                    mDataBinding.viewPager2.setCurrentItem(ARouterPath.Video.VIDEO_LIST_FRAGMENT_RECOMMEND);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
