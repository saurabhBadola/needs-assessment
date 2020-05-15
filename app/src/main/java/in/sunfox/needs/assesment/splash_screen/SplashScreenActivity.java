package in.sunfox.needs.assesment.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.authentication.AuthenticationActivity;
import in.sunfox.needs.assesment.change_language.ChangeLanguageActivity;
import in.sunfox.needs.assesment.helper.SharedPreferenceHelper;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(
                () -> {
                    Intent intent = null;
                    if (SharedPreferenceHelper.hasLanguageChosen(SplashScreenActivity.this)) {
                        intent = new Intent(SplashScreenActivity.this, AuthenticationActivity.class);
                    } else {
                        intent = new Intent(SplashScreenActivity.this, ChangeLanguageActivity.class);

                    }
                    startActivity(intent);
                    finish();
                },
                1500);
    }
}
