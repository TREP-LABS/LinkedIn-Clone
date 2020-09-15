package com.app.treplabs.linkedinclone.network;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
    Call<ResponseBody> addNewEducation(@Header("Authorization") String token, @Body HashMap<String, String> map);

    @PUT("educations/{educationId}")
    Call<ResponseBody> updateExistingEducation(@Header("Authorization") String token, @Path("educationId") String educationId,
                                               @Body HashMap<String, String> map);

    @DELETE("educations/{educationId}")
    Call<ResponseBody> deleteEducation(@Header("Authorization") String token, @Path("educationId") String educationId);

    //positions is used in place of experience in the backend API
    @POST("positions")
    Call<ResponseBody> addNewExperience(@Header("Authorization") String token, @Body HashMap<String, String> map);

    @PUT("positions/{positionId}")
    Call<ResponseBody> updateExistingExperience(@Header("Authorization") String token, @Path("positionId") String experienceId,
                                               @Body HashMap<String, String> map);

    @DELETE("positions/{positionId}")
    Call<ResponseBody> deleteExperience(@Header("Authorization") String token, @Path("positionId") String experienceId);
}
