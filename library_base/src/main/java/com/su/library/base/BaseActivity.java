package com.su.library.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.su.library.utils.StatusBarUtils;

public abstract class BaseActivity<VM extends BaseViewModel, VDB extends ViewDataBinding> extends AppCompatActivity {

    protected VM mViewModel;
    protected VDB mViewDataBinding;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        initDataBinding();

        StatusBarUtils.setImmerseStatusBar(this);

        ARouter.getInstance().inject(this);

        initView();
        initData();
        mViewModel.getFinish().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    finish();
                }
            }
        });

    }

    private void initToast() {
        mViewModel.getToastText().observe(this, text -> {
            if (text != null && !text.isEmpty()) {
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutResId());
        mViewDataBinding.setLifecycleOwner(this);
        // 和 ViewModel 进行绑定
        mViewDataBinding.setVariable(getBindingVariableId(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    private void initViewModel() {
        mViewModel = getViewModel();

        if (mViewModel != null) {
            initToast();
        }
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutResId();

    protected abstract VM getViewModel();

    protected abstract int getBindingVariableId();

}
