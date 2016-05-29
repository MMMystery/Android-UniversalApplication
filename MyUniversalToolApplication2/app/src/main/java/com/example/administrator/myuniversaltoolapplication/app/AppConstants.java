package com.example.administrator.myuniversaltoolapplication.app;

/**
 * Created by sunger on 2015/10/27.
 */
public class AppConstants {

    public final class RequestPath {
        public final static String BASE_URL = "https://newapi.meipai.com";
        public final static String CATEGOTY = "/channels/header_list.json?language=zh-Hans";
        public final static String VIDEO_LIST = "/channels/feed_timeline.json";
        public final static String HOT_VIDEO_LIST = "/hot/feed_timeline.json";
        public final static String MEDIAS = "/medias/show.json";

        public final static String LIKES_VIDEO_CREATE = "/likes/create.json";
        public final static String LIKES_VIDEO_DESTORY = "/likes/destroy.json";

        public final static String COMMENTS = "/comments/show.json";
        public final static String CREATE_COMMENTS_LIKE = "/comments/create_like.json";
        public final static String DESTORY_COMMENT_LIKE = "/comments/destroy_like.json";

        public final static String OAUTH = "/oauth/access_token.json";

        public final static String SEND_VERIFY_CODE = "/common/send_verify_code_to_phone.json";

        public final static String RESET_PASSWORD = "/users/reset_password.json";

        public final static String USERS_UPDATE = "/users/update.json";

        public final static String FRIENDSHIPS = "/friendships/friends.json";

        public final static String FOLLOWERS = "/friendships/followers.json";

        public final static String USER_MEDIAS = "/medias/user_timeline.json";

        public final static String USER_REPOSTS = "/reposts/user_timeline.json";
    }

    public final class Key {
        /*下面的是Bmob后台user表里的key*/
        public final static String BOMBUSERTABLE_OBJECTID = "objectId";
        public final static String BOMBUSERTABLE_USERNAME = "username";
        public final static String BOMBUSERTABLE_AVATER = "avater";

        /*下面的是SP里的key*/
        public final static String CURRENT_OBJECTID = "current_objectId";
        public final static String CURRENT_USERNAME = "current_username";
        public final static String CURRENT_AVATER = "current_avater";

        public final static String USERNAME = "username";

    }

    //好友请求：未读-未添加-接收到的初始状态
    public static final int STATUS_VERIFY_NONE=0;

}
