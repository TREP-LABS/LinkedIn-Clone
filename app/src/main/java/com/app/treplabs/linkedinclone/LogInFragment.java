package com.app.treplabs.linkedinclone;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.app.treplabs.linkedinclone.databinding.FragmentLogInBinding;
import com.app.treplabs.linkedinclone.interfaces.AuthStateListener;
import com.app.treplabs.linkedinclone.viewmodels.AuthViewModel;

public class LogInFragment extends Fragment implements AuthStateListener {
    //data binding
    FragmentLogInBinding mBinding;
    //view model
    AuthViewModel mAuthViewModel;

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        mAuthViewModel.setAuthStateListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentLogInBinding.inflate(inflater);
        mBinding.setAuthViewModel(mAuthViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.loginSignUpBtn.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_logInFragment_to_signUpFragment)
        );
    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStarted() {
        showToast("Login Started");
    }

    @Override
    public void onSuccess(LiveData<String> loginResponse) {
        loginResponse.observe(this, s -> {
            showToast(s);
            Log.d("LoginFragment", s);
        });
    }

    @Override
    public void onFailure(String message) {
        switch (message){
            case "Error in Email":
                mBinding.emailLayout.setError("Invalid Email");
                mBinding.passwordLayout.setError(null);
                break;
            case "Error in Password":
                mBinding.emailLayout.setError(null);
                mBinding.passwordLayout.setError("Invalid Password");
                break;
            case "No errors":
                mBinding.emailLayout.setError(null);
                mBinding.passwordLayout.setError(null);
                break;
        }
    }
}
