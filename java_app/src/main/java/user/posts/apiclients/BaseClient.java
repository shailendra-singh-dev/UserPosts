package user.posts.apiclients;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;

import user.posts.java.interfaces.OnRequestCallback;
import user.posts.java.model.Account;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

public class BaseClient {

    private static final String TAG = "MobileCodingChallenge" + BaseClient.class.getSimpleName();

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Session.ApiBaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    protected static <T> T create(Class<T> service) {
        Retrofit retrofit = getRetrofit();
        return retrofit.create(service);
    }

}
