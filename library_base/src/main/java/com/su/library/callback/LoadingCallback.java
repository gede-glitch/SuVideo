package com.su.library.callback;

import com.kingja.loadsir.callback.Callback;
import com.su.library.base.R;

public class LoadingCallback extends Callback{
    @Override
    protected int onCreateView() {
        return R.layout.layout_loading;
    }
}
