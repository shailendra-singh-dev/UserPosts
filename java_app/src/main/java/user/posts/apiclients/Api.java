package user.posts.apiclients;


import user.posts.java.model.Account;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Api {

    @GET("login")
    Call<Account> login(@Header("Authorization") String credentials);

}
