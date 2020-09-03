package com.app.treplabs.linkedinclone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.app.treplabs.linkedinclone.R;
import com.app.treplabs.linkedinclone.databinding.SkillsListItemBinding;
import com.app.treplabs.linkedinclone.models.UserSkill;
import java.util.List;

public class UserSkillAdapter extends RecyclerView.Adapter<UserSkillAdapter.BindingHolder> {
    private List<UserSkill> mUserSkills;
    private Context mContext;

    public UserSkillAdapter(List<UserSkill> userSkills, Context context) {
        mUserSkills = userSkills;
        mContext = context;
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SkillsListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext), R.layout.skills_list_item, parent, false);
        return new BindingHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        UserSkill userEducation = mUserSkills.get(position);
        if (position == mUserSkills.size() - 1)
            holder.mBinding.setIsLastItem(true);
        holder.mBinding.setUserSkill(userEducation);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mUserSkills.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        boolean isLastItem = false;
        SkillsListItemBinding mBinding;

        public BindingHolder(@NonNull View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
            mBinding.setIsLastItem(isLastItem);
        }
    }
}
