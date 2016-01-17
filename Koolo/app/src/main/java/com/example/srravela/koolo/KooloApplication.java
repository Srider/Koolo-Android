package com.example.srravela.koolo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by srravela on 11/18/2015.
 */
public class KooloApplication extends Application implements Application.ActivityLifecycleCallbacks{
    public static final String TAG=KooloApplication.class.getSimpleName();
    public static final String FIRST_TIME_LAUNCH="FIRST_TIME_LAUNCH";
    public static final String DATE_BUTTON_CONFIGURATION="DATE_BUTTON_CONFIGURATION";
    public static final String EVENT_BUTTON_CONFIGURATION = "EVENT_BUTTON_CONFIGURATION";

    public static final String EVENT_DATE_ENABLED="EVENT_DATE_ENABLED";
    public static final String EVENT_DATE_CONFIGURATION="EVENT_DATE_CONFIGURATION";
    public static final String EVENT_DATE_SELECTION_ACTION="EVENT_DATE_SELECTION_ACTION";
    public static final String EVENT_TYPE_SELECTION_ACTION="EVENT_TYPE_SELECTION_ACTION";
    public static final String EVENT_TIME_SELECTION_ACTION = "EVENT_TIME_SELECTION_ACTION";

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

    private Locale locale = null;

//    @Override
//    public void onConfigurationChanged(Configuration newConfig)
//    {
//        super.onConfigurationChanged(newConfig);
//        if (locale != null)
//        {
//            newConfig.locale = locale;
//            Locale.setDefault(locale);
//            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
//        }
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext=getApplicationContext();
        registerActivityLifecycleCallbacks(this);
//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
//
//        Configuration config = getBaseContext().getResources().getConfiguration();
//
//        String lang = settings.getString(getString(R.string.pref_locale), "");
//        if (! "".equals(lang) && ! config.locale.getLanguage().equals(lang))
//        {
//            locale = new Locale(lang);
//            Locale.setDefault(locale);
//            config.locale = locale;
//            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
//        }
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
