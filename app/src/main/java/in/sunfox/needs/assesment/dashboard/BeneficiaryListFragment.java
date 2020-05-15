package in.sunfox.needs.assesment.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.databinding.FragmentBeneficiaryListBinding;
import in.sunfox.needs.assesment.databinding.ItemBeneficiaryListBinding;
import in.sunfox.needs.assesment.helper.FirebaseAuthenticationHelper;
import in.sunfox.needs.assesment.helper.FirebaseFirestoreHelper;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeneficiaryListFragment extends Fragment {


    public BeneficiaryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView: Benef list frag");
        DashboardViewModel dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        dashboardViewModel.startObservingBeneficiaryList();
        FragmentBeneficiaryListBinding beneficiaryListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_beneficiary_list, container, false);
        beneficiaryListBinding.setViewModel(dashboardViewModel);
        return beneficiaryListBinding.getRoot();
    }


    public static class BeneficiaryListAdapter extends RecyclerView.Adapter<BeneficiaryListAdapter.ViewHolder> {

        private List<Beneficiary> beneficiaries;
        private DashboardViewModel viewModel;

        public BeneficiaryListAdapter(DashboardViewModel viewModel) {
            this.viewModel = viewModel;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemBeneficiaryListBinding beneficiaryListBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_beneficiary_list, parent, false);
            beneficiaryListBinding.setViewmodel(viewModel);
            return new ViewHolder(beneficiaryListBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.binding.setBeneficiary(beneficiaries.get(position));
        }

        @Override
        public int getItemCount() {
            if (beneficiaries == null) return 0;
            return beneficiaries.size();
        }

        public void setBeneficiaries(List<Beneficiary> beneficiaries) {
            this.beneficiaries = beneficiaries;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private ItemBeneficiaryListBinding binding;

            public ViewHolder(@NonNull ItemBeneficiaryListBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}
