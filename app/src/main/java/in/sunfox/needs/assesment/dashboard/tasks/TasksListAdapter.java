package in.sunfox.needs.assesment.dashboard.tasks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.dashboard.DashboardViewModel;
import in.sunfox.needs.assesment.databinding.ItemTaskListBinding;

import static in.sunfox.needs.assesment.authentication.AuthenticationViewModel.TAG;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.ViewHolder> {


    private List<Task> tasks;
    private DashboardViewModel viewModel;

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
        if (tasks != null)
            Log.d(TAG, "setTasks: " + tasks.size());
    }

    public TasksListAdapter(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
        Log.d(TAG, "TasksListAdapter: ---");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_task_list, parent, false);
        binding.setViewModel(viewModel);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setTask(tasks.get(position));
    }

    @Override
    public int getItemCount() {
        if (tasks == null) return 0;
        return tasks.size();
    }

    public void setViewModel(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ItemTaskListBinding binding;

        public ViewHolder(@NonNull ItemTaskListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
