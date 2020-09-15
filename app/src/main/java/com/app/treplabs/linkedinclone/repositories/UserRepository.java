package com.app.treplabs.linkedinclone.repositories;

import android.util.Log;
import com.app.treplabs.linkedinclone.models.User;
import com.app.treplabs.linkedinclone.network.BackendAuthApi;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private static UserRepository instance = null;
    private static final String BASE_URL = "http://trep-lc-backend.herokuapp.com/api/v1/auth/";
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

    public String logUserIn(HashMap<String, String> map) {
        Call<ResponseBody> call = invokeAPI().logUserIn(map);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()){
                Log.d("UserRepo", "logUserIn: isSuccessful");
                getLoginResponseFromJSON(result.body().string());
            }else {
                Log.d("UserRepo", "logUserIn: unSuccessful");
                getLoginResponseFromJSON(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mMessage;
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
            String slug = user.getString("slug");

            User.INSTANCE.setFirstName(firstname);
            User.INSTANCE.setLastName(lastname);
            User.INSTANCE.setEmail(email);
            User.INSTANCE.setUserId(id);
            User.INSTANCE.setSlug(slug);
            User.INSTANCE.setToken(token);
        }
    }

    public String signUserUp(HashMap<String, String> map) {
        Call<ResponseBody> call = invokeAPI().signUserUp(map);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()){
                Log.d("UserRepo", "logUserIn: isSuccessful");
                getSignUpResponseFromJSON(map, result.body().string());
            }else {
                Log.d("UserRepo", "logUserIn: unSuccessful");
                getSignUpResponseFromJSON(map, result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mMessage;
    }

    private void getSignUpResponseFromJSON(HashMap<String, String> map, String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        boolean success = parent.getBoolean("success");
        mMessage = parent.getString("message");
        if (success) {
            JSONObject data = parent.getJSONObject("data");
            String token = data.getString("token");

            User.INSTANCE.setFirstName(map.get("firstname"));
            User.INSTANCE.setLastName(map.get("lastname"));
            User.INSTANCE.setEmail(map.get("email"));
            User.INSTANCE.setToken(token);
        }
    }

    public String resetUserPassword(HashMap<String, String> map) {
        Call<ResponseBody> call = invokeAPI().resetUserPassword(map);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()){
                Log.d("UserRepo", "logUserIn: isSuccessful");
                getResetResponseFromJSON(result.body().string());
            }else {
                Log.d("UserRepo", "logUserIn: unSuccessful");
                getResetResponseFromJSON(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mMessage;
    }

    private void getResetResponseFromJSON(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        boolean success = parent.getBoolean("success");
        mMessage = parent.getString("message");
        if (success){
            mMessage = mMessage + " success";
        }
    }
}
