package in.sunfox.needs.assesment.volunteer_registration;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;
import java.io.IOException;

import in.sunfox.needs.assesment.DashboardActivity;
import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.authentication.model.Volunteer;
import in.sunfox.needs.assesment.databinding.DialogChooseMediaSourceBinding;
import in.sunfox.needs.assesment.helper.FirebaseAuthenticationHelper;
import in.sunfox.needs.assesment.helper.FirebaseFirestoreHelper;
import in.sunfox.needs.assesment.helper.MediaHelper;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;

public class VolunteerRegistrationActivity extends BaseActivity {

    private static final int REQUEST_IMAGE_SELECT = 109;
    private static final int REQUEST_IMAGE_CAPTURE = 110;
    private String currentCapturedPhotoFilePath = "";
    public static final String TAG = "Act.VolunteerReg";
    private RegistrationViewModel registrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_registration);

        String registeredPhoneNumber = FirebaseAuthenticationHelper.getLoggedInUser().getPhoneNumber();
        FirebaseFirestoreHelper.getVolunteer(registeredPhoneNumber, new FirebaseFirestoreHelper.DocumentQueryListener<Volunteer>() {
            @Override
            public void onDocumentFound(Volunteer document) {
                openDashboard();
            }

            @Override
            public void onDocumentNotFound(String reason) {
            }
        });
        setFragment(new EnterVolunteerDetailsOneFragment());
    }

    public void showEnterMoreVolunteerDetails() {
        setFragment(new EnterVolunteerDetailsTwoFragment());
    }

    public void openDashboard() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }


    public void openChoosePhotoSourceDialog(View.OnClickListener openGalleryClickListener, View.OnClickListener openCameraClickListener) {
        DialogChooseMediaSourceBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_choose_media_source, findViewById(R.id.activity_authentication_root), false);
        binding.setOpenCameraOnClickListener(openCameraClickListener);
        binding.setOpenGalleryOnClickListener(openGalleryClickListener);
        DialogPlus dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(binding.getRoot()))
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setCancelable(true)
                .create();
        dialogPlus.show();
    }


    public void openCameraToTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = MediaHelper.createImageFile(this);
                currentCapturedPhotoFilePath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                showError(ex.getMessage());
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void openGalleryToPickPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_SELECT);
    }


    @Override
    public BaseObservableViewModel getAssignedViewModel() {
        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        return registrationViewModel;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_volunteer_registration_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d(TAG, "IMG CAPTURE :: onActivityResult: " + currentCapturedPhotoFilePath);
                    registrationViewModel.setImagePath(currentCapturedPhotoFilePath);
                }
                break;
            case REQUEST_IMAGE_SELECT:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String path = MediaHelper.getRealPathFromURI(this, data.getData());
                        Log.d(TAG, "IMG SELECTED:: onActivityResult: " + path);
                        registrationViewModel.setImagePath(path);
                    }
                }
                break;
        }
    }
}
