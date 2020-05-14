package in.sunfox.needs.assesment.dashboard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import in.sunfox.needs.assesment.beneficiary.Beneficiary;
import in.sunfox.needs.assesment.helper.FirebaseFirestoreHelper;
import in.sunfox.needs.assesment.utils.DateTimeUtils;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;
import in.sunfox.needs.assesment.viewmodel_com.ViewModelEvent;


public class DoTaskViewModel extends BaseObservableViewModel {

    private Beneficiary beneficiary;
    private String beneficiaryId = "";
    private String tasksDone = "";
    private String needsDescription = "";
    private String recipientOfScheme = "";


    public void setBeneficiaryToken(String beneficiaryToken) {
        FirebaseFirestoreHelper.getBeneficiaryByPhone(beneficiaryToken, new FirebaseFirestoreHelper.DocumentQueryListenerWithId<Beneficiary>() {
            @Override
            public void onDocumentFound(Beneficiary document, String documentId) {
                beneficiary = document;
                beneficiaryId = documentId;
                notifyPropertyChanged(BR.beneficiary);
            }

            @Override
            public void onDocumentNotFound(String reason) {

            }
        });
    }


    public void dispatchUpdateBeneficiary() {

        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                baseActivity.showLoading("Sending details to the server....");

                if (!tasksDone.isEmpty()) {
                    tasksDone = beneficiary.getTasksDone().concat("\n/**").concat(DateTimeUtils.getCurrentDateTime()).concat("**/").concat("\n").concat(tasksDone);
                }
                if (!needsDescription.isEmpty()) {
                    needsDescription = beneficiary.getNeedsDescription().concat("\n/**").concat(DateTimeUtils.getCurrentDateTime()).concat("**/").concat("\n").concat(needsDescription);
                }
                if (!recipientOfScheme.isEmpty()) {
                    recipientOfScheme = beneficiary.getRecipientOfAnyScheme().concat("\n/**").concat(DateTimeUtils.getCurrentDateTime()).concat("**/").concat("\n").concat(recipientOfScheme);
                }

                FirebaseFirestoreHelper.updateBeneficiaryDetails(beneficiaryId, tasksDone, needsDescription, recipientOfScheme, new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        baseActivity.dismissLoading();
                        baseActivity.showToast("Successfully completed task");
                        baseActivity.finish();
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        baseActivity.dismissLoading();
                        baseActivity.showToast("Error: " + e.getLocalizedMessage());
                    }
                });


            }
        });


    }

    @Bindable
    public String getTasksDone() {
        return tasksDone;
    }

    @Bindable
    public String getNeedsDescription() {
        return needsDescription;
    }

    @Bindable
    public String getRecipientOfScheme() {
        return recipientOfScheme;
    }

    @Bindable
    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    public void setTasksDone(String tasksDone) {
        this.tasksDone = tasksDone;
    }

    public void setNeedsDescription(String needsDescription) {
        this.needsDescription = needsDescription;
    }

    public void setRecipientOfScheme(String recipientOfScheme) {
        this.recipientOfScheme = recipientOfScheme;
    }

    public DoTaskViewModel(@NonNull Application application) {
        super(application);
    }


}
