package user.posts.apiclients;

import android.content.Context;
import android.content.SharedPreferences;

import user.posts.java.BuildConfig;

public class Session {
    private static final String TAG = "MobileCodingChallenge" + Session.class.getSimpleName();
    public static final String ApiBaseURL = "https://engineering.user.posts.dev/challenge/api/";
    private static String prefsStoreName = "Preferences";
    private static Session mInstance = null;
    public boolean isDevelopment;
    private String mAPIKey;

    private Session() {
        //No one should instantiate Session
    }

    public synchronized static Session getInstance() {
        if (mInstance == null) {
            mInstance = new Session();
            mInstance.isDevelopment = BuildConfig.DEBUG;
        }
        return mInstance;
    }

    @Override
    public String toString() {
        return "[APIBaseURL:" + ApiBaseURL + "]";
    }

    // Session Management

    public void saveSession(Context appContext) {
        //context is null when going to the background.  Need to better udnerstand application life cycle
        SharedPreferences prefs = appContext.getSharedPreferences(prefsStoreName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("api_base_url", ApiBaseURL);
        editor.putString("api_key", mAPIKey);
        editor.commit();
    }

    public void recoverSession(Context appContext) {
        SharedPreferences prefs = appContext.getSharedPreferences(prefsStoreName, Context.MODE_PRIVATE);
        mAPIKey = prefs.getString("api_key", null);
    }

    public String getAPIKey() {
        return mAPIKey;
    }

    public void setAPIKey(String mAPIKey) {
        this.mAPIKey = mAPIKey;
    }

}
