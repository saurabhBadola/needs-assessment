package in.sunfox.needs.assesment.volunteer_registration;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.authentication.model.Volunteer;
import in.sunfox.needs.assesment.helper.FirebaseAuthenticationHelper;
import in.sunfox.needs.assesment.helper.FirebaseFirestoreHelper;
import in.sunfox.needs.assesment.helper.FirebaseStorageHelper;
import in.sunfox.needs.assesment.utils.DateTimeUtils;
import in.sunfox.needs.assesment.utils.PermissionsHelper;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;
import in.sunfox.needs.assesment.viewmodel_com.ViewModelEvent;
import in.sunfox.needs.assesment.BR;

import static in.sunfox.needs.assesment.volunteer_registration.VolunteerRegistrationActivity.TAG;


public class RegistrationViewModel extends BaseObservableViewModel {


    public enum CurrentSelectingImageCategory {
        PERMIT,
        IDENTITY,
        FACE_MASK
    }

    public enum BooleanAnswerField {
        PERMIT,
        FACE_MASK,
        FOOD_SHORTAGE,
        VEHICLE,
        COVID_19_SYMPTOMS
    }

    private Volunteer volunteer;
    private CurrentSelectingImageCategory currentSelectingImageFor;
    private String localPathToPermitSelectedImage = "";
    private String localPathToIdentitySelectedImage = "";
    private String localPathToFaceMaskSelectedImage = "";

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        volunteer = new Volunteer();
        volunteer.setFaceShieldData(new Volunteer.AttachableField());
        volunteer.setPermitData(new Volunteer.AttachableField());
    }

    @Bindable
    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public void dispatchChooseImageSourceDialog(CurrentSelectingImageCategory currentSelectingImageFor) {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((VolunteerRegistrationActivity) baseActivity).openChoosePhotoSourceDialog(view -> {
                    dispatchOpenGalleryForPhoto();
                }, view -> {
                    dispatchOpenCameraForPhoto();
                });
                ;
                setCurrentSelectingImageCategory(currentSelectingImageFor);
            }
        });
    }

    public void dispatchOpenCameraForPhoto() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                if (PermissionsHelper.hasCameraPermission(baseActivity))
                    ((VolunteerRegistrationActivity) baseActivity).openCameraToTakePhoto();
                else
                    PermissionsHelper.requestCameraPermission(baseActivity);
            }
        });
    }

    public void dispatchOpenGalleryForPhoto() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                if (PermissionsHelper.hasStoragePermission(baseActivity))
                    ((VolunteerRegistrationActivity) baseActivity).openGalleryToPickPhoto();
                else
                    PermissionsHelper.requestStoragePermission(baseActivity);
            }
        });
    }

    public void dispatchShowEnterMoreInformationScreen() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((VolunteerRegistrationActivity) baseActivity).showEnterMoreVolunteerDetails();
            }
        });
    }

    public void dispatchSubmitData() {
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                submitData(baseActivity);
            }
        });
    }

    @Bindable
    public String getLocalPathToPermitSelectedImage() {
        return localPathToPermitSelectedImage;
    }

    public void setLocalPathToPermitSelectedImage(String localPathToPermitSelectedImage) {
        this.localPathToPermitSelectedImage = localPathToPermitSelectedImage;
        notifyPropertyChanged(BR.localPathToPermitSelectedImage);
    }

    @Bindable
    public String getLocalPathToIdentitySelectedImage() {
        return localPathToIdentitySelectedImage;
    }

    public void setLocalPathToIdentitySelectedImage(String localPathToIdentitySelectedImage) {
        this.localPathToIdentitySelectedImage = localPathToIdentitySelectedImage;
        notifyPropertyChanged(BR.localPathToIdentitySelectedImage);
    }

    @Bindable
    public String getLocalPathToFaceMaskSelectedImage() {
        return localPathToFaceMaskSelectedImage;
    }

    public void setLocalPathToFaceMaskSelectedImage(String localPathToFaceMaskSelectedImage) {
        this.localPathToFaceMaskSelectedImage = localPathToFaceMaskSelectedImage;
        notifyPropertyChanged(BR.localPathToFaceMaskSelectedImage);
    }

    public CurrentSelectingImageCategory getCurrentSelectingImageFor() {
        return currentSelectingImageFor;
    }

    public void setCurrentSelectingImageCategory(CurrentSelectingImageCategory currentSelectingImageFor) {
        this.currentSelectingImageFor = currentSelectingImageFor;
    }


    public void setBooleanForField(BooleanAnswerField field, boolean value) {
        switch (field) {
            case PERMIT:
                volunteer.getPermitData().setHasData(value);
                break;
            case FOOD_SHORTAGE:
                volunteer.setHasFoodShortage(value);
                break;

            case COVID_19_SYMPTOMS:
                volunteer.setHasCovid19Symptoms(value);
                break;

            case FACE_MASK:
                volunteer.getFaceShieldData().setHasData(value);
                break;

            case VEHICLE:
                volunteer.setHasVehicle(value);
                break;
        }
        notifyPropertyChanged(BR._all);
        Log.d(TAG, "setBooleanForField: " + volunteer);
    }

    public void submitData(BaseActivity baseActivity) {
        Log.d(TAG, "submitData: ");
        baseActivity.showLoading(baseActivity.getString(R.string.initialising_submit_request));
        String registeredVolunteerPhoneNumber = FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber();
        if (localPathToIdentitySelectedImage == null || localPathToIdentitySelectedImage.isEmpty()) {
            baseActivity.dismissLoading();
            baseActivity.showError(baseActivity.getString(R.string.error_add_identity_proof));
        } else if (volunteer.getFaceShieldData().isHasData() && localPathToFaceMaskSelectedImage != null && !localPathToFaceMaskSelectedImage.isEmpty()) {
            String remoteUrlPath = "images/" + registeredVolunteerPhoneNumber + "_face_mask.jpg";
            try {
                baseActivity.setLoadingMessage(baseActivity.getString(R.string.uploading_face_mask_data));
                FirebaseStorageHelper.uploadFile(new File(localPathToFaceMaskSelectedImage), remoteUrlPath, e -> {
                    Log.d(TAG, "submitData: FaceMaskImageuploadFailed: " + e.getMessage());
                }, taskSnapshot -> {
                    Log.d(TAG, "submitData: FaceMaskImageuploadSuccess");
                    baseActivity.setLoadingMessage(baseActivity.getString(R.string.face_mask_data_upload_successful));
                    volunteer.getFaceShieldData().setDataResourcePath(remoteUrlPath);
                    uploadIdentityData(registeredVolunteerPhoneNumber, baseActivity);
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            uploadIdentityData(registeredVolunteerPhoneNumber, baseActivity);
        }
    }

    private void uploadPermitData(String registeredVolunteerPhoneNumber, BaseActivity baseActivity) {
        if (volunteer.getPermitData().isHasData() && localPathToPermitSelectedImage != null && !localPathToPermitSelectedImage.isEmpty()) {
            String remoteUrlPath = "images/" + registeredVolunteerPhoneNumber + "_permit.jpg";
            try {
                baseActivity.setLoadingMessage(baseActivity.getString(R.string.uploading_permit_data));
                FirebaseStorageHelper.uploadFile(new File(localPathToPermitSelectedImage), remoteUrlPath, e -> {
                    Log.d(TAG, "submitData: PermitImageuploadFailed: " + e.getMessage());
                }, taskSnapshot -> {
                    baseActivity.setLoadingMessage(baseActivity.getString(R.string.permit_data_uploaded));
                    Log.d(TAG, "submitData: PermitImageUploadSuccess");
                    volunteer.getPermitData().setDataResourcePath(remoteUrlPath);
                    baseActivity.setLoadingMessage(baseActivity.getString(R.string.sending_volunteer_details_to_server));
                    FirebaseFirestoreHelper.addVolunteer(volunteer, FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber(), o -> {
                        Log.d(TAG, "submitData: Success");
                        baseActivity.setLoadingMessage(baseActivity.getString(R.string.volunteer_details_sent));
                        baseActivity.dismissLoading();
                        ((VolunteerRegistrationActivity) baseActivity).openDashboard();
                    }, e -> {
                        Log.d(TAG, "submitData: Failed: " + e.getLocalizedMessage());
                        baseActivity.showError(baseActivity.getString(R.string.error) + e.getLocalizedMessage());
                    });
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            baseActivity.setLoadingMessage(baseActivity.getString(R.string.sending_volunteer_details_to_server));
            FirebaseFirestoreHelper.addVolunteer(volunteer, FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber(), o -> {
                Log.d(TAG, "submitData: Success");
                baseActivity.setLoadingMessage(baseActivity.getString(R.string.volunteer_details_sent));
                baseActivity.dismissLoading();
                ((VolunteerRegistrationActivity) baseActivity).openDashboard();
            }, e -> {
                Log.d(TAG, "submitData: Failed: " + e.getMessage());
                baseActivity.showError(baseActivity.getString(R.string.error) + e.getLocalizedMessage());

            });
        }
    }

    private void uploadIdentityData(String registeredVolunteerPhoneNumber, BaseActivity baseActivity) {
        if (localPathToIdentitySelectedImage != null && !localPathToIdentitySelectedImage.isEmpty()) {
            String remoteUrlPath = "images/" + registeredVolunteerPhoneNumber + "_id_proof.jpg";
            try {
                baseActivity.setLoadingMessage(baseActivity.getString(R.string.uploading_identity_data));
                FirebaseStorageHelper.uploadFile(new File(localPathToIdentitySelectedImage), remoteUrlPath, e -> {
                    Log.d(TAG, "submitData: IdentityImageuploadFailed: " + e.getMessage());
                }, taskSnapshot -> {
                    baseActivity.setLoadingMessage(baseActivity.getString(R.string.identity_data_uploaded));
                    Log.d(TAG, "submitData: IdentityImageUploadSuccess");
                    volunteer.setIdentityProofResourcePath(remoteUrlPath);
                    Log.d(TAG, "submitData: Setting img proof resource: " + volunteer.getIdentityProofResourcePath());
                    uploadPermitData(registeredVolunteerPhoneNumber, baseActivity);
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            uploadPermitData(registeredVolunteerPhoneNumber, baseActivity);
        }
    }

    public void setImagePath(String path) {
        switch (getCurrentSelectingImageFor()) {
            case PERMIT:
                setLocalPathToPermitSelectedImage(path);
                break;
            case FACE_MASK:
                setLocalPathToFaceMaskSelectedImage(path);
                break;
            case IDENTITY:
                setLocalPathToIdentitySelectedImage(path);
                break;
        }
    }
}
