package com.su.network;

import com.su.network.bean.ResBase;
import com.su.network.bean.ResList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiCall {
    /**
     * 通用列表请求回调（network 层不依赖 library_base，故自定接口）
     */
    public interface ListCallback<T> {
        void onSuccess(boolean isFirst, ResList<T> data);

        void onEmpty();

        void onServerError(String message);

        void onNetworkError();
    }

    /**
     * 统一执行列表网络请求，封装 enqueue + 响应解析 + 错误分发
     */
    public static <T> void enqueueListCall(Call<ResBase<ResList<T>>> call,
                                           boolean isFirst,
                                           ListCallback<T> callback) {
        call.enqueue(new Callback<ResBase<ResList<T>>>() {
            @Override
            public void onResponse(Call<ResBase<ResList<T>>> call, Response<ResBase<ResList<T>>> response) {
                if (response.isSuccessful()) {
                    ResBase<ResList<T>> body = response.body();
                    if (body != null && body.getCode() == 1) {
                        if (body.getData() != null && !body.getData().getList().isEmpty()) {
                            callback.onSuccess(isFirst, body.getData());
                        } else {
                            callback.onEmpty();
                        }
                    } else {
                        callback.onServerError(body != null ? body.getMsg() : "server error");
                    }
                } else {
                    callback.onNetworkError();
                }
            }

            @Override
            public void onFailure(Call<ResBase<ResList<T>>> call, Throwable throwable) {
                callback.onNetworkError();
            }
        });
    }

    /**
     * 非分页通用请求回调
     */
    public interface SimpleCallback<T> {
        void onSuccess(T data);

        void onEmpty();

        void onServerError(String message);

        void onNetworkError();
    }

    /**
     * 统一执行非分页请求（没有 ResList 包裹，直接取 body.getData()）
     */
    public static <T> void enqueueCall(Call<ResBase<T>> call,
                                       SimpleCallback<ResBase<T>> callback) {
        call.enqueue(new Callback<ResBase<T>>() {

            @Override
            public void onResponse(Call<ResBase<T>> call, Response<ResBase<T>> response) {
                if (response.isSuccessful()) {
                    ResBase<T> body = response.body();
                    if (body != null && body.getCode() == 1) {
                        callback.onSuccess(body);
                    } else {
                        callback.onServerError(
                                body != null ? body.getMsg() : "server request error");
                    }
                } else {
                    callback.onNetworkError();
                }
            }

            @Override
            public void onFailure(Call<ResBase<T>> call, Throwable throwable) {
                callback.onNetworkError();
            }
        });
    }
}
