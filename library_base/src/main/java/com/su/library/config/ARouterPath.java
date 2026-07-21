package com.su.library.config;

public class ARouterPath {
    public static class Main {
        private static final String MAIN = "/main";

        // 对应 MainActivity 的路径
        public static final String ACTIVITY_MAIN = MAIN + "/mainActivity";
    }

    public static class Home {
        private static final String HOME = "/home";
        public static final String FRAGMENT_HOME = HOME + "/homeFragment";
    }

    public static class Plaza {
        public static final String KEY_IMAGE_URL = "KEY_IMAGE_URL";
        private static final String PLAZA = "/plaza";
        public static final String FRAGMENT_PLAZA = PLAZA + "/plazaFragment";
        public static final String IMAGE_ACTIVITY = PLAZA + "/imageActivity";
        public static final String FRAGMENT_IMAGE_DETAIL = PLAZA + "/fragmentImageDetail";
        public static final String KEY_IMAGE_DATA = "KEY_IMAGE_DATA";
    }

    public static class User {
        private static final String USER = "/user";
        public static final String FRAGMENT_USER = USER + "/userFragment";
        public static final String ACTIVITY_USER = USER + "/userActivity";
        public static final String ACTIVITY_AGREEMENT = USER + "/agreementActivity";
        public static final String ACTIVITY_SETTINGS_PAGE = USER + "/settingsPage";
        public static final String ACTIVITY_SETTINGS_ABOUT_ME = USER + "/settingsAboutMe";
        public static final String ACTIVITY_SETTINGS_ACCOUNT = USER + "/settingsAccount";
        public static final String ACTIVITY_SETTINGS_CAMERA = USER + "/settingsCamera";
        public static final String ACTIVITY_SETTINGS_EDIT_USER_INFO = USER + "/settingsEditUserInfo";
        public static final String ACTIVITY_SETTINGS_PERMISSION = USER + "/settingsPermission";
        public static final String ACTIVITY_SETTINGS_PLAY = USER + "/settingsPlay";
        public static final String ACTIVITY_SETTINGS_PUSH = USER + "/settingsPush";
        public static final String ACTIVITY_SETTINGS_RESET_PASSWORD = USER + "/settingsResetPwd";

    }

    public static class Find {
        public static final String KEY_CATEGORY_DATA = "KEY_CATEGORY_DATA";
        private static final String FIND = "/find";
        public static final String ACTIVITY_CATEGORY_DETAIL = FIND + "/activityCategoryDetail";
        public static final String FRAGMENT_FIND = FIND + "/findFragment";
        public static final String ACTIVITY_THEME_LIST = FIND + "/activityThemeList";
        public static final String ACTIVITY_TOPIC_INFO = FIND + "/activityTopicInfo";
    }

    public static class Video {
        public static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";
        private static final String VIDEO = "/video";
        public static final String VIDEO_DETAIL = VIDEO + "/videoDetail";
        public static final String VIDEO_COMMENT = VIDEO + "/videoComment";
        public static final String INTRODUCE = VIDEO + "/introduce";

        public static final String KEY_VIDEO_LIST_TYPE = "KEY_VIDEO_LIST_TYPE";
        // 如果该KEY的值为true，则将字体改变为白色
        public static final String KEY_VIDEO_LIST_STYLE = "KEY_VIDEO_LIST_STYLE";
        public static final int VIDEO_LIST_FRAGMENT_RECOMMEND = 0;
        public static final int VIDEO_LIST_FRAGMENT_DAILY = 1;
        public static final String FRAGMENT_VIDEO_LIST = VIDEO + "/VideoListFragment";
        public static final String FRAGMENT_CATEGORY_LIST = VIDEO + "/VideoCategoryList";
        public static final String KEY_CATEGORY_ID = "KEY_CATEGORY_ID";
        public static final String KEY_CATEGORY_TYPE = "KEY_CATEGORY_TYPE";
        public static final int CATEGORY_VIDEO_RECOMMEND = 0;
        public static final int CATEGORY_VIDEO_NEWPUBLISH = 1;
        public static final String ACTIVITY_SEARCH = VIDEO + "/activitySearch";

        public static final String ACTIVITY_PLAYRECORD = VIDEO + "/activityPlayRecord";
    }
}
