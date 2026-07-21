package com.su.feature.find.ui.categorydetail;

import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.data.video.bean.ResFindCategory;
import com.su.feature.find.BR;
import com.su.feature.find.R;
import com.su.feature.find.databinding.ActivityCategoryDetailBinding;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

import java.util.ArrayList;

@Route(path = ARouterPath.Find.ACTIVITY_CATEGORY_DETAIL)
public class CategoryDetailActivity extends BaseActivity<CategoryDetailViewModel, ActivityCategoryDetailBinding> {
    private static final String TAG = "CategoryDetailActivity";
    @Autowired(name = ARouterPath.Find.KEY_CATEGORY_DATA)
    ResFindCategory mDetail;

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2Views(
                mViewDataBinding.getRoot(),
                mViewDataBinding.ivBack,
                mViewDataBinding.ivShare
        );
        initViewPager();
    }

    private void initViewPager() {
        Fragment recommendFragment = (Fragment) ARouter.getInstance()
                .build(ARouterPath.Video.FRAGMENT_CATEGORY_LIST)
                .withInt(ARouterPath.Video.KEY_CATEGORY_TYPE, ARouterPath.Video.CATEGORY_VIDEO_RECOMMEND)
                .withInt(ARouterPath.Video.KEY_CATEGORY_ID, mDetail.getId())
                .navigation();
        //最新发布
        Fragment publishFragment = (Fragment) ARouter.getInstance()
                .build(ARouterPath.Video.FRAGMENT_CATEGORY_LIST)
                .withInt(ARouterPath.Video.KEY_CATEGORY_TYPE, ARouterPath.Video.CATEGORY_VIDEO_NEWPUBLISH)
                .withInt(ARouterPath.Video.KEY_CATEGORY_ID, mDetail.getId())
                .navigation();

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(recommendFragment);
        fragments.add(publishFragment);

        mViewDataBinding.viewPager2.setAdapter(new FragmentStateAdapter(this) {
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

        mViewDataBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                int rbRecommendId = mViewDataBinding.rbRecommend.getId();
                int rbNewId = mViewDataBinding.rbNew.getId();
                if (checkedId == rbRecommendId) {
                    mViewDataBinding.viewPager2.setCurrentItem(0);
                    mViewDataBinding.barrier1.setVisibility(View.VISIBLE);
                    mViewDataBinding.barrier2.setVisibility(View.INVISIBLE);
                } else if (checkedId == rbNewId) {
                    mViewDataBinding.viewPager2.setCurrentItem(1);
                    mViewDataBinding.barrier1.setVisibility(View.INVISIBLE);
                    mViewDataBinding.barrier2.setVisibility(View.VISIBLE);
                }
            }
        });

        mViewDataBinding.viewPager2.setUserInputEnabled(false);
    }

    @Override
    protected void initData() {
        Log.i(TAG, "initData: mDetail=" + mDetail.getId() + "  name= " + mDetail.getName());
        //虽然mDetail已经有数据了，但是分类详情接口包含了浏览人数、参与人数等新字段
        mViewModel.requestDatas(mDetail.getId());
//        mViewDataBinding.radioGroup.setOnCheckedChangeListener((group, checkId) -> {
//            mViewDataBinding.barrier1.setVisibility(
//                    checkId == R.id.rb_recommend ? View.VISIBLE : View.GONE
//            );
//            mViewDataBinding.barrier2.setVisibility(
//                    checkId == R.id.rb_new ? View.VISIBLE : View.GONE
//            );
//        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_category_detail;
    }

    @Override
    protected CategoryDetailViewModel getViewModel() {
        return new ViewModelProvider(this).get(CategoryDetailViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.categoryDetailViewModel;
    }
}
