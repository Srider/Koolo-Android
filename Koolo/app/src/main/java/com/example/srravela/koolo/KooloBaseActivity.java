package com.example.srravela.koolo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.srravela.koolo.checklists.fragments.KooloChecklistOptionsFragment;
import com.example.srravela.koolo.humor.activities.KooloHumourActivity;
import com.example.srravela.koolo.passcode.activities.KooloPasscodeVerificationActivity;
import com.example.srravela.koolo.passcode.fragments.KooloPasscodeVerificationFragment;

//public class KooloBaseActivity extends AppCompatActivity {
//
//    private static final String TAG=KooloBaseActivity.class.getSimpleName();
//    public Context mContext;
//
//    public static boolean isAppWentToBg = false;
//
//    public static boolean isWindowFocused = false;
//
//    public static boolean isBackPressed = false;
//
//
//    private  void applicationWillEnterForeground() {
//        if (isAppWentToBg) {
//            isAppWentToBg = false;
//            SharedPreferences enablePasscodePreferences=mContext.getSharedPreferences(KooloApplication.PASSCODE_ENABLED, mContext.MODE_PRIVATE);
//            String actualPasscode = enablePasscodePreferences.getString(KooloApplication.SELECTED_PASSCODE, null);
//            boolean isPasscodeenabled = enablePasscodePreferences.getBoolean(KooloApplication.PASSCODE_ENABLED, false);
//            if(actualPasscode!= null && isPasscodeenabled) {
//                loadPasscodeVerificationActivity();
//            }
//        }
//    }
//
//    //Humours Activity
//    void loadPasscodeVerificationActivity() {
//        Intent licenseIntent = new Intent(KooloBaseActivity.this, KooloPasscodeVerificationActivity.class);
//        startActivity(licenseIntent);
//    }
//
//    public  void applicationDidEnterBackground() {
//        if (!isWindowFocused) {
//            isAppWentToBg = true;
//            Toast.makeText(getApplicationContext(),
//                    "App is Going to Background", Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        Log.d(TAG, "onStart isAppWentToBg " + isAppWentToBg);
//
//        applicationWillEnterForeground();
//
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        Log.d(TAG, "onStop ");
//        applicationDidEnterBackground();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    /**
//     *This method sets the idle timeout times. the idle time out times is set for 10 minutes
//     * The time out value can be changed from the strings_login.xml file
//     */
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mContext=getApplicationContext();
//        Log.d(TAG, "Starting application" + this.toString());
//    }
//
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//
//        isWindowFocused = hasFocus;
//
//        if (isBackPressed && !hasFocus) {
//            isBackPressed = false;
//            isWindowFocused = true;
//        }
//
//        super.onWindowFocusChanged(hasFocus);
//    }
//
//
//}


public class KooloBaseActivity extends AppCompatActivity {

    private static final String TAG=KooloBaseActivity.class.getSimpleName();
    public Context mContext;

    public static boolean isAppWentToBg = false;

    public static boolean isWindowFocused = false;

    public static boolean isBackPressed = false;

    private  void applicationWillEnterForeground() {
        if (isAppWentToBg && KooloApplication.count ==0 && !KooloApplication.isExternalIntentLoaded) {
            isAppWentToBg = false;
            SharedPreferences enablePasscodePreferences=mContext.getSharedPreferences(KooloApplication.PASSCODE_ENABLED, mContext.MODE_PRIVATE);
            String actualPasscode = enablePasscodePreferences.getString(KooloApplication.SELECTED_PASSCODE, null);
            boolean isPasscodeenabled = enablePasscodePreferences.getBoolean(KooloApplication.PASSCODE_ENABLED, false);
            if(actualPasscode!= null && isPasscodeenabled) {
                loadPasscodeVerificationActivity();
            }
        }
    }

    //Humours Activity
    void loadPasscodeVerificationActivity() {
        Intent licenseIntent = new Intent(KooloBaseActivity.this, KooloPasscodeVerificationActivity.class);
        startActivity(licenseIntent);
    }

    public  void applicationDidEnterBackground() {
        if (!isWindowFocused && KooloApplication.count == 0 && !KooloApplication.isExternalIntentLoaded) {
            isAppWentToBg = true;
            Toast.makeText(getApplicationContext(),"App is Going to Background", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart isAppWentToBg " + isAppWentToBg);

        applicationWillEnterForeground();

        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "onStop ");
        applicationDidEnterBackground();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     *This method sets the idle timeout times. the idle time out times is set for 10 minutes
     * The time out value can be changed from the strings_login.xml file
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getApplicationContext();
        Log.d(TAG, "Starting application" + this.toString());
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        isWindowFocused = hasFocus;

        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }

        super.onWindowFocusChanged(hasFocus);
    }


}
