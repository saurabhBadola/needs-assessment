package in.sunfox.needs.assesment.volunteer_registration;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.authentication.AuthenticationViewModel;
import in.sunfox.needs.assesment.beneficiary.BeneficiaryViewModel;
import in.sunfox.needs.assesment.databinding.FragmentEnterVolunteerDetailsOneBinding;
import in.sunfox.needs.assesment.helper.LocalCacheHelper;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterVolunteerDetailsOneFragment extends Fragment {

    public EnterVolunteerDetailsOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Spinner spinner = new Spinner(getContext());
        FragmentEnterVolunteerDetailsOneBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_volunteer_details_one, container, false);
        binding.setGendersAdapter(new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, LocalCacheHelper.getGendersList(requireContext())));
        binding.setPinCodesAdapter(new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, LocalCacheHelper.getPinCodes(requireContext())));
        RegistrationViewModel viewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @BindingAdapter(value = {"adapter", "selectedValue", "selectedValueAttrChanged"}, requireAll = false)
    public static void bindSpinnerData(AppCompatSpinner pAppCompatSpinner, ArrayAdapter<String> adapter, String newSelectedValue, final InverseBindingListener newTextAttrChanged) {

        //pAppCompatSpinner.setAdapter(new ArrayAdapter<String>(pAppCompatSpinner.getContext(), android.R.layout.simple_list_item_1, list));
        pAppCompatSpinner.setAdapter(adapter);

        pAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
                Log.d(TAG, "onItemSelected: " + newSelectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (newSelectedValue != null) {
            if (pAppCompatSpinner.getAdapter() != null) {
                int pos = ((ArrayAdapter<String>) pAppCompatSpinner.getAdapter()).getPosition(newSelectedValue);
                pAppCompatSpinner.setSelection(pos, true);
            } else {
                Log.d(TAG, "Adapter NULL: ");
            }
        }

        //    SpinnerAdapter adapter = pAppCompatSpinner.getAdapter();

    }


    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static String captureSelectedValue(AppCompatSpinner pAppCompatSpinner) {
        return (String) pAppCompatSpinner.getSelectedItem();
    }

//    @BindingAdapter()
//    public static void binAv(AppCompatSpinner appCompatSpinner, ) {
//
//    }
}
