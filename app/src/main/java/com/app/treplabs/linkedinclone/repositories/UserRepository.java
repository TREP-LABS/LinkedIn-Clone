package com.app.treplabs.linkedinclone.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treplabs.linkedinclone.models.User;
import com.app.treplabs.linkedinclone.network.BackendAuthApi;

import org.json.JSONException;
import org.json.JSONObject;

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
    private MutableLiveData<String> mResetResponse;
    private String mMessage;

    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private BackendAuthApi invokeAPI() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BackendAuthApi.class);
    }

    public LiveData<String> logUserIn(HashMap<String, String> map) {
        mLoginResponse = new MutableLiveData<>();
        invokeAPI().logUserIn(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                getLoginResponseFromJSON(response.body().string());
                                mLoginResponse.setValue(mMessage);
                                Log.d("UserRepo logIn", "onResponse: isSuccessful");
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                getLoginResponseFromJSON(response.errorBody().string());
                                mLoginResponse.setValue(mMessage);
                                Log.d("UserRepo logIn", "onResponse: unSuccessful");
                            } catch (IOException | JSONException e) {
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

    private void getLoginResponseFromJSON(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        boolean success = parent.getBoolean("success");
        mMessage = parent.getString("message");
        if (success) {
            JSONObject data = parent.getJSONObject("data");
            String token = data.getString("token");
            JSONObject user = data.getJSONObject("user");
            String id = user.getString("id");
            String firstname = user.getString("firstname");
            String lastname = user.getString("lastname");
            String email = user.getString("email");

            User.getInstance().setFirstName(firstname);
            User.getInstance().setLastName(lastname);
            User.getInstance().setEmail(email);
        }
    }

    public LiveData<String> signUserUp(HashMap<String, String> map) {
        mSignUpResponse = new MutableLiveData<>();
        invokeAPI().signUserUp(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                getSignUpResponseFromJSON(map, response.body().string());
                                mSignUpResponse.setValue(mMessage);
                                Log.d("UserRepo signUp", "onResponse: isSuccessful");
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                getSignUpResponseFromJSON(map, response.errorBody().string());
                                mSignUpResponse.setValue(mMessage);
                                Log.d("UserRepo signUp", "onResponse: unSuccessful");
                            } catch (IOException | JSONException e) {
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

    private void getSignUpResponseFromJSON(HashMap<String, String> map, String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        boolean success = parent.getBoolean("success");
        mMessage = parent.getString("message");
        if (success) {
            JSONObject data = parent.getJSONObject("data");
            String token = data.getString("token");

            User.getInstance().setFirstName(map.get("firstname"));
            User.getInstance().setLastName(map.get("lastname"));
            User.getInstance().setEmail(map.get("email"));
        }
    }

    public LiveData<String> resetUserPassword(HashMap<String, String> map) {
        mResetResponse = new MutableLiveData<>();
        invokeAPI().resetUserPassword(map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                getResetResponseFromJSON(response.body().string());
                                mResetResponse.setValue(mMessage + " success");
                                Log.d("UserRepo reset", "onResponse: isSuccessful");
                            } catch (IOException | JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                getResetResponseFromJSON(response.errorBody().string());
                                mResetResponse.setValue(mMessage);
                                Log.d("UserRepo reset", "onResponse: unSuccessful");
                            } catch (JSONException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        mResetResponse.setValue(t.getMessage());
                        Log.d("UserRepo", "onFailure:");
                    }
                });
        return mResetResponse;
    }

    private void getResetResponseFromJSON(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        boolean success = parent.getBoolean("success");
        mMessage = parent.getString("message");
    }
}
