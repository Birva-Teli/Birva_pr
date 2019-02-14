package example.com.birva_pr.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import example.com.birva_pr.R;
import example.com.birva_pr.helpers.AppConstants;
import io.fabric.sdk.android.Fabric;

public class SplashScreen extends AppCompatActivity {
    private static int Splash_time_out=3000;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        SharedPreferences sharedpreferences = getSharedPreferences(AppConstants.MyPREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedpreferences.edit();
        boolean isLoggedIn=sharedpreferences.getBoolean(AppConstants.isLoggedIn, false);
        boolean isIntroSeen=sharedpreferences.getBoolean(AppConstants.isIntroSeen,false);
        if(isIntroSeen)
        {
            if (isLoggedIn)
            {
                i = new Intent(SplashScreen.this, MainActivity.class);
            }
            else
            {
                i = new Intent(SplashScreen.this, LoginActivity.class);

            }
        }
        else
        {
                i = new Intent(SplashScreen.this, IntroActivity.class);
        }
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i);
    }


}
