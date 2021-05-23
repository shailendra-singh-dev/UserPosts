package user.posts.apiclients;

import android.util.Log;

import java.util.List;

import user.posts.java.interfaces.OnRequestItemsCallback;
import user.posts.java.model.UserPost;
import user.posts.java.model.User;
import user.posts.java.ui.UserPostsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class UserPostsClient extends BaseClient {

    public static final String TAG = "MobileCodingChallenge:" + UserPostsClient.class.getSimpleName();

    public interface UserPostsService {
        @GET("posts")
        Call<List<UserPost>> getUserPosts(@Header("x-access-token") String accessToken, @Query("userId") int userId);
    }

    public static void fetchUserPosts(final OnRequestItemsCallback<UserPost> onRequestItemsCallback, int userID) {
        UserPostsService service = create(UserPostsService.class);
        String apiKey = Session.getInstance().getAPIKey();//"A3768F04BAB2D0378CF4DC7BA29D1BB7";
        Call<List<UserPost>> call = service.getUserPosts(apiKey,userID);
        String request = call.request().toString();
        Log.i(TAG,"fetchUserPosts(),userID:"+ userID +",apiKey:"+apiKey +",request:"+ request);

        call.enqueue(
                new Callback<List<UserPost>>() {

                    @Override
                    public void onResponse(Call<List<UserPost>> call, Response<List<UserPost>> response) {
                        List<UserPost> userPosts = response.body();
                        Log.i(TAG,"fetchUserPosts()->onResponse(),userPosts:"+ userPosts.size());
                        onRequestItemsCallback.onSuccess(userPosts);
                    }

                    @Override
                    public void onFailure(Call<List<UserPost>> call, Throwable t) {
                        onRequestItemsCallback.onFailure(new Throwable("Failure in fetchUserPosts() ."));
                    }
                }
        );
    }

}
