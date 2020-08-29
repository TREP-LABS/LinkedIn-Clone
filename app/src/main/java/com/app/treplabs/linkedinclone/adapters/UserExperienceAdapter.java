package com.app.treplabs.linkedinclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.app.treplabs.linkedinclone.R;
import com.app.treplabs.linkedinclone.databinding.ExperienceListItemBinding;
import com.app.treplabs.linkedinclone.models.UserExperience;
import java.util.List;

public class UserExperienceAdapter extends RecyclerView.Adapter<UserExperienceAdapter.BindingHolder> {
    private List<UserExperience> mUserExperiences;
    private Context mContext;

    public UserExperienceAdapter(List<UserExperience> userExperiences, Context context) {
        mUserExperiences = userExperiences;
        mContext = context;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ExperienceListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.experience_list_item, parent, false);
        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        UserExperience userExperience = mUserExperiences.get(position);
        holder.mBinding.setUserExperience(userExperience);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mUserExperiences.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        ExperienceListItemBinding mBinding;

        public BindingHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }
    }
}
