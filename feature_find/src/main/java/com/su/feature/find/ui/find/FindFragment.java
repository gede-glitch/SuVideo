package com.su.feature.find.ui.find;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.su.feature.find.BR;
import com.su.feature.find.R;
import com.su.feature.find.adapter.AnchorAdapter;
import com.su.feature.find.adapter.CategoryAdapter;
import com.su.feature.find.bean.ResFindAnchor;
import com.su.data.video.bean.ResFindCategory;

import com.su.feature.find.databinding.LayoutFragmentFindBinding;
import com.su.library.base.BaseFragment;
import com.su.library.callback.EmptyCallback;
import com.su.library.callback.ErrorCallback;
import com.su.library.callback.LoadingCallback;
import com.su.library.config.ARouterPath;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.utils.StatusBarUtils;

import java.util.List;

@Route(path = ARouterPath.Find.FRAGMENT_FIND)
public class FindFragment extends BaseFragment<FindViewModel, LayoutFragmentFindBinding> implements CategoryAdapter.CategoryListener {
    private static final String TAG = "FindFragment";
    private CategoryAdapter mCategoryAdapter;
    private AnchorAdapter mAnchorAdapter;
    private LoadService mLoadService;


    @Override
    protected int getBindingVariableId() {
        return BR.viewModel;
    }

    @Override
    protected FindViewModel getViewModel() {
        return new ViewModelProvider(this).get(FindViewModel.class);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_fragment_find;
    }

    @Override
    protected void initView() {
        StatusBarUtils.addStatusBarHeight2RootView(mDataBinding.getRoot());
        Log.i(TAG, "initView: Category");
        GridLayoutManager categoryLayoutManager = new GridLayoutManager(getContext(), 3);
        mDataBinding.rcvCategory.setLayoutManager(categoryLayoutManager);
        mCategoryAdapter = new CategoryAdapter();
        mCategoryAdapter.setListener(this);
        mDataBinding.rcvCategory.setAdapter(mCategoryAdapter);

        Log.i(TAG, "initView: Anchor");
        LinearLayoutManager anchorLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        int space12 = (int) (12 * getResources().getDisplayMetrics().density);
        mDataBinding.rcvAnchor.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position != parent.getAdapter().getItemCount() - 1) {
                    outRect.right = space12;
                }
            }
        });
        mDataBinding.rcvAnchor.setLayoutManager(anchorLayoutManager);
        mAnchorAdapter = new AnchorAdapter();
        mAnchorAdapter.setListener(new AnchorAdapter.OnItemClickListener() {
            @Override
            public void onThemeListClick() {
                ARouter.getInstance().build(ARouterPath.Find.ACTIVITY_THEME_LIST)
                        .navigation();
            }
        });
        mDataBinding.rcvAnchor.setAdapter(mAnchorAdapter);

        mViewModel.getAction().observe(this, action->{
            if (action == FindViewModel.FindAction.NAVIGATION_TO_THEME_LIST) {
                ARouter.getInstance().build(ARouterPath.Find.ACTIVITY_THEME_LIST)
                        .navigation();
            } else if (action == FindViewModel.FindAction.NAVIGATION_TO_TOPIC) {
                ARouter.getInstance().build(ARouterPath.Find.ACTIVITY_TOPIC_INFO)
                        .navigation();
            }
        });

        mDataBinding.etInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.Video.ACTIVITY_SEARCH).navigation();
            }
        });

        mLoadService = LoadSir.getDefault().register(
                mDataBinding.nestedScrollView,
                (Callback.OnReloadListener) context -> mViewModel.loadFindData()
        );
    }

    @Override
    protected void initData() {
        mLoadService.showCallback(LoadingCallback.class);

        mViewModel.getErrorCode().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null) return;
                if (integer == ErrorStatusConfig.ERROR_STATUS_EMPTY) {
                    mLoadService.showCallback(EmptyCallback.class);
                } else {
                    mLoadService.showCallback(ErrorCallback.class);
                }
            }
        });

        mViewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<List<ResFindCategory>>() {
            @Override
            public void onChanged(List<ResFindCategory> resFindCategories) {
                mCategoryAdapter.setDatas(resFindCategories);
                mLoadService.showSuccess();
            }
        });

        mViewModel.getAnchor().observe(getViewLifecycleOwner(), new Observer<List<ResFindAnchor>>() {
            @Override
            public void onChanged(List<ResFindAnchor> resFindAnchors) {
                mAnchorAdapter.setDatas(resFindAnchors);
                mLoadService.showSuccess();
            }
        });

        mViewModel.loadFindData();
    }

    @Override
    public void onCategroyItemClick(ResFindCategory category) {
        ARouter.getInstance().build(ARouterPath.Find.ACTIVITY_CATEGORY_DETAIL)
                .withParcelable(ARouterPath.Find.KEY_CATEGORY_DATA, category)
                .navigation();
    }
}
