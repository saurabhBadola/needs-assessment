package in.sunfox.needs.assesment.beneficiary;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.databinding.FragmentAddBeneficiaryPageOneBinding;
import in.sunfox.needs.assesment.databinding.FragmentAddBeneficiaryPageTwoBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddBeneficiaryPageTwoFragment extends Fragment {

    public AddBeneficiaryPageTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        BeneficiaryViewModel viewModel = new ViewModelProvider(requireActivity()).get(BeneficiaryViewModel.class);
        FragmentAddBeneficiaryPageTwoBinding beneficiaryPageTwoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_beneficiary_page_two, container, false);
        beneficiaryPageTwoBinding.setViewModel(viewModel);
        return beneficiaryPageTwoBinding.getRoot();
    }
}
