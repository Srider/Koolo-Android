package com.example.srravela.koolo.home.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.activities.KooloChecklistActivity;
import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.home.fragments.KooloDateConfigurationFragment;
import com.example.srravela.koolo.home.fragments.KooloHomeFragment;
import com.example.srravela.koolo.home.listeners.KooloHomeInteractionListener;
import com.example.srravela.koolo.moods.activities.KooloMoodsActivity;
import com.example.srravela.koolo.settings.activities.KooloSettingsActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class KooloHomeActivity extends KooloBaseActivity implements KooloHomeInteractionListener{
    private static final String TAG = KooloHomeActivity.class.getSimpleName();
    private FrameLayout kooloHomeFragmentContainer;
    private ImageView backgroundImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_home);
        mContext=getApplicationContext();
        getSupportActionBar().hide();
        initUI();
    }

    void initUI() {
        backgroundImageView = (ImageView)findViewById(R.id.home_image);
        loadKooloHomeFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences backgroundSharedPreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
        SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);
        if(backgroundImageFlagPreferences.getBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, false)) {
            Uri myUri = Uri.parse(backgroundSharedPreferences.getString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, KooloApplication.getImageUri()));
            backgroundImageView.setImageURI(myUri);

        } else {
            backgroundImageView.setImageResource(R.drawable.background);
        }
    }


    /**
     *  Method for loading Manual LayBy Entry Fragment.
     */
    private void loadKooloHomeFragment(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloHomeFragment.newInstance();
        transaction.replace(R.id.fragment_home_container,fragment,"koolohomefragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onHomeInteraction(Bundle urlBundle) {
        int homeAction = urlBundle.getInt(KooloHomeInteractionListener.KOOLO_HOME_ACTION);
        switch(homeAction) {
            case KOOLO_SETTINGS_BUTTON_CLICKED:
                loadSettingsActivity();
                break;
            case KOOLO_CALENDAR_DATE_BUTTON_CLICKED:
                loadCalendarDateButtonConfigurationFragment();
                break;
            case KOOLO_CALENDAR_DATE_CONFIGURATION_CHANGED:
                popDateConfigurationFromStack();
                break;
            case KOOLO_POPUP_CALENDAR_BUTTON_CLICKED:
                loadCalendarActivity();
                break;
            case KOOLO_POPUP_CHECKLIST_BUTTON_CLICKED:
                loadChecklistActivity();
                break;
            case KOOLO_POPUP_CAMERA_BUTTON_CLICKED:
                loadMoodsActivity();
                break;
        }
    }

    /**
     *  Method for Date Configurations Fragment.
     */
    private void loadCalendarDateButtonConfigurationFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloDateConfigurationFragment.newInstance();
        transaction.replace(R.id.fragment_home_container,fragment,"koolodateconfigurationfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     *  Method for loading Settings Fragment.
     */
    private void loadSettingsActivity() {
        Intent settingsIntent = new Intent(KooloHomeActivity.this, KooloSettingsActivity.class);
        startActivity(settingsIntent);
    }

    /**
     * Method for navigating to Scan Next parcel screen.
     */
    private void popDateConfigurationFromStack(){
        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.popBackStack();
    }

    private void loadCalendarActivity() {
//        Intent settingsIntent = new Intent(KooloHomeActivity.this, KooloSettingsActivity.class);
//        startActivity(settingsIntent);

    }

    private void loadChecklistActivity() {
        Intent checklistIntent = new Intent(KooloHomeActivity.this, KooloChecklistActivity.class);
        startActivity(checklistIntent);

    }

    private void loadMoodsActivity() {
        Intent settingsIntent = new Intent(KooloHomeActivity.this, KooloMoodsActivity.class);
        startActivity(settingsIntent);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
