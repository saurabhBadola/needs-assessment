package in.sunfox.needs.assesment.authentication;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.databinding.FragmentEnterOtpBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterOtpFragment extends Fragment {

    public EnterOtpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentEnterOtpBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_otp, container, false);
        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
        binding.setViewmodel(authenticationViewModel);
        return binding.getRoot();
    }

//    @BindingAdapter("onClick")
//    public static void runMe(View view, View.OnClickListener function) {
//        view.setOnClickListener(function);
//    }
}
