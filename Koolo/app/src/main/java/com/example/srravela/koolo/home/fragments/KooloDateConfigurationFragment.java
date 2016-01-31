package com.example.srravela.koolo.home.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.listeners.KooloCalendarInteractionListener;
import com.example.srravela.koolo.calendar.utils.EventsDataStore;
import com.example.srravela.koolo.entities.CalendarEvents;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.home.listeners.KooloHomeInteractionListener;

import java.util.List;

public class KooloDateConfigurationFragment extends Fragment implements View.OnClickListener{

    public static final String TAG=KooloDateConfigurationFragment.class.getSimpleName();
    private Context mContext;
    private Activity mActivity;
    Button mDefaultConfigurationButton;
    Button mAquaMarineButton;
    Button mYellowButton;
    Button mBlueButton;
    Button mPinkButton;
    Button mRedButton;
    Button mBlackButton;
    Button mLightGrayButton;
    Button mOrangeButton;
    Button mBrownButton;
    CalendarEvents selectedCalendarEvent;
    private View rootView;
    private KooloHomeInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloDateConfigurationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloDateConfigurationFragment newInstance() {
        KooloDateConfigurationFragment fragment = new KooloDateConfigurationFragment();
        return fragment;
    }

    public KooloDateConfigurationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_koolo_date_configuration,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=getActivity();
        mContext=mActivity.getApplicationContext();
        mListener =(KooloHomeInteractionListener) mActivity;
        Bundle eventBundle = getArguments();
        selectedCalendarEvent = (CalendarEvents)eventBundle.getSerializable("SelectedEvent");

        setHasOptionsMenu(true);
        intiUI();
    }

    private void intiUI(){

        mDefaultConfigurationButton  = (Button) rootView.findViewById(R.id.default_date_button);
        mDefaultConfigurationButton.setOnClickListener(this);

        mAquaMarineButton  = (Button) rootView.findViewById(R.id.theme_green_button);
        mAquaMarineButton.setOnClickListener(this);

        mYellowButton  = (Button) rootView.findViewById(R.id.yellow_button);
        mYellowButton.setOnClickListener(this);

        mBlueButton  = (Button) rootView.findViewById(R.id.blue_button);
        mBlueButton.setOnClickListener(this);

        mPinkButton  = (Button) rootView.findViewById(R.id.pink_button);
        mPinkButton.setOnClickListener(this);

        mRedButton  = (Button) rootView.findViewById(R.id.red_button);
        mRedButton.setOnClickListener(this);

        mBlackButton  = (Button) rootView.findViewById(R.id.black_button);
        mBlackButton.setOnClickListener(this);

        mLightGrayButton  = (Button) rootView.findViewById(R.id.light_gray_button);
        mLightGrayButton.setOnClickListener(this);

        mOrangeButton  = (Button) rootView.findViewById(R.id.orange_button);
        mOrangeButton.setOnClickListener(this);

        mBrownButton = (Button) rootView.findViewById(R.id.brown_button);
        mBrownButton.setOnClickListener(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        SharedPreferences configurationSharedPreferences=mContext.getSharedPreferences(KooloApplication.DATE_BUTTON_CONFIGURATION, mContext.MODE_PRIVATE);
        Boolean isDateConfigurationButtonSet = configurationSharedPreferences.getBoolean(KooloApplication.DATE_BUTTON_CONFIGURATION,true);

        if(!isDateConfigurationButtonSet) {
            SharedPreferences.Editor configurationEditor=configurationSharedPreferences.edit();
            configurationEditor.putBoolean(KooloApplication.DATE_BUTTON_CONFIGURATION, true);
            configurationEditor.commit();
        }

        SharedPreferences colorSharedPreferences=mContext.getSharedPreferences(KooloApplication.DATE_BUTTON_CONFIGURATION, mContext.MODE_PRIVATE);
        SharedPreferences.Editor colorEditor=colorSharedPreferences.edit();

        switch(v.getId()) {
            case R.id.default_date_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.DARK_GREY);
                selectedCalendarEvent.setColorType(Utils.ColorType.DARK_GREY);
                break;

            case R.id.yellow_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.YELLOW);
                selectedCalendarEvent.setColorType(Utils.ColorType.YELLOW);
                break;

            case R.id.black_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.BLACK);
                selectedCalendarEvent.setColorType(Utils.ColorType.BLACK);
                break;

            case R.id.red_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.RED);
                selectedCalendarEvent.setColorType(Utils.ColorType.RED);
                break;

            case R.id.light_gray_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.LIGHT_GREY);
                selectedCalendarEvent.setColorType(Utils.ColorType.GREY);
                break;

            case R.id.pink_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.PINK);
                selectedCalendarEvent.setColorType(Utils.ColorType.PINK);
                break;

            case R.id.orange_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.ORANGE);
                selectedCalendarEvent.setColorType(Utils.ColorType.ORANGE);
                break;

            case R.id.blue_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.BLUE);
                selectedCalendarEvent.setColorType(Utils.ColorType.BLUE);
                break;

            case R.id.theme_green_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.THEME_GREEN);
                selectedCalendarEvent.setColorType(Utils.ColorType.GREEN);
                break;

            case R.id.brown_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.BROWN);
                selectedCalendarEvent.setColorType(Utils.ColorType.BROWN);
                break;
        }
        colorEditor.commit();
        updateCalendarEvents();
    }

    private void updateCalendarEvents() {
        EventsDataStore sharedEventsDataStore;
        sharedEventsDataStore = EventsDataStore.getSharedEventsDataStore(mContext.getResources().getString(R.string.events_file_name), mContext);
        boolean status = sharedEventsDataStore.updateFileForCalendarEvent(selectedCalendarEvent);
        if(status) {
            Log.d(TAG, "Updated Event Successfully");
            Toast.makeText(mContext,"Event updated Successfully", Toast.LENGTH_LONG).show();

        } else {
            Log.d(TAG, "Update Event Failed");
            Toast.makeText(mContext,"Event update failed", Toast.LENGTH_LONG).show();

        }
        Bundle bundle = new Bundle();
        bundle.putInt(KooloHomeInteractionListener.KOOLO_HOME_ACTION, KooloHomeInteractionListener.KOOLO_CALENDAR_DATE_CONFIGURATION_CHANGED);
        mListener.onHomeInteraction(bundle);
    }
}
