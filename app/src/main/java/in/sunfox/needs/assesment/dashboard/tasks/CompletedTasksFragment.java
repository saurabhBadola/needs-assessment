package in.sunfox.needs.assesment.dashboard.tasks;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.dashboard.DashboardViewModel;
import in.sunfox.needs.assesment.databinding.FragmentCompletedTasksBinding;
import in.sunfox.needs.assesment.databinding.FragmentCompletedTasksBindingImpl;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedTasksFragment extends Fragment {

    public CompletedTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: completed");

        DashboardViewModel viewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        viewModel.startObservingCompletedTasks();
        FragmentCompletedTasksBinding binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()),R.layout.fragment_completed_tasks, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
