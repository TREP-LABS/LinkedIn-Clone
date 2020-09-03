package com.app.treplabs.linkedinclone.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BackendProfileApi {
    @GET("basic/{userId}")
    Call<ResponseBody> getBasicProfile(@Path("userId") String userId);

    @GET("full/{userId}")
    Call<ResponseBody> getFullProfile(@Path("userId") String userId);

}
