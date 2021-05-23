package user.posts.application;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class UserPostsApplication extends Application {
    private static final String LOG = Application.class.getSimpleName();
    private static final long DISK_CACHE_SIZE = 100 * 1024 * 1024;
    private static UserPostsApplication mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
//        Session session = Session.getInstance();
//        session.recoverSession();
        initPicasso();
    }

    private void initPicasso() {
        Picasso.Builder builder = new Picasso.Builder(this);
        OkHttpDownloader okHttpDownloader = new OkHttpDownloader(this, DISK_CACHE_SIZE);
        builder.downloader(okHttpDownloader);
        Picasso picasso = builder.build();
        Picasso.setSingletonInstance(picasso);
    }

    private static void setInstance(final UserPostsApplication app) {
        mApp = app;
    }

    public static UserPostsApplication getInstance() {
        return mApp;
    }
}
