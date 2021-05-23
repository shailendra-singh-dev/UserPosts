package user.posts.apiclients;

import android.util.Log;

import java.util.List;

import user.posts.java.interfaces.OnRequestItemsCallback;
import user.posts.java.model.User;
import user.posts.java.ui.UserPostsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;


public class UsersClient extends BaseClient {

    public static final String TAG = "MobileCodingChallenge:" + UsersClient.class.getSimpleName();

    public interface UsersService {
        @GET("users")
        Call<List<User>> getUsers(@Header("x-access-token") String accessToken);
    }

    private UsersClient() {
    }

    public static void fetchUsers(final OnRequestItemsCallback<User> onRequestItemsCallback) {
        UsersService service = create(UsersService.class);
        String apiKey = Session.getInstance().getAPIKey();// "A3768F04BAB2D0378CF4DC7BA29D1BB7";
        Call<List<User>> call = service.getUsers(apiKey);
        Log.i(TAG,"fetchUsers(),call"+ call.request().toString());

        call.enqueue(
                new Callback<List<User>>() {

                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        List<User> users = response.body();
                        Log.i(TAG,"fetchUsers()->onResponse(),users:"+ users.size());
                        onRequestItemsCallback.onSuccess(users);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        onRequestItemsCallback.onFailure(new Throwable("Failure in fetchUsers() ."));
                    }
                }
        );
    }

}
