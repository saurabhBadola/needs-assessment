package in.sunfox.needs.assesment.dashboard;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.databinding.ActivityDoTaskBinding;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;

public class DoTaskActivity extends BaseActivity {


    private DoTaskViewModel doTaskViewModel;
    public static final  String EXTRA_BENEFICIARY_PHONE = "EXTRA_BENEFICIARY_PHONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDoTaskBinding doTaskBinding = DataBindingUtil.setContentView(this, R.layout.activity_do_task);
        doTaskBinding.setViewmodel(doTaskViewModel);
        if (getIntent() != null) {
            String beneficiaryPhone = getIntent().getStringExtra(EXTRA_BENEFICIARY_PHONE);
            doTaskViewModel.setBeneficiaryToken(beneficiaryPhone);
        }

    }


    @Override
    public BaseObservableViewModel getAssignedViewModel() {
        doTaskViewModel = ViewModelProviders.of(this).get(DoTaskViewModel.class);
        return doTaskViewModel;
    }
}
