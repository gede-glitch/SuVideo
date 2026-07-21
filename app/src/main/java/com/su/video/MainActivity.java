package com.su.video;

import android.util.Log;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.video.databinding.ActivityMainBinding;

@Route(path = ARouterPath.Main.ACTIVITY_MAIN)
public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {
    private static final String TAG = "MainActivity";

    @Override
    protected void initView() {
        Fragment homeFragment = (Fragment) ARouter.getInstance().build(ARouterPath.Home.FRAGMENT_HOME).navigation();
        Fragment findFragment = (Fragment) ARouter.getInstance().build(ARouterPath.Find.FRAGMENT_FIND).navigation();
        Fragment plazaFragment = (Fragment) ARouter.getInstance().build(ARouterPath.Plaza.FRAGMENT_PLAZA).navigation();
        Fragment userFragment = (Fragment) ARouter.getInstance().build(ARouterPath.User.FRAGMENT_USER).navigation();

        commitFragment(homeFragment);

        mViewDataBinding.rbBottomNavigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_home) {
                    commitFragment(homeFragment);
                } else if (checkedId == R.id.rb_plaza) {
                    commitFragment(plazaFragment);
                } else if (checkedId == R.id.rb_find) {
                    commitFragment(findFragment);
                } else if (checkedId == R.id.rb_user) {
                    commitFragment(userFragment);
                } else {
                    Log.e(TAG, "RadioGroup 触发了未知的 checkedId: " + checkedId);
                    commitFragment(homeFragment);
                }
            }
        });
    }

    private void commitFragment(Fragment oneFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fcv, oneFragment).commit();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainViewModel getViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.mainViewModel;
    }
}