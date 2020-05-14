package in.sunfox.needs.assesment.helper;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthenticationHelper {

    private static FirebaseAuth firebaseAuth;

    public static void initializeFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static boolean hasUserLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public static FirebaseUser getLoggedInUser(){
        return firebaseAuth.getCurrentUser();
    }

    public static void logout(){
        firebaseAuth.signOut();
    }

}
