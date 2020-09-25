package com.app.treplabs.linkedinclone.bindingadapters;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.treplabs.linkedinclone.adapters.UserCertificateAdapter;
import com.app.treplabs.linkedinclone.adapters.UserEducationAdapter;
import com.app.treplabs.linkedinclone.adapters.UserExperienceAdapter;
import com.app.treplabs.linkedinclone.adapters.UserSkillAdapter;
import com.app.treplabs.linkedinclone.models.UserCertificate;
import com.app.treplabs.linkedinclone.models.UserEducation;
import com.app.treplabs.linkedinclone.models.UserExperience;
import com.app.treplabs.linkedinclone.models.UserSkill;
import java.util.List;

public class ProfileBindingAdapters {

    @BindingAdapter("userExperienceList")
    public static void setUserExperienceList(RecyclerView view, List<UserExperience> experiences){
        if (experiences == null)
            return;
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null)
            view.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        UserExperienceAdapter adapter = (UserExperienceAdapter) view.getAdapter();
        if (adapter == null){
            adapter = new UserExperienceAdapter(experiences, view.getContext());
            view.setAdapter(adapter);
        }
    }

    @BindingAdapter("userEducationList")
    public static void setUserEducationList(RecyclerView view, List<UserEducation> educations){
        if (educations == null)
            return;
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null)
            view.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        UserEducationAdapter adapter = (UserEducationAdapter) view.getAdapter();
        if (adapter == null){
            adapter = new UserEducationAdapter(educations, view.getContext());
            view.setAdapter(adapter);
        }
    }

    @BindingAdapter("userSkillList")
    public static void setUserSkillList(RecyclerView view, List<UserSkill> skills){
        if (skills == null)
            return;
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null)
            view.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        UserSkillAdapter adapter = (UserSkillAdapter) view.getAdapter();
        if (adapter == null){
            adapter = new UserSkillAdapter(skills, view.getContext());
            view.setAdapter(adapter);
        }
    }

    @BindingAdapter("userCertificateList")
    public static void setUserCertificateList(RecyclerView view, List<UserCertificate> certificates){
        if (certificates == null)
            return;
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager == null)
            view.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        UserCertificateAdapter adapter = (UserCertificateAdapter) view.getAdapter();
        if (adapter == null){
            adapter = new UserCertificateAdapter(certificates, view.getContext());
            view.setAdapter(adapter);
        }
    }
}
