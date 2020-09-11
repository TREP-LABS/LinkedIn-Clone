package com.app.treplabs.linkedinclone.network;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BackendProfileApi {
    @GET("basic/{userId}")
    Call<ResponseBody> getBasicProfile(@Path("userId") String userId);

    @GET("full/{userId}")
    Call<ResponseBody> getFullProfile(@Path("userId") String userId);

    @POST("educations")
    Call<ResponseBody> addNewEducation(String token, @Body HashMap<String, String> map);

    @PUT("educations/{educationId}")
    Call<ResponseBody> updateExistingEducation(String token, @Path("educationId") String educationId,
                                               @Body HashMap<String, String> map);

}
