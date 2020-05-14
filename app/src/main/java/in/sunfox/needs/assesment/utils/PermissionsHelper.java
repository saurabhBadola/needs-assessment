package in.sunfox.needs.assesment.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsHelper {

    public static boolean hasCameraPermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasStoragePermission(Activity activity) {
        return ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity context) {

        ActivityCompat.requestPermissions(context,
                new String[]{Manifest.permission.CAMERA},
                23);

    }

    public static void requestStoragePermission(Activity context) {

        ActivityCompat.requestPermissions(context,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                23);

    }
}
