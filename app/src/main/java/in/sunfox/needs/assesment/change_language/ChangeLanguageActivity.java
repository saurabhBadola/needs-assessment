package in.sunfox.needs.assesment.change_language;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.authentication.AuthenticationActivity;
import in.sunfox.needs.assesment.databinding.ActivityChangeLanguageBinding;
import in.sunfox.needs.assesment.helper.SharedPreferenceHelper;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;

public class ChangeLanguageActivity extends BaseActivity {

    public static final String IS_CHANGING_LANGUAGE = "IS_CHANGING_LANGUAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChangeLanguageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_change_language);
        boolean isChangingLanguage = false;

        if (getIntent() != null) {
            isChangingLanguage = getIntent().getBooleanExtra(IS_CHANGING_LANGUAGE, false);
        }
        ((ChangeLanguageViewModel) getAssignedViewModel()).setChangingLanguage(isChangingLanguage);
        binding.setViewModel((ChangeLanguageViewModel) getAssignedViewModel());

    }

    @Override
    public BaseObservableViewModel getAssignedViewModel() {
        return ViewModelProviders.of(this).get(ChangeLanguageViewModel.class);
    }

    public void setLanguage() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void openNext() {
        SharedPreferenceHelper.setLanguageChosen(this, true);
        startActivity(new Intent(this, AuthenticationActivity.class));
        finish();
    }
}
