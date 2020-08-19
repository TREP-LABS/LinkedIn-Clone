package com.app.treplabs.linkedinclone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LogInFragment extends Fragment {
    private Button mLogInButton;
    private Button mSignUpButton;

    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_log_in, container, false);
        mLogInButton = view.findViewById(R.id.login_button);
        mSignUpButton = view.findViewById(R.id.login_sign_up_btn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSignUpButton.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_logInFragment_to_signUpFragment)
        );
    }
}
