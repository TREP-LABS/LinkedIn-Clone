package com.app.treplabs.linkedinclone.repositories;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.app.treplabs.linkedinclone.network.BackendAuthApi;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private static UserRepository instance = null;
    private static final String BASE_URL = "http://trep-lc-backend.herokuapp.com/api/v1/auth/";
    private MutableLiveData<String> mLoginResponse;
    private MutableLiveData<String> mSignUpResponse;

    private UserRepository(){}

    public static UserRepository getInstance(){
        if (instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    private BackendAuthApi invokeAPI(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BackendAuthApi.class);
    }

    public LiveData<String> logUserIn(HashMap<String, String> map){
        mLoginResponse = new MutableLiveData<>();
        invokeAPI().logUserIn(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                mLoginResponse.setValue(response.body().string());
                                Log.d("UserRepo logIn", "onResponse: isSuccessful");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            try {
                                mLoginResponse.setValue(response.errorBody().string());
                                Log.d("UserRepo logIn", "onResponse: unSuccessful");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mLoginResponse.setValue(t.getMessage());
                        Log.d("UserRepo", "onFailure:");
                    }
                });
        return mLoginResponse;
    }

    public LiveData<String> signUserIn(HashMap<String, String> map){
        mSignUpResponse = new MutableLiveData<>();
        invokeAPI().signUserIn(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            try {
                                mSignUpResponse.setValue(response.body().string());
                                Log.d("UserRepo signUp", "onResponse: isSuccessful");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else {
                            try {
                                mSignUpResponse.setValue(response.errorBody().string());
                                Log.d("UserRepo signUp", "onResponse: unSuccessful");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mSignUpResponse.setValue(t.getMessage());
                        Log.d("UserRepo signUp", "onFailure:");
                    }
                });
        return mSignUpResponse;
    }
}
