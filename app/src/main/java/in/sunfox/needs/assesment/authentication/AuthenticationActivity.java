package in.sunfox.needs.assesment.authentication;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import in.sunfox.needs.assesment.DashboardActivity;
import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.helper.FirebaseAuthenticationHelper;
import in.sunfox.needs.assesment.helper.SharedPreferenceHelper;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;
import in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity;

public class AuthenticationActivity extends BaseActivity {

    public static final String TAG = "Test.te.te";
    private ProgressDialog progressDialog;
    private static final int REQUEST_IMAGE_CAPTURE = 346;
    private static final int REQUEST_IMAGE_SELECT = 347;
    private String imageFilePath;

    @Override
    public BaseObservableViewModel getAssignedViewModel() {
        return ViewModelProviders.of(this).get(AuthenticationViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        if (FirebaseAuthenticationHelper.hasUserLoggedIn()) {
            startActivity(new Intent(this, VolunteerRegistrationActivity.class));
            finish();
        }

        progressDialog = new ProgressDialog(this);
        setFragment(new EnterPhoneNumberFragment());
    }


    public void showEnterOtpScreen() {
        setFragment(new EnterOtpFragment());
    }

    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showLoading(String message) {
        progressDialog.setTitle(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void openVolunteerRegistrationActivity() {
        finish();
        startActivity(new Intent(this, VolunteerRegistrationActivity.class));
    }

    public void showVerificationComplete() {
        setFragment(new PhoneNumberVerifiedFragment());
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_authentication_layout_root_fragment_container, fragment);
        fragmentTransaction.commit();
    }


}
