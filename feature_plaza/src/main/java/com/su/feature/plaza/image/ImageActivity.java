package com.su.feature.plaza.image;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.feature.plaza.BR;
import com.su.feature.plaza.R;
import com.su.feature.plaza.bean.ResPlaza;
import com.su.feature.plaza.databinding.ActivityImageBinding;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterPath.Plaza.IMAGE_ACTIVITY)
public class ImageActivity extends BaseActivity<ImageViewModel, ActivityImageBinding> {

    @Autowired(name = ARouterPath.Plaza.KEY_IMAGE_DATA)
    public ResPlaza.PlazaDetail mDetail;

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mViewDataBinding.getRoot());
        initViewPager2();
    }

    private void initViewPager2() {
        List<String> images = mDetail.getImages();
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            String Url = images.get(i);
            Fragment fragment = (Fragment) ARouter.getInstance().build(ARouterPath.Plaza.FRAGMENT_IMAGE_DETAIL)
                    .withString("KEY_IMAGE_URL", Url).navigation();
            fragments.add(fragment);
        }

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

        mViewDataBinding.viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                String format = String.format("%s/%s", position + 1, fragments.size());
                mViewDataBinding.tvTitle.setText(format);
            }
        });
    }

    @Override
    protected void initData() {
        mViewModel.updateData(mDetail);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_image;
    }

    @Override
    protected ImageViewModel getViewModel() {
        return new ViewModelProvider(this).get(ImageViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.imageViewModel;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.anim_activity_top2bottom);
    }
}