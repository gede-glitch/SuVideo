package com.su.library.list;

import com.su.network.bean.ResList;

public interface IListListener<T> {
    void onLoadFinish(boolean isFirst, ResList<T> dataList);
    void onLoadFail(int statusCode, String message);
}
