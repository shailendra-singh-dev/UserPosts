package user.posts.interfaces;

import user.posts.java.R;
import user.posts.java.application.UserPostsApplication;

public enum SCREEN {

    USER_POSTS(R.string.screen_posts);

    private int mScreenId;

    SCREEN(int id) {
        mScreenId = id;
    }

    public String getScreenName() {
        return UserPostsApplication.getInstance().getString(mScreenId);
    }
}
