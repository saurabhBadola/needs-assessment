package in.sunfox.needs.assesment.volunteer_registration;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.InverseBindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.authentication.AuthenticationViewModel;
import in.sunfox.needs.assesment.databinding.FragmentEnterVolunteerDetailsTwoBinding;
import in.sunfox.needs.assesment.databinding.FragmentEnterVolunteerDetailsTwoBindingImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterVolunteerDetailsTwoFragment extends Fragment {

    public EnterVolunteerDetailsTwoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentEnterVolunteerDetailsTwoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_volunteer_details_two, container, false);
        RegistrationViewModel registrationViewModel = new ViewModelProvider(requireActivity()).get(RegistrationViewModel.class);
        binding.setViewModel(registrationViewModel);
        return binding.getRoot();

    }

}