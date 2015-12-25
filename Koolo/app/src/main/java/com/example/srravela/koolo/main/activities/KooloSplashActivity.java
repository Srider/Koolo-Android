package com.example.srravela.koolo.main.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.home.activities.KooloHomeActivity;
import com.example.srravela.koolo.passcode.activities.KooloPasscodeVerificationActivity;

public class KooloSplashActivity extends KooloBaseActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                loadLandingPage();

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_koolo_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void loadLandingPage() {
        Intent mainIntent;
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(KooloApplication.FIRST_TIME_LAUNCH, MODE_PRIVATE);
        Boolean firstTimeLaunch = sharedPreferences.getBoolean(KooloApplication.FIRST_TIME_LAUNCH,true);
        if(firstTimeLaunch) {
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putBoolean(KooloApplication.FIRST_TIME_LAUNCH, false);
            editor.commit();
            mainIntent = new Intent(KooloSplashActivity.this, KooloMainActivity.class);
            startActivity(mainIntent);
        } else {
            mainIntent = new Intent(KooloSplashActivity.this, KooloHomeActivity.class);
            startActivity(mainIntent);

//            SharedPreferences enablePasscodePreferences=mContext.getSharedPreferences(KooloApplication.PASSCODE_ENABLED, mContext.MODE_PRIVATE);
//            if(enablePasscodePreferences.getBoolean(KooloApplication.PASSCODE_ENABLED, false)) {
//                mainIntent = new Intent(KooloSplashActivity.this, KooloPasscodeVerificationActivity.class);
//                startActivity(mainIntent);
//            } else {
//                mainIntent = new Intent(KooloSplashActivity.this, KooloHomeActivity.class);
//                startActivity(mainIntent);
//            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
