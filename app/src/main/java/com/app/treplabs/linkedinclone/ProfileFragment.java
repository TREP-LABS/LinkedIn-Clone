package com.app.treplabs.linkedinclone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.app.treplabs.linkedinclone.databinding.FragmentProfileBinding;
import com.app.treplabs.linkedinclone.interfaces.ProfileStateListener;
import com.app.treplabs.linkedinclone.models.User;
import com.app.treplabs.linkedinclone.viewmodels.ProfileViewModel;

public class ProfileFragment extends Fragment implements ProfileStateListener {
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
        mProfileViewModel.setProfileStateListener(this);
        mProfileViewModel.getFullProfileFromRepo("5f4a2f6db262d23d4808948b");
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
        mProfileViewModel.getUserEducationLD().observe(getViewLifecycleOwner(),
                educations -> mBinding.setEducations(educations));
        mProfileViewModel.getUserSkillLD().observe(getViewLifecycleOwner(),
                skills -> mBinding.setSkills(skills));
    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetProfileFailure(String message) {
        showToast(message);
    }

    @Override
    public void onGetProfileSuccess(String s) {
        mBinding.setUser(User.INSTANCE);
        showToast(s);
    }
}
