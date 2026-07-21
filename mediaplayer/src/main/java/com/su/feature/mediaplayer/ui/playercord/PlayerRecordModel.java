package com.su.feature.mediaplayer.ui.playercord;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.su.feature.mediaplayer.db.VideoHistory;
import com.su.feature.mediaplayer.db.VideoHistoryRepository;
import com.su.library.base.BaseApplication;
import com.su.library.base.IRequestCallback;
import com.su.library.config.ARouterPath;
import com.su.library.manager.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class PlayerRecordModel {
    public void requestHistory(IRequestCallback<List<VideoHistory>> callback) {

        VideoHistoryRepository repository = new VideoHistoryRepository(BaseApplication.getContext());
        //通过usedid来查询浏览记录
        String userId = "0";
        if (UserManager.getInstance().isLogin()) {
            userId = UserManager.getInstance().getUserInfo().getUser().getId();
        }
        //查询
        repository.query(userId, callback);
    }

    public void deleteByIds(HashMap<VideoHistory, Boolean> selectDelDatas, IRequestCallback<String> callback) {
        VideoHistoryRepository repository = new VideoHistoryRepository(BaseApplication.getContext());
        //通过usedid来查询浏览记录
        String userId = "0";
        if (UserManager.getInstance().isLogin()) {
            userId = UserManager.getInstance().getUserInfo().getUser().getId();
        }

        List<Integer> ids = new ArrayList<>();

        Set<VideoHistory> histories = selectDelDatas.keySet();
        for (VideoHistory history : histories) {
            ids.add(history.getVideoId());
        }

        repository.deleteByIds(userId, ids, callback);
    }
}
