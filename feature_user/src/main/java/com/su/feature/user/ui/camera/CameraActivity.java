package com.su.feature.user.ui.camera;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.su.feature.user.BR;
import com.su.feature.user.R;
import com.su.feature.user.databinding.ActivityCameraBinding;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;

@Route(path = ARouterPath.User.ACTIVITY_SETTINGS_CAMERA)
public class CameraActivity extends BaseActivity<CameraViewModel, ActivityCameraBinding> {

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_camera;
    }

    @Override
    protected CameraViewModel getViewModel() {
        return new ViewModelProvider(this).get(CameraViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.cameraViewModel;
    }
}