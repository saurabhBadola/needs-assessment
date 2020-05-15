package in.sunfox.needs.assesment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.databinding.ActivityBeneficiaryDetailsBinding;
import in.sunfox.needs.assesment.databinding.ItemFamilyMembersListBinding;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;
import in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity;

public class BeneficiaryDetailsActivity extends BaseActivity {

    private Beneficiary beneficiary;
    private BeneficiaryDetailsViewModel beneficiaryDetailsViewModel;
    public static final String EXTRA_BENEFICIARY = "EXTRA_BENEFICIARY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBeneficiaryDetailsBinding beneficiaryDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_beneficiary_details);


        if (getIntent() != null) {
            beneficiary = (Beneficiary) getIntent().getSerializableExtra(EXTRA_BENEFICIARY);

//        beneficiary = new Beneficiary();
//        ArrayList<Beneficiary.FamilyMember> members = new ArrayList<>();
//        members.add(new Beneficiary.FamilyMember("Member1", "Self Employed"));
//        members.add(new Beneficiary.FamilyMember("Member2", "Govt. Sector"));
//        members.add(new Beneficiary.FamilyMember("Member1", "Self Employed"));
//        members.add(new Beneficiary.FamilyMember("Member2", "Govt. Sector"));
//        members.add(new Beneficiary.FamilyMember("Member1", "Self Employed"));
//        members.add(new Beneficiary.FamilyMember("Member2", "Govt. Sector"));
//        beneficiary.setFamilyMembers(members);

            beneficiaryDetailsViewModel.setBeneficiary(beneficiary);
        }
        beneficiaryDetailsBinding.setViewmodel(beneficiaryDetailsViewModel);
    }

    @Override
    public BaseObservableViewModel getAssignedViewModel() {
        beneficiaryDetailsViewModel = ViewModelProviders.of(this).get(BeneficiaryDetailsViewModel.class);
        return beneficiaryDetailsViewModel;
    }


    public static class FamilyMembersAdapter extends RecyclerView.Adapter<FamilyMembersAdapter.ViewHolder> {


        private List<Beneficiary.FamilyMember> familyMembers;

        @NonNull
        @Override
        public FamilyMembersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemFamilyMembersListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_family_members_list, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull FamilyMembersAdapter.ViewHolder holder, int position) {
            holder.binding.setIndex(position + 1);
            holder.binding.setFamilyMember(familyMembers.get(position));
        }

        @Override
        public int getItemCount() {
            if (familyMembers == null)
                return 0;
            return familyMembers.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private ItemFamilyMembersListBinding binding;

            public ViewHolder(@NonNull ItemFamilyMembersListBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }

        public void setFamilyMembers(List<Beneficiary.FamilyMember> familyMembers) {
            this.familyMembers = familyMembers;
            Log.d(VolunteerRegistrationActivity.TAG, "setFamilyMembers: "+familyMembers.size());
            notifyDataSetChanged();
        }

    }
}
