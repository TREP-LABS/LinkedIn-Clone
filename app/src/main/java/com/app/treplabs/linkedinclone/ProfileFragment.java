package com.app.treplabs.linkedinclone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.treplabs.linkedinclone.databinding.FragmentProfileBinding;
import com.app.treplabs.linkedinclone.models.UserExperience;
import com.app.treplabs.linkedinclone.repositories.UserProfileRepository;
import com.app.treplabs.linkedinclone.viewmodels.ProfileViewModel;

import java.util.List;

public class ProfileFragment extends Fragment {
    //data binding
    FragmentProfileBinding mBinding;
    //view model
    ProfileViewModel mProfileViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfileViewModel.getUserExperienceLD().observe(getViewLifecycleOwner(),
                experiences -> mBinding.setExperiences(experiences));
    }
}
