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
import retrofit2.http.Query;

public interface BackendProfileApi {
    @GET("basic/{userId}")
    Call<ResponseBody> getBasicProfile(@Path("userId") String userId);

    @GET("full/{userId}")
    Call<ResponseBody> getFullProfile(@Path("userId") String userId);

    //education requests
    @POST("educations")
    Call<ResponseBody> addNewEducation(@Header("Authorization") String token, @Body HashMap<String, String> map);

    @PUT("educations/{educationId}")
    Call<ResponseBody> updateExistingEducation(@Header("Authorization") String token, @Path("educationId") String educationId,
                                               @Body HashMap<String, String> map);

    @DELETE("educations/{educationId}")
    Call<ResponseBody> deleteEducation(@Header("Authorization") String token, @Path("educationId") String educationId);

    //experience requests
    //positions is used in place of experience in the backend API
    @POST("positions")
    Call<ResponseBody> addNewExperience(@Header("Authorization") String token, @Body HashMap<String, String> map);

    @PUT("positions/{positionId}")
    Call<ResponseBody> updateExistingExperience(@Header("Authorization") String token, @Path("positionId") String experienceId,
                                               @Body HashMap<String, String> map);

    @DELETE("positions/{positionId}")
    Call<ResponseBody> deleteExperience(@Header("Authorization") String token, @Path("positionId") String experienceId);

    //skills request
    @POST("skills")
    Call<ResponseBody> addNewSkill(@Header("Authorization") String token, @Body HashMap<String, String> map);

    @GET("skills")
    Call<ResponseBody> searchForSkill(@Header("Authorization") String token, @Query("q") String searchText);

    @DELETE("skills/{skillId}")
    Call<ResponseBody> deleteSkill(@Header("Authorization") String token, @Path("skillId") String skillId);

    //certifications
    @POST("certifications")
    Call<ResponseBody> addNewCertificate(@Header("Authorization") String token, @Body HashMap<String, String> map);

    @PUT("certifications/{certificationId}")
    Call<ResponseBody> updateExistingCertificate(@Header("Authorization") String token, @Path("certificationId") String certificationId,
                                                @Body HashMap<String, String> map);

    @DELETE("certifications/{certificationId}")
    Call<ResponseBody> deleteCertificate(@Header("Authorization") String token, @Path("certificationId") String certificationId);
}
