package in.sunfox.needs.assesment.dashboard.tasks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.dashboard.DashboardViewModel;
import in.sunfox.needs.assesment.databinding.ItemAttemptBeneficiaryTaskListBinding;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;

public class TaskDetailsAdapter extends RecyclerView.Adapter<TaskDetailsAdapter.ViewHolder> {

    private List<Task.BeneficiaryTask> beneficiaryTasks;
    private DashboardViewModel viewModel;

    public TaskDetailsAdapter(DashboardViewModel viewModel) {
        Log.d(TAG, "TaskDetailsAdapter: ");
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAttemptBeneficiaryTaskListBinding beneficiaryTaskListBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_attempt_beneficiary_task_list, parent, false);
        beneficiaryTaskListBinding.setViewModel(viewModel);
        return new ViewHolder(beneficiaryTaskListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setBeneficiaryTask(beneficiaryTasks.get(position));
    }

    @Override
    public int getItemCount() {
        if (beneficiaryTasks == null)
            return 0;
        return beneficiaryTasks.size();
    }

    public void setBeneficiaryTasks(List<Task.BeneficiaryTask> beneficiaryTasks) {
        this.beneficiaryTasks = beneficiaryTasks;
        if(beneficiaryTasks!=null)
        Log.d(TAG, "Set Beneficiary Tasks: "+beneficiaryTasks.size());

        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemAttemptBeneficiaryTaskListBinding binding;

        public ViewHolder(@NonNull ItemAttemptBeneficiaryTaskListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}