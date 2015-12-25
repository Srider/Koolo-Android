package com.example.srravela.koolo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.home.activities.KooloHomeActivity;
import com.example.srravela.koolo.passcode.activities.KooloPasscodeVerificationActivity;

import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by srravela on 11/18/2015.
 */
public class KooloApplication extends Application implements Application.ActivityLifecycleCallbacks{
    public static final String TAG=KooloApplication.class.getSimpleName();
    public static final String FIRST_TIME_LAUNCH="FIRST_TIME_LAUNCH";
    public static final String DATE_BUTTON_CONFIGURATION="DATE_BUTTON_CONFIGURATION";
    public static final String DATE_BUTTON_COLOR="DATE_BUTTON_COLOR";

    public static String imageUri = null;
    public static final String PASSCODE_ENABLED="PASSCODE_ENABLED";
    public static final String SELECTED_PASSCODE="SELECTED_PASSCODE";
    public static final String SECURITY_QUESTION_ENABLED="SECURITY_QUESTION_ENABLED";
    public static final String SECURITY_QUESTION="SECURITY_QUESTION";
    public static final String SELECTED_SECURITY_QUESTION="SELECTED_SECURITY_QUESTION";
    public static final String SECURITY_QUESTION_ANSWER="SECURITY_QUESTION_ANSWER";

    public static final String QUOTES_ENABLED="QUOTES_ENABLED";
    public static final String SELECTED_QUOTE_INDEX="SELECTED_QUOTE_INDEX";
    public static final String SELECTED_HOME_QUOTE="SELECTED_HOME_QUOTE";
    public static final String QUOTE_DISPLAY_ENABLED="QUOTE_DISPLAY_ENABLED";
    public static final String SELECTED_BACKGROUND_IMAGE_URI="SELECTED_BACKGROUND_IMAGE_URI";
    public static final String BACKGROUND_IMAGE_SELECTED="BACKGROUND_IMAGE_SELECTED";

    public static final String DARK_GREY="DARK_GREY";
    public static final String LIGHT_GREY="LIGHT_GREY";
    public static final String BLUE="BLUE";
    public static final String YELLOW="YELLOW";
    public static final String RED="RED";
    public static final String PINK="PINK";
    public static final String ORANGE="ORANGE";
    public static final String BLACK="BLACK";
    public static final String THEME_GREEN="THEME_GREEN";
    public static final String BROWN="BROWN";
    public static int count = 0;
    public static final int RESULT_LOAD_IMAGE = 1;
    private Context mContext;
    private static KooloApplication mAppInstance;

    public static boolean isExternalIntentLoaded = false;

    //Image Uri.
    public static void setImageUri(String imageUri) {
        KooloApplication.imageUri = imageUri;
    }

    public static String getImageUri() {
        return imageUri;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext=getApplicationContext();
        registerActivityLifecycleCallbacks(this);
    }

    public static synchronized KooloApplication getInstance(){
        return mAppInstance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
       ++count;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        --count;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
