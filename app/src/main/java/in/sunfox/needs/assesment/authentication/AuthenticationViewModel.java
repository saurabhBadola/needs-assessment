package in.sunfox.needs.assesment.authentication;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Bindable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

import java.util.concurrent.TimeUnit;

import in.sunfox.needs.assesment.BR;
import in.sunfox.needs.assesment.authentication.model.Volunteer;
import in.sunfox.needs.assesment.viewmodel_com.BaseActivity;
import in.sunfox.needs.assesment.viewmodel_com.BaseObservableViewModel;
import in.sunfox.needs.assesment.viewmodel_com.ViewModelEvent;

public class AuthenticationViewModel extends BaseObservableViewModel {

    private String phone;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken token;

    private String otp;
    public static final String TAG = "AuthVM.TAG";


    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
    }

    public void sendSms() {
        postViewModelEvent(new SendSmsEvent(false));
    }

    public void resendSms() {
        postViewModelEvent(new SendSmsEvent(true));
    }

    public void verifyOtp() {
        postViewModelEvent(new VerifyOtpEvent());
    }

    public void openVolunteerRegistration(){
        postViewModelEvent(new ViewModelEvent() {
            @Override
            public void handle(BaseActivity baseActivity) {
                super.handle(baseActivity);
                ((AuthenticationActivity)baseActivity).openVolunteerRegistrationActivity();
            }
        });
    }

    @Bindable
    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
        notifyPropertyChanged(BR.otp);
    }

    @Bindable
    public String getPhone() {
        return phone;

    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }


    class SendSmsEvent extends ViewModelEvent {

        private boolean isResendingOtp;

        public SendSmsEvent(boolean isResendingOtp) {
            this.isResendingOtp = isResendingOtp;
        }

        @Override
        public void handle(BaseActivity baseActivity) {
            super.handle(baseActivity);
            AuthenticationActivity activity = ((AuthenticationActivity) baseActivity);

            OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    Log.d(TAG, "onVerificationCompleted:" + credential);
                    activity.showVerificationComplete();
                    activity.dismissLoading();

                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener(baseActivity, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithCredential:success");

                                        FirebaseUser user = task.getResult().getUser();
                                        // ...
                                    } else {
                                        // Sign in failed, display a message and update the UI
                                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                            // The verification code entered was invalid
                                        }
                                    }
                                }
                            });
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Log.w(TAG, "onVerificationFailed", e);
                    activity.dismissLoading();
                    activity.showError("Error: " + e);
                }

                @Override
                public void onCodeSent(@NonNull String verifId,
                                       @NonNull PhoneAuthProvider.ForceResendingToken t) {
                    Log.d(TAG, "onCodeSent:" + verifId);
                    verificationId = verifId;
                    token = t;
                    activity.showEnterOtpScreen();
                    activity.dismissLoading();
                }
            };
            activity.showLoading("Sending OTP. Please wait...");

            if (isResendingOtp)
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phone, 60, TimeUnit.SECONDS, baseActivity, callbacks, token);
            else
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phone, 60, TimeUnit.SECONDS, baseActivity, callbacks);
        }
    }

    class VerifyOtpEvent extends ViewModelEvent {
        @Override
        public void handle(BaseActivity baseActivity) {
            super.handle(baseActivity);
            AuthenticationActivity activity = ((AuthenticationActivity) baseActivity);
            activity.showLoading("Verifying OTP. Please wait...");
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    activity.dismissLoading();
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential: success");
                        activity.showVerificationComplete();
                    } else {
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Log.w(TAG, "INVALID TOKEN", task.getException());
                            activity.showError("Invalid OTP. Please check the OTP or resend OTP.");

                        } else {
                            activity.showError(task.getException().getMessage());
                        }
                    }
                }
            });
        }
    }


}
