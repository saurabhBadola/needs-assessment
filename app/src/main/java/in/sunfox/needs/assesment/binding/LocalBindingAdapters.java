package in.sunfox.needs.assesment.binding;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.dashboard.BeneficiaryListFragment;
import in.sunfox.needs.assesment.dashboard.DashboardViewModel;
import in.sunfox.needs.assesment.dashboard.tasks.Task;
import in.sunfox.needs.assesment.dashboard.tasks.TaskDetailsAdapter;
import in.sunfox.needs.assesment.dashboard.tasks.TasksListAdapter;

import static in.sunfox.needs.assesment.authentication.AuthenticationViewModel.TAG;

public class LocalBindingAdapters {

    @BindingAdapter("items")
    public static void bindRecyclerAdapter(RecyclerView recyclerView, List<Beneficiary> items) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        BeneficiaryListFragment.BeneficiaryListAdapter adapter = new BeneficiaryListFragment.BeneficiaryListAdapter();
        adapter.setBeneficiaries(items);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value={"tasks","viewmodel"})
    public static void bindRecyclerViewTasks(RecyclerView recyclerView, List<Task> tasks, DashboardViewModel viewModel) {
        Log.d(TAG, "bindRecyclerViewTasks: ");
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        TasksListAdapter adapter = new TasksListAdapter(viewModel);
        adapter.setTasks(tasks);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"beneficiaryTasks","viewmodel"})
    public static void bindBeneficiaryItems(RecyclerView recyclerView, List<Task.BeneficiaryTask> beneficiaryTasks, DashboardViewModel viewModel) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        TaskDetailsAdapter adapter = new TaskDetailsAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        adapter.setBeneficiaryTasks(beneficiaryTasks);
    }
}
