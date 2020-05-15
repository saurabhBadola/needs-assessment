package in.sunfox.needs.assesment.beneficiary;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.library.baseAdapters.BR;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.helper.FirebaseAuthenticationHelper;
import in.sunfox.needs.assesment.helper.FirebaseFirestoreHelper;
import in.sunfox.needs.assesment.helper.FirebaseStorageHelper;
import in.sunfox.needs.assesment.helper.LocalCacheHelper;
import in.sunfox.needs.assesment.utils.PermissionsHelper;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;
import in.sunfox.needs.assesment.viewmodel_com.ViewModelEvent;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;

public class BeneficiaryViewModel extends BaseObservableViewModel {


    private Beneficiary beneficiary;
    private List<Beneficiary.FamilyMember> familyMembers;
    private String localPathForBeneficiaryIdentityProofPhoto = "";


    public BeneficiaryViewModel(@NonNull Application application) {
        super(application);
        familyMembers = new ArrayList<>();
        beneficiary = new Beneficiary();
        beneficiary.setFamilyMembers(familyMembers);
    }


    public void dispatchShowPageTwo() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((BeneficiaryActivity) baseActivity).showPageTwo();
            }
        });
    }

    public void dispatchChooseImageSourceDialog() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((BeneficiaryActivity) baseActivity).openChoosePhotoSourceDialog(view -> {
                    dispatchOpenGallery();
                }, view -> {
                    dispatchOpenCamera();
                });

            }
        });
    }

    public void dispatchAddBeneficiary() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                try {
                    if (localPathForBeneficiaryIdentityProofPhoto == null || localPathForBeneficiaryIdentityProofPhoto.isEmpty())
                        baseActivity.showError(baseActivity.getString(R.string.select_identity_proof));
                    else submitData(baseActivity);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    baseActivity.showError(e.getMessage());
                }
            }
        });
    }

    private void dispatchOpenGallery() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                if(PermissionsHelper.hasStoragePermission(baseActivity))
                ((BeneficiaryActivity) baseActivity).openGalleryToPickPhoto();
                else
                    PermissionsHelper.requestStoragePermission(baseActivity);
            }
        });
        Log.d(TAG, "dispatchOpenGallery: ");
    }

    private void dispatchOpenCamera() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                if(PermissionsHelper.hasCameraPermission(baseActivity))
                ((BeneficiaryActivity) baseActivity).openCameraToTakePhoto();
                else
                    PermissionsHelper.requestCameraPermission(baseActivity);
            }
        });
        Log.d(TAG, "dispatchOpenCamera: ");
    }

    public void addFamilyMember() {
        Beneficiary.FamilyMember familyMember = new Beneficiary.FamilyMember();
        familyMembers.add(familyMember);
        Log.d(TAG, "Cliked(): " + beneficiary);

        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((BeneficiaryActivity) baseActivity).addFamilyMember(familyMember);
            }
        });
    }

    @Bindable
    public Beneficiary getBeneficiary() {
        return beneficiary;
    }

    private void submitData(BaseActivity activity) throws FileNotFoundException {
        activity.showLoading(activity.getString(R.string.adding_beneficiary_please_wait));
        String volunteerPhone = FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber();
        String remoteUrl = "images/" + volunteerPhone + "_" + beneficiary.getName().replaceAll(" ", "_") + "_identity.jpg";
        FirebaseStorageHelper.uploadFile(new File(localPathForBeneficiaryIdentityProofPhoto), remoteUrl, e -> {
            activity.dismissLoading();
            activity.showError("Error: " + e.getMessage());
        }, taskSnapshot -> {
            beneficiary.setIdentityProofUrl(remoteUrl);
            beneficiary.setReferrerVolunteerPhoneNumber(volunteerPhone);
            FirebaseFirestoreHelper.addBeneficiary(beneficiary, documentReference -> {
                activity.dismissLoading();
                activity.showToast(activity.getString(R.string.successfully_added_beneficiary));
                activity.finish();
            }, e -> {
                activity.dismissLoading();
                activity.showError(activity.getString(R.string.error) + e.getLocalizedMessage());
            });
        });
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Bindable
    public String getLocalPathForBeneficiaryIdentityProofPhoto() {
        return localPathForBeneficiaryIdentityProofPhoto;
    }

    public void setLocalPathForBeneficiaryIdentityProofPhoto(String localPathForBeneficiaryIdentityProofPhoto) {
        this.localPathForBeneficiaryIdentityProofPhoto = localPathForBeneficiaryIdentityProofPhoto;
        notifyPropertyChanged(BR.localPathForBeneficiaryIdentityProofPhoto);
    }
}
