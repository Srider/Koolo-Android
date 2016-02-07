package com.example.srravela.koolo.calendar.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.fragments.KooloAddCalendarEventFragment;
import com.example.srravela.koolo.calendar.fragments.KooloBorderConfigurationFragment;
import com.example.srravela.koolo.calendar.fragments.KooloCalendarFragment;
import com.example.srravela.koolo.calendar.fragments.KooloDateSetterFragment;
import com.example.srravela.koolo.calendar.listeners.KooloCalendarInteractionListener;
import com.example.srravela.koolo.calendar.listeners.OnBorderConfigurationListener;
import com.example.srravela.koolo.calendar.listeners.OnButtonClickedListener;
import com.example.srravela.koolo.entities.Utils;

import java.io.Serializable;
import java.util.Calendar;

public class KooloCalendarActivity extends KooloBaseActivity implements KooloCalendarInteractionListener {
    private static final String TAG = KooloCalendarActivity.class.getSimpleName();
    private FrameLayout kooloCalendarFragmentContainer;
    private ImageView backgroundImageView;
    KooloCalendarFragment kooloCalendarEventFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_calendar);
        mContext=getApplicationContext();
        getSupportActionBar().show();
        getSupportActionBar().setTitle(getResources().getString(R.string.calendar_title_string));
        initUI();
    }

    void initUI() {

        backgroundImageView = (ImageView)findViewById(R.id.home_image);
        loadCalendarFragment();
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

    private void loadCalendarFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        kooloCalendarEventFragment = KooloCalendarFragment.newInstance();
        transaction.replace(R.id.fragment_calendar_container, kooloCalendarEventFragment, "koolocalendarfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCalendarInteraction(Bundle urlBundle) {
        int action = (int)urlBundle.get(KooloCalendarInteractionListener.KOOLO_CALENDAR_ACTION);
        switch(action) {
            case KOOLO_DATE_PICKER_ACTION:
//                loadDatePicker();
                break;
            case KOOLO_ADD_CALENDAR_EVENT_ACTION:
                loadAddCalendarFragment();
                break;
            case KOOLO_BORDER_CONFIGURATION_SET_ACTION:
                popAction();
                break;
            case KOOLO_LOAD_BORDER_CONFIGURATION_ACTION:
                loadBorderConfigurationFragment();
                break;
            case CALENDAR_EVENT_DONE_ACTION:
                popAction();
                break;
            case CALENDAR_EVENT_CANCEL_ACTION:
                popAction();
                break;
        }
    }

    private void loadAddCalendarFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloAddCalendarEventFragment.newInstance();
        transaction.replace(R.id.fragment_calendar_container, fragment, "kooloaddcalendareventfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadBorderConfigurationFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloBorderConfigurationFragment.newInstance();
        transaction.replace(R.id.fragment_calendar_container, fragment, "kooloborderconfigurationfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void popAction() {
        if(getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        if(kooloCalendarEventFragment.isVisible()) {
            kooloCalendarEventFragment.onBackPressed();
        }
    }
}
