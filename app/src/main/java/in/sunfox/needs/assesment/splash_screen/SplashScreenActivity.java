package in.sunfox.needs.assesment.splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.sunfox.needs.assesment.R;
import in.sunfox.needs.assesment.authentication.AuthenticationActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(
                () -> {
                    startActivity(new Intent(SplashScreenActivity.this, AuthenticationActivity.class));
                    finish();
                },
                1500);
    }
}
