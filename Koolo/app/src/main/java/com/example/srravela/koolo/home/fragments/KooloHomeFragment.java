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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.adapters.KooloCalendarEventsAdapter;
import com.example.srravela.koolo.calendar.utils.DateAndTimeUtility;
import com.example.srravela.koolo.calendar.utils.EventsDataStore;
import com.example.srravela.koolo.entities.CalendarDates;
import com.example.srravela.koolo.entities.CalendarEvents;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.home.listeners.KooloHomeInteractionListener;

import java.sql.Date;
import java.util.List;

public class KooloHomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String TAG=KooloHomeFragment.class.getSimpleName();
    private Context mContext;
    private Activity mActivity;
    private static boolean isMenuExpanded = false;
    TextView mSelectedQuoteTextView, mDayNotificationText, mTimeNotificationText, mLastDayNotificationText, mCalendarDateText;
    Button mSettingsButton, mCalendarDateButton;
    Button mPopUpMenuButton, mPopUpCalendarButton, mPopUpCameraButton, mPopUpChecklistButton;
    private View rootView, popUpMenuView;
    private KooloHomeInteractionListener mListener;
    private static CalendarEvents selectedEvent;
    ListView todaysEventsListView;
    List<CalendarEvents> calendarEvents = null;
    KooloCalendarEventsAdapter calendarEventsAdapter;
    CalendarDates todaysDate = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloHomeFragment newInstance() {
        KooloHomeFragment fragment = new KooloHomeFragment();
        return fragment;
    }

    public KooloHomeFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_home,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=getActivity();
        mContext=mActivity.getApplicationContext();
        mListener =(KooloHomeInteractionListener) mActivity;
        setHasOptionsMenu(true);
    }

    private void intiUI(){
        todaysDate = getTodaysCalendarDate();

        View homeNotificationsView = (View)rootView.findViewById(R.id.home_notification_view);

        popUpMenuView = (View)rootView.findViewById(R.id.menu_button_view);
        popUpMenuView.setVisibility(View.GONE);

        mPopUpMenuButton = (Button)rootView.findViewById(R.id.menu_button);
        mPopUpMenuButton.setOnClickListener(this);

        mPopUpCalendarButton = (Button)popUpMenuView.findViewById(R.id.calendar_button);
        mPopUpCalendarButton.setOnClickListener(this);

        mPopUpCameraButton = (Button)popUpMenuView.findViewById(R.id.camera_button);
        mPopUpCameraButton.setOnClickListener(this);

        mPopUpChecklistButton = (Button)popUpMenuView.findViewById(R.id.checklist_button);
        mPopUpChecklistButton.setOnClickListener(this);

        mCalendarDateButton = (Button) homeNotificationsView.findViewById(R.id.calendar_date_button);
        mCalendarDateButton.setOnClickListener(this);

        SharedPreferences sharedPreferences=mContext.getSharedPreferences(KooloApplication.DATE_BUTTON_CONFIGURATION, mContext.MODE_PRIVATE);
        Boolean isDateConfigurationButtonSet = sharedPreferences.getBoolean(KooloApplication.FIRST_TIME_LAUNCH, true);
        if(isDateConfigurationButtonSet) {
            String dataConfiguration = sharedPreferences.getString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.DARK_GREY);

            switch(dataConfiguration) {
                case  KooloApplication.DARK_GREY:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_darkgray);
                    break;
                case KooloApplication.THEME_GREEN:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_theme_green);
                    break;
                case KooloApplication.LIGHT_GREY:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_gray);
                    break;
                case KooloApplication.BLUE:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_blue);
                    break;
                case  KooloApplication.YELLOW:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_yellow);
                    break;
                case  KooloApplication.RED:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_red);
                    break;
                case KooloApplication.PINK:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_magenta);
                    break;
                case  KooloApplication.ORANGE:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_orange);
                    break;
                case  KooloApplication.BLACK:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_black);
                    break;
                case  KooloApplication.BROWN:
                    mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_brown);
                    break;
            }
        } else {
            mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_red);
        }

        SharedPreferences homeQuotePreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_HOME_QUOTE, mContext.MODE_PRIVATE);
        String selectedQuote = homeQuotePreferences.getString(KooloApplication.SELECTED_HOME_QUOTE, mContext.getResources().getString(R.string.default_quote_string));

        mSelectedQuoteTextView = (TextView)homeNotificationsView.findViewById(R.id.quote_text);
        mSelectedQuoteTextView.setText(selectedQuote);

        SharedPreferences enableQuotePreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_HOME_QUOTE, mContext.MODE_PRIVATE);
        if(enableQuotePreferences.getBoolean(KooloApplication.QUOTE_DISPLAY_ENABLED, true)) {
            mSelectedQuoteTextView.setVisibility(View.VISIBLE);
        } else {
            mSelectedQuoteTextView.setVisibility(View.INVISIBLE);
        }

        // Todays Events
        todaysEventsListView= (ListView) rootView.findViewById(R.id.todays_events_list_view);
        calendarEvents = loadCalendarEvents();

        if(calendarEvents!=null && calendarEvents.size()>0){
            calendarEventsAdapter = new KooloCalendarEventsAdapter(calendarEvents, mContext, true);
            todaysEventsListView.setAdapter(calendarEventsAdapter);
            todaysEventsListView.setOnItemClickListener(this);

            mCalendarDateButton.setText(todaysDate.getDayText() + "\n" + todaysDate.getDateText());
            CalendarEvents lastEvent = calendarEvents.get(calendarEvents.size() - 1);

            if(selectedEvent != null) {
                configureDateButtonForColorType(selectedEvent.getColorType());
            } else if(lastEvent != null) {
                selectedEvent = lastEvent;
                configureDateButtonForColorType(lastEvent.getColorType());
            } 
        } else {
            configureDateButtonForColorType(Utils.ColorType.DARK_GREY);
            Log.i(TAG, "NO ITEMS");
        }

        mCalendarDateButton.setText(todaysDate.getDayText() + "\n" + todaysDate.getDateText());

        mSettingsButton = (Button)rootView.findViewById(R.id.settings_button);
        mSettingsButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        intiUI();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.settings_button:
                loadSettingsActivity();
                break;
            case R.id.menu_button:
                showOrHideMenu();
                break;
            case R.id.calendar_date_button:
                loadDateButtonConfigurationFragment();
                break;

            case R.id.calendar_button:
                triggerCalendarActivityEvent();
                break;
            case R.id.checklist_button:
                triggerChecklistActivityEvent();
                break;
            case R.id.camera_button:
                triggerCameraActivityEvent();
                break;
        }
    }

    private void showOrHideMenu() {
        if(isMenuExpanded) {
            popUpMenuView.setVisibility(View.GONE);
            isMenuExpanded=false;
        } else {
            popUpMenuView.setVisibility(View.VISIBLE);
            isMenuExpanded=true;
        }
    }

    void loadSettingsActivity() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloHomeInteractionListener.KOOLO_HOME_ACTION, KooloHomeInteractionListener.KOOLO_SETTINGS_BUTTON_CLICKED);
        mListener.onHomeInteraction(bundle);
    }

    void loadDateButtonConfigurationFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloHomeInteractionListener.KOOLO_HOME_ACTION, KooloHomeInteractionListener.KOOLO_CALENDAR_DATE_BUTTON_CLICKED);
        bundle.putSerializable(KooloHomeInteractionListener.KOOLO_HOME_SELECTED_CALENDAR_EVENT, selectedEvent);
        mListener.onHomeInteraction(bundle);
    }


    void triggerCalendarActivityEvent() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloHomeInteractionListener.KOOLO_HOME_ACTION, KooloHomeInteractionListener.KOOLO_POPUP_CALENDAR_BUTTON_CLICKED);
        mListener.onHomeInteraction(bundle);

    }

    void triggerChecklistActivityEvent() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloHomeInteractionListener.KOOLO_HOME_ACTION, KooloHomeInteractionListener.KOOLO_POPUP_CHECKLIST_BUTTON_CLICKED);
        mListener.onHomeInteraction(bundle);
    }

    void triggerCameraActivityEvent() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloHomeInteractionListener.KOOLO_HOME_ACTION, KooloHomeInteractionListener.KOOLO_POPUP_CAMERA_BUTTON_CLICKED);
        mListener.onHomeInteraction(bundle);
    }


    //TODO:
    private List<CalendarEvents> loadCalendarEvents() {
        List<CalendarEvents> calendarEventsList = null;
        EventsDataStore sharedEventsDataStore;
        if(calendarEventsList == null) {
            sharedEventsDataStore = EventsDataStore.getSharedEventsDataStore(mContext.getResources().getString(R.string.events_file_name), mContext);
            //First Get transfers from file.
            if(todaysDate != null) {
                calendarEventsList = sharedEventsDataStore.readEventsForDate(todaysDate);
            }
        }
        return calendarEventsList;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //first get event.
        selectedEvent = calendarEvents.get(position);
        configureDateButtonForColorType(calendarEvents.get(position).getColorType());
    }

    private CalendarDates getTodaysCalendarDate() {
        String [] dateComponents = DateAndTimeUtility.getSharedDateAndTimeUtility(mContext).getDateComponents();
        String dateText = dateComponents[2];
        String dayText = dateComponents[0];
        String monthText = dateComponents[1];
        String yearText = dateComponents[3];


        Log.d(TAG, dateText+dayText+monthText);

        Utils.ColorType colorType= Utils.ColorType.DARK_GREY;

        return new CalendarDates(dateText, dayText, monthText, yearText, colorType);
    }

    private void configureDateButtonForColorType(Utils.ColorType colorType) {
        switch (colorType) {
            case DARK_GREY:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_darkgray);
                break;
            case YELLOW:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_yellow);
                break;
            case BLACK:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_black);
                break;
            case RED:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_red);
                break;
            case GREY:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_gray);
                break;
            case PINK:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_magenta);
                break;
            case ORANGE:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_orange);
                break;
            case BLUE:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_blue);
                break;
            case GREEN:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_theme_green);
                break;
            case BROWN:
                mCalendarDateButton.setBackgroundResource(R.drawable.drawable_mood_color_brown);
                break;
        }
    }
}
