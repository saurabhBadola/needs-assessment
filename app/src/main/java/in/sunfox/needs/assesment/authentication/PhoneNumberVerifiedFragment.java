package in.sunfox.needs.assesment.authentication;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.databinding.FragmentEnterPhoneNumberBinding;
import in.sunfox.needs.assesment.databinding.FragmentPhoneNumberVerifiedBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneNumberVerifiedFragment extends Fragment {

    public PhoneNumberVerifiedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
        FragmentPhoneNumberVerifiedBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_phone_number_verified, container, false);
        binding.setViewModel(authenticationViewModel);
        return binding.getRoot();
    }
}
