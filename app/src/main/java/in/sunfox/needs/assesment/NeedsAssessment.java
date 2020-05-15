package in.sunfox.needs.assesment;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import in.sunfox.needs.assesment.helper.FirebaseAuthenticationHelper;
import in.sunfox.needs.assesment.helper.FirebaseStorageHelper;
import in.sunfox.needs.assesment.helper.LocaleHelper;
import in.sunfox.needs.assesment.volunteer_registration.SyncLocalCacheService;

public class NeedsAssessment extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseStorageHelper.initializeFirebaseStorage();
        FirebaseAuthenticationHelper.initializeFirebaseAuth();
        startService(new Intent(this, SyncLocalCacheService.class));
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}
