package in.sunfox.needs.assesment.helper;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import in.sunfox.needs.assesment.authentication.AuthenticationActivity;

public class FirebaseStorageHelper {

    private static FirebaseStorage firebaseStorage;

    public static void initializeFirebaseStorage() {
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public static void uploadFile(File localFile, String remoteFilePath, OnFailureListener onFailureListener, OnSuccessListener<UploadTask.TaskSnapshot> onSuccessListener) throws FileNotFoundException {
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference child = storageReference.child(remoteFilePath);
        InputStream stream = new FileInputStream(localFile);
        UploadTask uploadTask = child.putStream(stream);
        uploadTask.addOnFailureListener(onFailureListener).addOnSuccessListener(onSuccessListener);
    }

}
