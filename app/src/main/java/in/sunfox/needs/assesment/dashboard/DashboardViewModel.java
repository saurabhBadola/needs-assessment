package in.sunfox.needs.assesment.dashboard;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

import java.util.List;

import in.sunfox.needs.assesment.BR;
import in.sunfox.needs.assesment.DashboardActivity;
import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.dashboard.tasks.Task;
import in.sunfox.needs.assesment.helper.FirebaseAuthenticationHelper;
import in.sunfox.needs.assesment.helper.FirebaseFirestoreHelper;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;
import in.sunfox.needs.assesment.viewmodel_com.ViewModelEvent;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;

public class DashboardViewModel extends BaseObservableViewModel {


    @Bindable
    private List<Beneficiary> beneficiaries;
    private Application application;
    private FirebaseUser user;
    private Task currentSelectedTask;
    private boolean completedTasksFetched = false;
    private boolean pendingTasksFetched = false;
    private boolean beneficiariesFetched = false;

    @Bindable
    private List<Task> pendingTasks;

    @Bindable
    private List<Task> completedTasks;

    public void startObservingBeneficiaryList() {
        FirebaseFirestoreHelper.observeBeneficiaryList(application, new FirebaseFirestoreHelper.ListQueryListener<Beneficiary>() {
            @Override
            public void onSuccess(List<Beneficiary> dataList) {
                beneficiariesFetched = true;
                notifyPropertyChanged(BR.beneficiariesFetched);
                beneficiaries = dataList;
                notifyPropertyChanged(BR.beneficiaries);
            }

            @Override
            public void onFailed(String message) {
            }
        });
    }

    public void startObservingPendingTasks() {
        FirebaseFirestoreHelper.observePendingTasksList(application, new FirebaseFirestoreHelper.ListQueryListener<Task>() {
            @Override
            public void onSuccess(List<Task> dataList) {
                pendingTasksFetched = true;
                notifyPropertyChanged(BR.pendingTasksFetched);
                Log.d(TAG, "ObservePendingTasks: " + dataList.size());
                pendingTasks = dataList;
                notifyPropertyChanged(BR.pendingTasks);
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    public void startObservingCompletedTasks() {
        FirebaseFirestoreHelper.observeCompletedList(application, new FirebaseFirestoreHelper.ListQueryListener<Task>() {
            @Override
            public void onSuccess(List<Task> dataList) {
                completedTasksFetched = true;
                notifyPropertyChanged(BR.completedTasksFetched);
                Log.d(TAG, "ObserveCompletedTasks: " + dataList.size());
                completedTasks = dataList;
                notifyPropertyChanged(BR.completedTasks);
            }

            @Override
            public void onFailed(String message) {

            }
        });

    }

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        user = FirebaseAuthenticationHelper.getLoggedInUser();
        if (user == null) {
            dispatchLogoutUser();
        }
    }

    public void dispatchLogoutUser() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                FirebaseAuthenticationHelper.logout();
                ((DashboardActivity) baseActivity).logoutUser();
            }
        });
    }

    public void dispatchShowBeneficiaryDetails(Beneficiary beneficiary) {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((DashboardActivity) baseActivity).openBeneficiaryDetails(beneficiary);
            }
        });
    }

    public List<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }


    public void dispatchAddBeneficiary() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((DashboardActivity) baseActivity).openAddBeneficiaryActivity();
            }
        });
    }

    public void dispatchDoTask(String phoneNumber) {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((DashboardActivity) baseActivity).doTask(phoneNumber);
            }
        });
    }


    public void dispatchOpenTaskDetails(Task task) {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                currentSelectedTask = task;
                Log.d(TAG, "handle: Selected Task: " + task);
                notifyPropertyChanged(BR.currentSelectedTask);
                ((DashboardActivity) baseActivity).showTaskDetails(task);
            }
        });
    }

    @Bindable
    public List<Task> getPendingTasks() {
        return pendingTasks;
    }

    @Bindable
    public List<Task> getCompletedTasks() {
        return completedTasks;
    }

    @Bindable
    public Task getCurrentSelectedTask() {
        return currentSelectedTask;
    }

    @Bindable
    public FirebaseUser getUser() {
        return user;
    }

    @Bindable
    public boolean isCompletedTasksFetched() {
        return completedTasksFetched;
    }

    @Bindable
    public boolean isPendingTasksFetched() {
        return pendingTasksFetched;
    }

    @Bindable
    public boolean isBeneficiariesFetched() {
        return beneficiariesFetched;
    }
}
