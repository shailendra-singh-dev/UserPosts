package user.posts.kotlin.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {

    private const val HOST = "https://engineering.user.posts.dev/challenge/api/"
    private const val TAG = "Service"

    val api: Api by lazy {
        val retrofit = Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        retrofit.create<Api>(Api::class.java)
    }
}
