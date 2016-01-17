package com.example.srravela.koolo.calendar.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.activities.KooloCalendarActivity;
import com.example.srravela.koolo.calendar.adapters.KooloCalendarDatesAdapter;
import com.example.srravela.koolo.calendar.adapters.KooloCalendarEventsAdapter;
import com.example.srravela.koolo.calendar.listeners.KooloCalendarInteractionListener;
import com.example.srravela.koolo.calendar.utils.DateAndTimeUtility;
import com.example.srravela.koolo.checklists.activities.KooloChecklistActivity;
import com.example.srravela.koolo.checklists.adapters.KooloChecklistAdapter;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.checklists.utils.GoalsDataStore;
import com.example.srravela.koolo.entities.CalendarDates;
import com.example.srravela.koolo.entities.CalendarEvents;
import com.example.srravela.koolo.entities.Checklist;
import com.example.srravela.koolo.entities.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class KooloCalendarFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final String TAG = KooloCalendarFragment.class.getSimpleName();
    private ImageView backgroundImageView;
    private View rootView;
    GridView calendarDatesGridView;
    ListView calendarEventsListView;

    KooloCalendarDatesAdapter calendarDatesAdapter;
    KooloCalendarEventsAdapter calendarEventsAdapter;

    private static int currentWindowIndex;

    HashMap<String, List<CalendarDates>> calendarDates = null;
    List<CalendarEvents> calendarEvents = null;

    private Button leftArrowButton, rightArrowButton, addCalendarEventButton;
    private Context mContext;
    private KooloCalendarActivity mActivity;
    public static KooloCalendarFragment fragmentCalendar;
    private KooloCalendarInteractionListener mListener;
    private Bundle calendarBundle;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment KooloCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloCalendarFragment newInstance() {
        KooloCalendarFragment fragment = new KooloCalendarFragment();
        return fragment;
    }

    public KooloCalendarFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_calendar, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=(KooloCalendarActivity)getActivity();
        mListener = mActivity;
        mContext=mActivity.getApplicationContext();

        initUI();
    }

    /**
     * Method for configuring the UI
     */
    private void initUI(){
        setHasOptionsMenu(true);

        addCalendarEventButton = (Button)rootView.findViewById(R.id.add_calendar_event_button);
        addCalendarEventButton.setOnClickListener(this);

        //Image
        backgroundImageView = (ImageView)rootView.findViewById(R.id.checklist_background_image);

        SharedPreferences backgroundSharedPreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
        SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);
        if(backgroundImageFlagPreferences.getBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, false)) {
            Uri myUri = Uri.parse(backgroundSharedPreferences.getString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, KooloApplication.getImageUri()));
            backgroundImageView.setImageURI(myUri);
        } else {
            backgroundImageView.setImageResource(R.drawable.background);
        }

        //Button
        leftArrowButton = (Button) rootView.findViewById(R.id.left_arrow_button);
        leftArrowButton.setOnClickListener(this);

        rightArrowButton = (Button) rootView.findViewById(R.id.right_arrow_button);
        rightArrowButton.setOnClickListener(this);

        //CalendarDates
        calendarDatesGridView= (GridView) rootView.findViewById(R.id.calendar_dates_grid_view);
        calendarDates = loadCalendarDates();
        currentWindowIndex = 0;
        if(calendarDates!=null){

            calendarDatesAdapter = new KooloCalendarDatesAdapter(calendarDates.get(""+currentWindowIndex), mContext);
            calendarDatesAdapter.notifyDataSetChanged();
            calendarDatesGridView.setAdapter(calendarDatesAdapter);
            calendarDatesGridView.setOnItemClickListener(this);

            //Get Event loading for current date.
            calendarEvents = loadCalendarEventsForDate(calendarDates.get(""+currentWindowIndex).get(0));
        } else {
            Log.i(TAG, "NO ITEMS");
        }

        //CalendarEvents
        calendarEventsListView= (ListView) rootView.findViewById(R.id.caledar_events_list_view);

        if(calendarEvents!=null){
            calendarEventsAdapter = new KooloCalendarEventsAdapter(calendarEvents, mContext);
            calendarEventsListView.setAdapter(calendarEventsAdapter);
            calendarEventsListView.setOnItemClickListener(this);
        } else {
            Log.i(TAG, "NO ITEMS");
        }

    }

    //TODO:
    private HashMap<String, List<CalendarDates>> loadCalendarDates() {
        HashMap<String, List<CalendarDates>> calendarDatesMap = null;
        DateAndTimeUtility sharedDateAndtimeUtility =  DateAndTimeUtility.getSharedDateAndTimeUtility(mContext);
        calendarDatesMap = sharedDateAndtimeUtility.getAllFormattedDates();
        return calendarDatesMap;
    }

    //TODO:
    private List<CalendarEvents> loadCalendarEventsForDate(CalendarDates date) {
        List<CalendarEvents> calendarEventsList = null;

        return calendarEventsList;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_calendar_event_button:
                loadAddCalendarEventFragment();
                break;
            case R.id.left_arrow_button:
                leftArrowButtonClicked();
                break;
            case R.id.right_arrow_button:
                rightArrowButtonClicked();
                break;
        }
    }


    private void loadAddCalendarEventFragment() {
        Bundle bundle=new Bundle();
        bundle.putInt(KooloCalendarInteractionListener.KOOLO_CALENDAR_ACTION, KooloCalendarInteractionListener.KOOLO_ADD_CALENDAR_EVENT_ACTION);
        mListener.onCalendarInteraction(bundle);
    }


    private void leftArrowButtonClicked() {

        if(currentWindowIndex >=0) {
            currentWindowIndex -= 1;
            calendarDatesAdapter.items = calendarDates.get(""+currentWindowIndex);
            calendarDatesGridView.invalidateViews();
        }
    }

    private void rightArrowButtonClicked() {
        if(currentWindowIndex <=4) {
            currentWindowIndex += 1;
            calendarDatesAdapter.items = calendarDates.get(""+currentWindowIndex);
            calendarDatesGridView.invalidateViews();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


}
