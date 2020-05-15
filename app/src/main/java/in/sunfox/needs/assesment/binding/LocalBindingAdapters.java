package in.sunfox.needs.assesment.binding;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.sunfox.needs.assesment.BeneficiaryDetailsActivity;
import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.dashboard.BeneficiaryListFragment;
import in.sunfox.needs.assesment.dashboard.DashboardViewModel;
import in.sunfox.needs.assesment.dashboard.tasks.Task;
import in.sunfox.needs.assesment.dashboard.tasks.TaskDetailsAdapter;
import in.sunfox.needs.assesment.dashboard.tasks.TasksListAdapter;

import static in.sunfox.needs.assesment.authentication.AuthenticationViewModel.TAG;

public class LocalBindingAdapters {

    @BindingAdapter(value = {"items","viewmodel"})
    public static void bindRecyclerAdapter(RecyclerView recyclerView, List<Beneficiary> items,DashboardViewModel viewModel) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        BeneficiaryListFragment.BeneficiaryListAdapter adapter = new BeneficiaryListFragment.BeneficiaryListAdapter(viewModel);
        adapter.setBeneficiaries(items);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"tasks", "viewmodel"})
    public static void bindRecyclerViewTasks(RecyclerView recyclerView, List<Task> tasks, DashboardViewModel viewModel) {
        Log.d(TAG, "bindRecyclerViewTasks: ");
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        TasksListAdapter adapter = new TasksListAdapter(viewModel);
        adapter.setTasks(tasks);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"beneficiaryTasks", "viewmodel"})
    public static void bindBeneficiaryItems(RecyclerView recyclerView, List<Task.BeneficiaryTask> beneficiaryTasks, DashboardViewModel viewModel) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        TaskDetailsAdapter adapter = new TaskDetailsAdapter(viewModel);
        recyclerView.setAdapter(adapter);
        adapter.setBeneficiaryTasks(beneficiaryTasks);
    }

    @BindingAdapter("members")
    public static void bindMembers(RecyclerView recyclerView, List<Beneficiary.FamilyMember> familyMembers) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        //   ViewCompat.setNestedScrollingEnabled(recyclerView,false);
        BeneficiaryDetailsActivity.FamilyMembersAdapter adapter = new BeneficiaryDetailsActivity.FamilyMembersAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setFamilyMembers(familyMembers);
    }

    @BindingAdapter("backgroundR")
    public static void bindBg(TextView textView, Drawable drawable) {
        textView.setBackgroundDrawable(drawable);
    }
}
