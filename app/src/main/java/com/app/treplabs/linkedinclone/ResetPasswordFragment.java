package com.app.treplabs.linkedinclone;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.treplabs.linkedinclone.databinding.FragmentResetPasswordBinding;

public class ResetPasswordFragment extends Fragment {
    //data binding
    FragmentResetPasswordBinding mBinding;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = FragmentResetPasswordBinding.inflate(inflater);
        return mBinding.getRoot();
    }
}
