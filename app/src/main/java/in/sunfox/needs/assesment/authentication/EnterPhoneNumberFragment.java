package in.sunfox.needs.assesment.authentication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.databinding.FragmentEnterPhoneNumberBinding;


public class EnterPhoneNumberFragment extends Fragment {


    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public EnterPhoneNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AuthenticationViewModel authenticationViewModel = new ViewModelProvider(requireActivity()).get(AuthenticationViewModel.class);
        FragmentEnterPhoneNumberBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_enter_phone_number, container, false);
        binding.setViewModel(authenticationViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
