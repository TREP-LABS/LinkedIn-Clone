package com.app.treplabs.linkedinclone;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.app.treplabs.linkedinclone.databinding.FragmentSignUpBinding;
import com.app.treplabs.linkedinclone.interfaces.AuthStateListener;
import com.app.treplabs.linkedinclone.viewmodels.AuthViewModel;

public class SignUpFragment extends Fragment implements AuthStateListener {
    //data binding
    FragmentSignUpBinding mBinding;
    //view model
    AuthViewModel mAuthViewModel;

    public SignUpFragment() {
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
        mBinding = FragmentSignUpBinding.inflate(inflater);
        mBinding.setAuthViewModel(mAuthViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.registerLogInButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_signUpFragment_to_logInFragment)
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
    public void onSuccess(LiveData<String> signUpResponse) {
        signUpResponse.observe(this, this::showToast);
    }

    @Override
    public void onFailure(String message) {
        switch (message){
            case "Error in First name":
                mBinding.firstNameLayout.setError("*Required (2 or more characters)");
                mBinding.lastNameLayout.setError(null);
                mBinding.emailLayout.setError(null);
                mBinding.passwordLayout.setError(null);
                break;
            case "Error in Last name":
                mBinding.firstNameLayout.setError(null);
                mBinding.lastNameLayout.setError("*Required (2 or more characters)");
                mBinding.emailLayout.setError(null);
                mBinding.passwordLayout.setError(null);
                break;
            case "Error in Email":
                mBinding.firstNameLayout.setError(null);
                mBinding.lastNameLayout.setError(null);
                mBinding.emailLayout.setError("Email of the form yourname@domain.xyz is needed");
                mBinding.passwordLayout.setError(null);
                break;
            case "Error in Password":
                mBinding.firstNameLayout.setError(null);
                mBinding.lastNameLayout.setError(null);
                mBinding.emailLayout.setError(null);
                mBinding.passwordLayout.setError("6 or more characters");
                break;
            case "No errors":
                mBinding.firstNameLayout.setError(null);
                mBinding.lastNameLayout.setError(null);
                mBinding.emailLayout.setError(null);
                mBinding.passwordLayout.setError(null);
                break;
        }
    }
}
