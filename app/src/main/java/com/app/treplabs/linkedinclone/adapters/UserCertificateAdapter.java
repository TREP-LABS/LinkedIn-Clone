package com.app.treplabs.linkedinclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.app.treplabs.linkedinclone.R;
import com.app.treplabs.linkedinclone.databinding.CertificateListItemBinding;
import com.app.treplabs.linkedinclone.models.UserCertificate;
import java.util.List;

public class UserCertificateAdapter extends RecyclerView.Adapter<UserCertificateAdapter.BindingHolder> {
    private List<UserCertificate> mUserCertificates;
    private Context mContext;

    public UserCertificateAdapter(List<UserCertificate> userCertificates, Context context) {
        mUserCertificates = userCertificates;
        mContext = context;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CertificateListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.certificate_list_item, parent, false);
        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        UserCertificate userCertificate = mUserCertificates.get(position);
        if (position == mUserCertificates.size() - 1)
            holder.mBinding.setIsLastItem(true);
        holder.mBinding.setUserCertificate(userCertificate);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mUserCertificates.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        boolean isLastItem = false;
        CertificateListItemBinding mBinding;

        public BindingHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.setIsLastItem(isLastItem);
        }
    }
}
