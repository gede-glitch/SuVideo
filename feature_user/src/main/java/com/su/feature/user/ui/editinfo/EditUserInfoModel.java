package com.su.feature.user.ui.editinfo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.su.feature.user.api.UserApiServiceProvider;
import com.su.feature.user.bean.ReqUpdateUserInfo;
import com.su.feature.user.bean.ResUpload;
import com.su.library.base.BaseApplication;
import com.su.library.base.IRequestCallback;
import com.su.library.bean.UserInfo;
import com.su.library.config.ErrorStatusConfig;
import com.su.library.manager.UserManager;
import com.su.network.ApiCall;
import com.su.network.bean.ResBase;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;

public class EditUserInfoModel {
    public boolean isLogin() {
        return UserManager.getInstance().isLogin();
    }

    /**
     * 从本地加载当前用户信息（用于回显到编辑页）
     */
    public UserInfo getUserInfo() {
        UserInfo userInfo = null;
        if (isLogin()) {
            userInfo = UserManager.getInstance().getUserInfo().getUser();
        }
        return userInfo;
    }

    /**
     * 上传文件
     *
     * @param uri
     * @param callback
     */
    public void updateFile(Uri uri, IRequestCallback<ResUpload> callback) {
        MultipartBody.Part multipartBody = createMultipartBody(uri);
        String token = UserManager.getInstance().getToken();
        Call<ResBase<ResUpload>> resBaseCall = UserApiServiceProvider.getUserApiService().uploadFile(token, multipartBody);
        ApiCall.enqueueCall(resBaseCall, new ApiCall.SimpleCallback<ResBase<ResUpload>>() {
            @Override
            public void onSuccess(ResBase<ResUpload> data) {
                callback.onLoadFinish(data.getData());
            }

            @Override
            public void onEmpty() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_EMPTY, "server is empty");
            }

            @Override
            public void onServerError(String message) {
                callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, message);
            }

            @Override
            public void onNetworkError() {
                callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "network is error");
            }
        });
    }

    /**
     * 根据Uri生成一个上传的对象
     * 创建了一个自定义的 RequestBody 对象，用于将 Uri 指向的文件内容以流式方式上传到服务器
     *
     * @param uri
     */
    public MultipartBody.Part createMultipartBody(Uri uri) {
        ContentResolver contentResolver = BaseApplication.getContext().getContentResolver();
        String type = contentResolver.getType(uri);

        RequestBody requestBody = new RequestBody() {

            @Override
            public void writeTo(@NonNull BufferedSink bufferedSink) throws IOException {
                try (InputStream inputStream = contentResolver.openInputStream(uri)) {
                    byte[] buffer = new byte[4096];// 每次读取 4KB 缓冲区
                    int read;
                    while ((read = inputStream.read(buffer)) != -1) {
                        bufferedSink.write(buffer, 0, read);
                    }
                }
            }

            @Nullable
            @Override
            public MediaType contentType() {
                return MediaType.parse(type);
            }
        };

        MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                "file",
                getFileNameFromUri(uri),
                requestBody
        );
        return filePart;
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        if (uri.getScheme().equals("content")) {
            ContentResolver contentResolver = BaseApplication.getContext().getContentResolver();
            try (Cursor cursor = contentResolver.query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        fileName = cursor.getString(nameIndex);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (uri.getScheme().equals("file")) {
            fileName = new File(uri.getPath()).getName();
        }
        return fileName != null ? fileName : "unnamed_file";
    }

    public void updateUserInfo(String avatarUrl, String nickName, String bio, IRequestCallback<ResBase> callback) {
        if (isLogin()) {
            ReqUpdateUserInfo reqUpdateUserInfo = new ReqUpdateUserInfo();
            reqUpdateUserInfo.setAvatar(avatarUrl);
            reqUpdateUserInfo.setNickname(nickName);
            reqUpdateUserInfo.setBio(bio);

            String token = UserManager.getInstance().getToken();
            Call<ResBase<Void>> resBaseCall = UserApiServiceProvider.getUserApiService().updateUserProfile(token, reqUpdateUserInfo);
            ApiCall.enqueueCall(resBaseCall, new ApiCall.SimpleCallback<ResBase<Void>>() {
                @Override
                public void onSuccess(ResBase<Void> data) {
                    UserManager.getInstance().updateUserInfo(avatarUrl, nickName, bio);
                    callback.onLoadFinish(data);
                }

                @Override
                public void onEmpty() {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_EMPTY, "server is empty");
                }

                @Override
                public void onServerError(String message) {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_SERVER_REQUEST, message);
                }

                @Override
                public void onNetworkError() {
                    callback.onLoadFail(ErrorStatusConfig.ERROR_STATUS_NETWORK_FAIL, "network is error");
                }
            });
        }
    }
}
