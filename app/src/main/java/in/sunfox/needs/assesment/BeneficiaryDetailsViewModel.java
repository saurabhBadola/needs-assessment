package in.sunfox.needs.assesment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;
import in.sunfox.needs.assesment.viewmodel_com.ViewModelEvent;

public class BeneficiaryDetailsViewModel extends BaseObservableViewModel {

    private Beneficiary beneficiary;

    public void dispatchGoBack() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                baseActivity.finish();
            }
        });
    }

    @Bindable
    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public BeneficiaryDetailsViewModel(@NonNull Application application) {
        super(application);
    }


}
