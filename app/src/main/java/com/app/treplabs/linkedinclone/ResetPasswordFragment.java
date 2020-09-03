package com.app.treplabs.linkedinclone;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.app.treplabs.linkedinclone.databinding.FragmentResetPasswordBinding;
import com.app.treplabs.linkedinclone.databinding.ResetPasswordDialogBinding;
import com.app.treplabs.linkedinclone.interfaces.AuthStateListener;
import com.app.treplabs.linkedinclone.viewmodels.AuthViewModel;

public class ResetPasswordFragment extends Fragment implements AuthStateListener {
    boolean mIsBtnClicked;
    //data binding
    FragmentResetPasswordBinding mBinding;
    //view model
    AuthViewModel mAuthViewModel;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsBtnClicked = false;
        mAuthViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        mAuthViewModel.setAuthStateListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentResetPasswordBinding.inflate(inflater);
        mBinding.setAuthViewModel(mAuthViewModel);
        mBinding.setIsBtnClicked(mIsBtnClicked);
        return mBinding.getRoot();
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStarted() {
        mBinding.setIsBtnClicked(true);
    }

    @Override
    public void onSuccess(LiveData<String> loginResponse) {
        loginResponse.observe(this, s -> {
            if (s.contains("success")) {
                s = s.replace(" success", "");
                createDialogBox(s);
            } else {
                showToast(s);
            }
        });
        mBinding.setIsBtnClicked(false);
    }

    @Override
    public void onFailure(String message) {
        switch (message) {
            case "Error in Email":
                mBinding.emailLayout.setError("Invalid Email");
                break;
            case "No errors":
                mBinding.emailLayout.setError(null);
                break;
        }
    }

    private void createDialogBox(String message) {
        View dialogView = getLayoutInflater().inflate(R.layout.reset_password_dialog, null);
        ResetPasswordDialogBinding dialogBinding = ResetPasswordDialogBinding.bind(dialogView);

        AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        builder.setView(dialogView);
        builder.show();

        dialogBinding.dialogMessage.setText(message);
        dialogBinding.dialogButton.setOnClickListener(v -> builder.dismiss());
    }
}
