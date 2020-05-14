package in.sunfox.needs.assesment.viewmodel_com;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseObservableViewModel viewModel = getAssignedViewModel();
        progressDialog = new ProgressDialog(this);
        viewModel.observeViewModelEvents().observe(this, event -> {
            if (event != null && !event.isHandled()) {
                handleViewModelAction(event);
            }
        });
    }

    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void setLoadingMessage(String message) {
        if (progressDialog != null)
            progressDialog.setTitle(message);
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


    protected void handleViewModelAction(ViewModelEvent event) {
        event.handle(this);
    }

    public abstract BaseObservableViewModel getAssignedViewModel();
}
