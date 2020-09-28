package com.app.treplabs.linkedinclone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.treplabs.linkedinclone.databinding.EditIntroBottomSheetBinding;
import com.app.treplabs.linkedinclone.databinding.FragmentProfileBinding;
import com.app.treplabs.linkedinclone.interfaces.ProfileStateListener;
import com.app.treplabs.linkedinclone.models.User;
import com.app.treplabs.linkedinclone.viewmodels.ProfileViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ProfileFragment extends Fragment implements ProfileStateListener {
    BottomSheetBehavior mBottomSheetBehavior;
    boolean mIsThereProfile;
    //data binding
    FragmentProfileBinding mBinding;
    EditIntroBottomSheetBinding mIntroBottomSheetBinding;
    //view model
    ProfileViewModel mProfileViewModel;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsThereProfile = false;
        mProfileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        mProfileViewModel.setProfileStateListener(this);
        mProfileViewModel.getFullProfileFromRepo(User.INSTANCE.getUserId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater);
        mIntroBottomSheetBinding = mBinding.includedEditIntro;
        mBottomSheetBehavior = BottomSheetBehavior.from(mIntroBottomSheetBinding.editIntroBottomSheet);
        mBinding.setIsThereProfile(mIsThereProfile);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.editHeadline.setOnClickListener(v -> {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED)
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        mIntroBottomSheetBinding.closeEditIntro.setOnClickListener(v -> {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetProfileFailure(String message) {
        mBinding.setIsThereProfile(false);
        showToast(message);
    }

    @Override
    public void onGetProfileSuccess(String s) {
        mBinding.setUserProfile(mProfileViewModel.getUserProfile());
        mBinding.setExperiences(mProfileViewModel.getUserExperiences());
        mBinding.setEducations(mProfileViewModel.getUserEducations());
        mBinding.setSkills(mProfileViewModel.getUserSkills());
        mBinding.setCertificates(mProfileViewModel.getUserCertificates());
        mBinding.setIsThereProfile(true);
        showToast(s);
    }
}
