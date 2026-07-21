package com.su.library.base;

import java.util.List;

public interface IRequestCallback<T> {
    void onLoadFinish(T datas);
    void onLoadFail(int statusCode, String message);
}
