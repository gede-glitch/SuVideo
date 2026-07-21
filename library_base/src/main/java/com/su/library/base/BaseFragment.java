package com.su.library.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.alibaba.android.arouter.launcher.ARouter;

public abstract class BaseFragment<VM extends BaseViewModel, VDB extends ViewDataBinding> extends Fragment {
    protected VM mViewModel;
    protected VDB mDataBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        mDataBinding.setLifecycleOwner(this);

        VM viewModel = getViewModel();
        if (viewModel != null) {
            mViewModel = getViewModel();
        }

        int bindingVariableId = getBindingVariableId();
        if (bindingVariableId != 0) {
            mDataBinding.setVariable(getBindingVariableId(), mViewModel);
            mDataBinding.executePendingBindings();
        }
        return mDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ARouter.getInstance().inject(this);
//        initToast();
        initView();
        initData();
    }

//    private void initToast() {
//        mViewModel.getToastText().observe(getViewLifecycleOwner(), text->{
//            if (text != null && !text.isEmpty()) {
//                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    protected abstract int getBindingVariableId();

    protected abstract VM getViewModel();

    protected abstract int getLayoutResId();

    protected abstract void initView();

    protected abstract void initData();
}
