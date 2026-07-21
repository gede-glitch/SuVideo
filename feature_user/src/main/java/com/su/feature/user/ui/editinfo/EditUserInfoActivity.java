package com.su.feature.user.ui.editinfo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.su.feature.user.BR;
import com.su.feature.user.R;
import com.su.feature.user.config.UserConfig;
import com.su.feature.user.databinding.ActivityEditUserInfoBinding;
import com.su.library.adapter.CommonBindingAdapter;
import com.su.library.base.BaseActivity;
import com.su.library.config.ARouterPath;
import com.su.library.ui.dialog.YesOrNoDialog;
import com.su.library.utils.StatusBarUtils;

@Route(path = ARouterPath.User.ACTIVITY_SETTINGS_EDIT_USER_INFO)
public class EditUserInfoActivity extends BaseActivity<EditUserInfoViewModel, ActivityEditUserInfoBinding> {
    private ActivityResultLauncher<String> pickImageLauncher;
    private ProgressBar mProgressBar;
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private static final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2Views(mViewDataBinding.getRoot(), mViewDataBinding.ivBack, mViewDataBinding.tvTitle, mViewDataBinding.tvSave);
        initProgressBar();
        initShow();

        mViewModel.getAction().observe(this, editUserAction -> {
            switch (editUserAction) {
                case SHOW_AVATAR_SELECT_DIALOG:
                    showAvatarSelectDialog();
                    break;
                case FINISH:
                    super.finish();//调用父类finish，直接关闭
                    break;
            }
        });

        // 初始化ActivityResultLauncher
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null) {
                            CommonBindingAdapter.loadAvatar(mViewDataBinding.ivAvatar, uri.toString());
//                        loadImageFromUri(uri);
//                            String filePath = getFileNameFromUri(uri);
                            mViewModel.uploadAvatar(uri);
                        }
                    }
                }

        );
    }

    @Override
    public void finish() {
        //资料发生变化，退出需要特殊处理
        if (mViewModel.isChange()) {
            YesOrNoDialog.showDialog(this, "提示", "是否保存更新？", new YesOrNoDialog.Callback() {
                @Override
                public void onConfirm() {
                    //提交保存，提交成功后会再执行super.finish
                    mViewModel.onSaveUserInfo();
                }

                @Override
                public void onCancel() {
                    //直接关闭
                    EditUserInfoActivity.super.finish();
                }
            });
        } else {
            super.finish();//一定要调用父类的finish，否则就是调用当前类中的finish
        }
    }

    /**
     * 显示选择头像的弹窗（相册、或者是相机拍摄）     */
    public void showAvatarSelectDialog() {
        PictureSelectDialog pictureSelectDialog = PictureSelectDialog.newInstance();

        pictureSelectDialog.setOnItemClickListenner(new PictureSelectDialog.OnItemClickListenner() {
            @Override
            public void onCameraClick() {

                // 请求相机权限，如果有权限直接启动相机
                if (allPermissionsGranted()) {
                    startCamera();
                } else {
                    ActivityCompat.requestPermissions(EditUserInfoActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                }

            }

            @Override
            public void onAlbumClick() {
                openGallery();
            }
        });

        pictureSelectDialog.show(getSupportFragmentManager(), "PictureSelectDialog");
    }
    /**
     * 打开相册
     */
    private void openGallery() {
        pickImageLauncher.launch("image/*");
    }

    // 检查是否已经获得所有权限
    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // 权限请求结果回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                mViewModel.showToast("权限获取失败，请在系统设置手动授权");
                finish();
            }
        }
    }

    private void startCamera() {
        //需要实现接受回传值：
        //1、navigation(this,100);需要指定请求码  2、在onActivityResult中获取结果
        ARouter.getInstance().build(ARouterPath.User.ACTIVITY_SETTINGS_CAMERA).navigation(this,100);
    }

    private void initShow() {
        if (mViewModel != null) {
            mViewModel.getShowLoading().observe(this, show -> {
                mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果结果码与拍摄照片相同
        if (UserConfig.Camera_CAPTURE.CAPTURE_REQUEST == resultCode){
            //获取照片拍摄结果
            Uri uri = data.getParcelableExtra(UserConfig.Camera_CAPTURE.KEY_CAPTURE_REQUEST);
            mViewModel.uploadAvatar(uri);
        }
    }

    private void initProgressBar() {
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

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_user_info;
    }

    @Override
    protected EditUserInfoViewModel getViewModel() {
        return new ViewModelProvider(this).get(EditUserInfoViewModel.class);
    }

    @Override
    protected int getBindingVariableId() {
        return BR.editUserViewModel;
    }
}
