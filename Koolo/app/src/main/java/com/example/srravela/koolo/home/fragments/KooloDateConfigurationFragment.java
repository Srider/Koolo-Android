package com.example.srravela.koolo.home.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.utils.DateAndTimeUtility;
import com.example.srravela.koolo.calendar.utils.EventsDataStore;
import com.example.srravela.koolo.entities.CalendarEvents;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.home.listeners.KooloHomeInteractionListener;

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

        String[] dateComponents = DateAndTimeUtility.getSharedDateAndTimeUtility(mContext).getDateComponents();

        mDefaultConfigurationButton  = (Button) rootView.findViewById(R.id.default_date_button);
        mDefaultConfigurationButton.setOnClickListener(this);
        mDefaultConfigurationButton.setText(dateComponents[0] + "\n" + dateComponents[2]);

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

            Utils.ColorType selectedColorType = null;

            SharedPreferences configurationSharedPreferences=mContext.getSharedPreferences(KooloApplication.HOME_DATE_BUTTON_CONFIGURATION, mContext.MODE_PRIVATE);
            Boolean isDateConfigurationButtonSet = configurationSharedPreferences.getBoolean(KooloApplication.IS_HOME_DATE_BUTTON_CONFIGURATION_SET, false);
            SharedPreferences.Editor configurationEditor=configurationSharedPreferences.edit();
            switch(v.getId()) {
                case R.id.default_date_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.DARK_GREY);
                    selectedColorType = Utils.ColorType.DARK_GREY;
                    break;

                case R.id.yellow_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.YELLOW);
                    selectedColorType = Utils.ColorType.YELLOW;
                    break;

                case R.id.black_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.BLACK);
                    selectedColorType = Utils.ColorType.BLACK;

                    break;

                case R.id.red_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.RED);
                    selectedColorType = Utils.ColorType.RED;
                    break;

                case R.id.light_gray_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.LIGHT_GREY);
                    selectedColorType = Utils.ColorType.GREY;
                    break;

                case R.id.pink_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.PINK);
                    selectedColorType = Utils.ColorType.PINK;
                    break;

                case R.id.orange_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.ORANGE);
                    selectedColorType = Utils.ColorType.ORANGE;
                    break;

                case R.id.blue_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.BLUE);
                    selectedColorType = Utils.ColorType.BLUE;
                    break;

                case R.id.theme_green_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.THEME_GREEN);
                    selectedColorType = Utils.ColorType.GREEN;
                    break;

                case R.id.brown_button:
                    configurationEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.BROWN);
                    selectedColorType = Utils.ColorType.BROWN;
                    break;
            }

        if(selectedCalendarEvent != null) {
            selectedCalendarEvent.setColorType(selectedColorType);
        }

        if(!isDateConfigurationButtonSet) {
            configurationEditor.putBoolean(KooloApplication.IS_HOME_DATE_BUTTON_CONFIGURATION_SET, true);
            configurationEditor.commit();
        }

        configurationEditor.commit();
        updateCalendarEvents();
    }

    private void updateCalendarEvents() {
        EventsDataStore sharedEventsDataStore;
        if(selectedCalendarEvent != null) {
            sharedEventsDataStore = EventsDataStore.getSharedEventsDataStore(mContext.getResources().getString(R.string.events_file_name), mContext);
            boolean status = sharedEventsDataStore.updateFileForCalendarEvent(selectedCalendarEvent);
            if (status) {
                Log.d(TAG, "Updated Event Successfully");
            } else {
                Log.d(TAG, "Update Event Failed");
            }
        }
        Bundle bundle = new Bundle();
        bundle.putInt(KooloHomeInteractionListener.KOOLO_HOME_ACTION, KooloHomeInteractionListener.KOOLO_CALENDAR_DATE_CONFIGURATION_CHANGED);
        mListener.onHomeInteraction(bundle);
    }
}
