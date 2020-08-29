package com.app.treplabs.linkedinclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.app.treplabs.linkedinclone.R;
import com.app.treplabs.linkedinclone.databinding.EducationListItemBinding;
import com.app.treplabs.linkedinclone.models.UserEducation;
import java.util.List;

public class UserEducationAdapter extends RecyclerView.Adapter<UserEducationAdapter.BindingHolder> {
    private List<UserEducation> mUserEducations;
    private Context mContext;

    public UserEducationAdapter(List<UserEducation> userEducations, Context context) {
        mUserEducations = userEducations;
        mContext = context;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EducationListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.education_list_item, parent, false);
        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        UserEducation userEducation = mUserEducations.get(position);
        if (position == mUserEducations.size() - 1)
            holder.mBinding.setIsLastItem(true);
        holder.mBinding.setUserEducation(userEducation);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mUserEducations.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        boolean isLastItem = false;
        EducationListItemBinding mBinding;

        public BindingHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.setIsLastItem(isLastItem);
        }
    }
}
