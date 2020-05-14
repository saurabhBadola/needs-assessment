package in.sunfox.needs.assesment.beneficiary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;
import java.io.IOException;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.databinding.DialogChooseMediaSourceBinding;
import in.sunfox.needs.assesment.helper.MediaHelper;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;

public class BeneficiaryActivity extends BaseActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int REQUEST_IMAGE_SELECT = 102;
    private String currentCapturedPhotoFilePath;
    AddBeneficiaryPageOneFragment beneficiaryPageOneFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary);
        beneficiaryPageOneFragment = new AddBeneficiaryPageOneFragment();
        setFragment(beneficiaryPageOneFragment);
    }

    public void addFamilyMember(Beneficiary.FamilyMember familyMember){
        beneficiaryPageOneFragment.addNewRow(familyMember);
    }


    public void showPageTwo(){
        setFragment(new AddBeneficiaryPageTwoFragment());
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


    public void openChoosePhotoSourceDialog(View.OnClickListener openGalleryClickListener, View.OnClickListener openCameraClickListener) {
        DialogChooseMediaSourceBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_choose_media_source, findViewById(R.id.activity_authentication_root), false);
        binding.setOpenCameraOnClickListener( openCameraClickListener);
        binding.setOpenGalleryOnClickListener( openGalleryClickListener);
        DialogPlus dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(new ViewHolder(binding.getRoot()))
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setCancelable(true)
                .create();
        dialogPlus.show();
    }


    @Override
    public BaseObservableViewModel getAssignedViewModel() {
        return ViewModelProviders.of(this).get(BeneficiaryViewModel.class);
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_beneficiary_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == Activity.RESULT_OK) {
                   // Log.d(TAG, "IMG CAPTURE :: onActivityResult: " + currentCapturedPhotoFilePath);
                    ((BeneficiaryViewModel)getAssignedViewModel()).setLocalPathForBeneficiaryIdentityProofPhoto(currentCapturedPhotoFilePath);
                }
                break;
            case REQUEST_IMAGE_SELECT:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String path = MediaHelper.getRealPathFromURI(this, data.getData());
                       // Log.d(TAG, "IMG SELECTED:: onActivityResult: " + path);
                        ((BeneficiaryViewModel)getAssignedViewModel()).setLocalPathForBeneficiaryIdentityProofPhoto(path);
                    }
                }
                break;
        }
    }

    public void goBack(View view) {
        finish();
    }
}
