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

import com.app.treplabs.linkedinclone.databinding.FragmentOnboardingBinding;

public class OnboardingFragment extends Fragment {
    //data binding
    FragmentOnboardingBinding mBinding;

    public OnboardingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentOnboardingBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.onboardingJoinNowBtn.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_onboardingFragment_to_signUpFragment)
        );
        mBinding.onboardingLoginBtn.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_onboardingFragment_to_logInFragment)
        );
    }
}
