package com.example.srravela.koolo.calendar.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.listeners.KooloCalendarInteractionListener;
import com.example.srravela.koolo.calendar.listeners.OnBorderConfigurationListener;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.home.listeners.KooloHomeInteractionListener;

public class KooloBorderConfigurationFragment extends android.app.Fragment implements View.OnClickListener{

    public static final String TAG=KooloBorderConfigurationFragment.class.getSimpleName();
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

    private View rootView;
    private KooloCalendarInteractionListener mCalendarListener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloDateConfigurationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloBorderConfigurationFragment newInstance() {
        KooloBorderConfigurationFragment fragment = new KooloBorderConfigurationFragment();

        return fragment;
    }

    public KooloBorderConfigurationFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_border_configuration,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=getActivity();
        mContext=mActivity.getApplicationContext();
        mCalendarListener = (KooloCalendarInteractionListener)mActivity;
        setHasOptionsMenu(true);
        intiUI();
    }

    private void intiUI(){

        mDefaultConfigurationButton  = (Button) rootView.findViewById(R.id.default_date_border_button);
        mDefaultConfigurationButton.setOnClickListener(this);

        mAquaMarineButton  = (Button) rootView.findViewById(R.id.theme_green_border_button);
        mAquaMarineButton.setOnClickListener(this);

        mYellowButton  = (Button) rootView.findViewById(R.id.yellow_border_button);
        mYellowButton.setOnClickListener(this);

        mBlueButton  = (Button) rootView.findViewById(R.id.blue_border_button);
        mBlueButton.setOnClickListener(this);

        mPinkButton  = (Button) rootView.findViewById(R.id.pink_border_button);
        mPinkButton.setOnClickListener(this);

        mRedButton  = (Button) rootView.findViewById(R.id.red_border_button);
        mRedButton.setOnClickListener(this);

        mBlackButton  = (Button) rootView.findViewById(R.id.black_border_button);
        mBlackButton.setOnClickListener(this);

        mLightGrayButton  = (Button) rootView.findViewById(R.id.light_gray_border_button);
        mLightGrayButton.setOnClickListener(this);

        mOrangeButton  = (Button) rootView.findViewById(R.id.orange_border_button);
        mOrangeButton.setOnClickListener(this);

        mBrownButton = (Button) rootView.findViewById(R.id.brown_border_button);
        mBrownButton.setOnClickListener(this);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCalendarListener = null;
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
            case R.id.default_date_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.DARK_GREY);
                break;
            case R.id.yellow_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.YELLOW);
                break;
            case R.id.black_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.BLACK);
                break;
            case R.id.red_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.RED);
                break;
            case R.id.light_gray_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.LIGHT_GREY);
                break;
            case R.id.pink_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.PINK);
                break;
            case R.id.orange_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.ORANGE);
                break;
            case R.id.blue_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.BLUE);
                break;
            case R.id.theme_green_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.THEME_GREEN);
                break;
            case R.id.brown_border_button:
                colorEditor.putString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.BROWN);
                break;
        }
        colorEditor.commit();
        Bundle bundle = new Bundle();
        bundle.putInt(KooloCalendarInteractionListener.KOOLO_CALENDAR_ACTION, KooloCalendarInteractionListener.KOOLO_BORDER_CONFIGURATION_SET_ACTION);
        mCalendarListener.onCalendarInteraction(bundle);
    }


}