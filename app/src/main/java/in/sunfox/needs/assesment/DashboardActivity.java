package in.sunfox.needs.assesment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import in.sunfox.needs.assesment.authentication.AuthenticationActivity;
import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.beneficiary.BeneficiaryActivity;
import in.sunfox.needs.assesment.dashboard.BeneficiaryListFragment;
import in.sunfox.needs.assesment.dashboard.DashboardViewModel;
import in.sunfox.needs.assesment.dashboard.DoTaskActivity;
import in.sunfox.needs.assesment.dashboard.HomeFragment;
import in.sunfox.needs.assesment.dashboard.MyProfileFragment;
import in.sunfox.needs.assesment.dashboard.tasks.Task;
import in.sunfox.needs.assesment.dashboard.tasks.TaskDetailsFragment;
import in.sunfox.needs.assesment.dashboard.tasks.TasksFragment;
import in.sunfox.needs.assesment.databinding.ActivityDashboardBinding;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;

public class DashboardActivity extends BaseActivity {

    public static final String TAG = "dasboard.activity";

    @Override
    public BaseObservableViewModel getAssignedViewModel() {
        return ViewModelProviders.of(this).get(DashboardViewModel.class);
    }

    public void doTask(String beneficiaryPhone) {
        Intent intent = new Intent(this, DoTaskActivity.class);
        intent.putExtra(DoTaskActivity.EXTRA_BENEFICIARY_PHONE, beneficiaryPhone);
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        binding.setViewModel((DashboardViewModel) getAssignedViewModel());
        BottomNavigationView bottomNavigationView = findViewById(R.id.activity_dashboard_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationView.setSelectedItemId(R.id.bottomNavigationHomeMenuId);
    }

    public void logoutUser() {
        finish();
        startActivity(new Intent(this, AuthenticationActivity.class));
    }

    public void openBeneficiaryDetails(Beneficiary beneficiary) {
        Intent intent = new Intent(this, BeneficiaryDetailsActivity.class);
        intent.putExtra(BeneficiaryDetailsActivity.EXTRA_BENEFICIARY, beneficiary);
        startActivity(intent);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.bottomNavigationHomeMenuId:
                    setFragment(new HomeFragment(), "home");
                    return true;
                case R.id.bottomNavigationTasksMenuId:
                    setFragment(new TasksFragment(), "tasks");
                    return true;
                case R.id.bottomNavigationBeneficiariesMenuId:
                    setFragment(new BeneficiaryListFragment(), "beneficiaries");
                    return true;
                case R.id.bottomNavigationMyProfileMenuId:
                    setFragment(new MyProfileFragment(), "profile");
                    return true;
            }
            return false;
        }
    };

    public void openAddBeneficiaryActivity() {
        startActivity(new Intent(this, BeneficiaryActivity.class));
    }

    public void showTaskDetails(Task task) {
        setFragment(new TaskDetailsFragment(), "tdf");
    }

    private void setFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }


        Fragment fragmentTemp = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction.add(R.id.activity_dashboard_fragment_root_container, fragmentTemp, fragmentTag);
        } else {
            fragmentTransaction.show(fragmentTemp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }
}
