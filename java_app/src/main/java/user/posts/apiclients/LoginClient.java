package user.posts.apiclients;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;

import user.posts.java.interfaces.OnRequestCallback;
import user.posts.java.model.Account;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

public final class LoginClient extends BaseClient {

    private static final String TAG = "MobileCodingChallenge" + LoginClient.class.getSimpleName();

    public interface LoginService {
        @GET("login")
        Call<Account> login(@Header("Authorization") String credentials);
    }

    public static void login(String username, String password, final OnRequestCallback<Account> callback) {
        final String credentials = username + ":" + password;
        final String auth = "Basic " + Base64.encodeToString(credentials.getBytes(),
                Base64.NO_WRAP);

        LoginService loginService = create(LoginService.class);
        new Thread(() -> {
            try {
                final Response<Account> response = loginService.login(auth).execute();
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body());
                    } else {
                        callback.onFailure(new Throwable("Failed call to login"));
                    }
                });
            } catch (IOException e) {
                Log.e(TAG, "Login failed", e);
                callback.onFailure(new Throwable(e.getMessage()));
            }
        }).start();
    }

}
