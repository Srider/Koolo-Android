package com.example.srravela.koolo.calendar.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.activities.KooloCalendarActivity;
import com.example.srravela.koolo.calendar.adapters.KooloTagSpinnerAdapter;
import com.example.srravela.koolo.calendar.listeners.KooloCalendarInteractionListener;
import com.example.srravela.koolo.calendar.listeners.OnButtonClickedListener;
import com.example.srravela.koolo.calendar.utils.DateAndTimeUtility;
import com.example.srravela.koolo.calendar.utils.EventsDataStore;
import com.example.srravela.koolo.calendar.utils.MultipleSelectionSpinner;
import com.example.srravela.koolo.entities.CalendarEvents;
import com.example.srravela.koolo.entities.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class KooloAddCalendarEventFragment extends Fragment implements View.OnClickListener, CheckBox.OnCheckedChangeListener, OnButtonClickedListener, DatePickerDialog.OnDateSetListener, TextView.OnEditorActionListener {
    // TODO: Rename parameter arguments, choose names that match
    private View rootView;
    private Button dateButton, clockButton, eventTagButton;
    private MultipleSelectionSpinner tagSpinner;
    private KooloTagSpinnerAdapter tagAdapter = null;
    List<String> tags;
    private TextView monthText;
    private CheckBox reminderCheckbox;
    private EditText eventEditText;
    private TextView eventTypeTextView;
    private DatePicker datePicker;
    private KooloCalendarInteractionListener mListener;
    private KooloCalendarActivity mActivity;
    private Context mContext;
    public static Utils.ColorType colorType;
    private static String eventDate;
    private static String eventTime;
    private static String eventType;
    private static String eventText;
    private boolean isTough = false;
    private boolean isLong = false;
    private boolean isFaith = false;
    private boolean isRemindMe = false;

    KooloAddCalendarEventFragment eventFragment;


    public KooloAddCalendarEventFragment() {
        // Required empty public constructor
        eventFragment = this;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment KooloAddCalendarEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloAddCalendarEventFragment newInstance() {
        KooloAddCalendarEventFragment fragment = new KooloAddCalendarEventFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_koolo_add_calendar_event, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (KooloCalendarActivity) getActivity();
        mListener = mActivity;
        mContext = mActivity.getApplicationContext();
        setHasOptionsMenu(true);
        eventFragment = this;
        initUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        setBorderConfiguration();
    }

    private void initUI() {
        String[] dateComponents = DateAndTimeUtility.getSharedDateAndTimeUtility(mContext).getDateComponents();

        dateButton = (Button) rootView.findViewById(R.id.calendar_date_button);
        dateButton.setOnClickListener(this);
        dateButton.setText(dateComponents[2] + "\n" + dateComponents[0]);

        eventTagButton = (Button) rootView.findViewById(R.id.event_time_button);
        eventTagButton.setOnClickListener(this);

        clockButton = (Button) rootView.findViewById(R.id.clock_button);
        clockButton.setOnClickListener(this);

        monthText = (TextView) rootView.findViewById(R.id.month_text);
        monthText.setText(dateComponents[1]);
        reminderCheckbox = (CheckBox) rootView.findViewById(R.id.remindme_checkbox);
        reminderCheckbox.setOnCheckedChangeListener(this);
        eventEditText = (EditText) rootView.findViewById(R.id.event_edit_text);
        eventEditText.setOnEditorActionListener(this);

        eventTypeTextView = (TextView) rootView.findViewById(R.id.event_type_text);

        tagSpinner = (MultipleSelectionSpinner) rootView.findViewById(R.id.tag_spinner);
        tagSpinner.setItems(getResources().getStringArray(R.array.calendar_tag_array));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_koolo_add_event, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.event_done_option:
                addCalendarEvent();
                break;
            case R.id.event_cancel_option:
                Bundle bundle=new Bundle();
                bundle.putInt(KooloCalendarInteractionListener.KOOLO_CALENDAR_ACTION, KooloCalendarInteractionListener.CALENDAR_EVENT_CANCEL_ACTION);
                mListener.onCalendarInteraction(bundle);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calendar_date_button:
                goToBorderSelectorPage();
                break;
            case R.id.event_time_button:
                break;
            case R.id.clock_button:
                displayDatePicker();
                break;
        }
    }

    private void goToBorderSelectorPage() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloCalendarInteractionListener.KOOLO_CALENDAR_ACTION, KooloCalendarInteractionListener.KOOLO_LOAD_BORDER_CONFIGURATION_ACTION);
        mListener.onCalendarInteraction(bundle);
    }

    private void displayDatePicker() {

        KooloDateSetterFragment newFragment = new KooloDateSetterFragment();
        Calendar calender = Calendar.getInstance();
        final String[] eventDate = null;
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        newFragment.setArguments(args);
        newFragment.setOnButtonClickListener(this);
        newFragment.setOnDateSetListener(this);
        newFragment.show(getFragmentManager(), "DatePicker");
    }

    public void setBorderConfiguration() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(KooloApplication.DATE_BUTTON_CONFIGURATION, mContext.MODE_PRIVATE);
        Boolean isDateConfigurationButtonSet = sharedPreferences.getBoolean(KooloApplication.FIRST_TIME_LAUNCH, true);
        if (isDateConfigurationButtonSet) {
            String dataConfiguration = sharedPreferences.getString(KooloApplication.DATE_BUTTON_COLOR, KooloApplication.DARK_GREY);

            switch (dataConfiguration) {

                case KooloApplication.DARK_GREY:
                    colorType = Utils.ColorType.DARK_GREY;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_darkgray);
                    break;
                case KooloApplication.YELLOW:
                    colorType = Utils.ColorType.YELLOW;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_yellow);
                    break;
                case KooloApplication.BLACK:
                    colorType = Utils.ColorType.BLACK;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_black);
                    break;
                case KooloApplication.RED:
                    colorType = Utils.ColorType.RED;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_red);
                    break;
                case KooloApplication.LIGHT_GREY:
                    colorType = Utils.ColorType.GREY;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_gray);
                    break;
                case KooloApplication.PINK:
                    colorType = Utils.ColorType.PINK;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_magenta);
                    break;
                case KooloApplication.ORANGE:
                    colorType = Utils.ColorType.ORANGE;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_orange);
                    break;
                case KooloApplication.BLUE:
                    colorType = Utils.ColorType.BLUE;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_blue);
                    break;
                case KooloApplication.THEME_GREEN:
                    colorType = Utils.ColorType.GREEN;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_theme_green);
                    break;
                case KooloApplication.BROWN:
                    colorType = Utils.ColorType.BROWN;
                    dateButton.setBackgroundResource(R.drawable.drawable_mood_color_brown);
                    break;
            }
        }


        if(isTough) {
            tagSpinner.setSelection(0);
        }
        if(isLong) {
            tagSpinner.setSelection(1);
        }
        if(isFaith) {
            tagSpinner.setSelection(2);
        }
        if(isRemindMe) {
            reminderCheckbox.setChecked(true);
        }

        //Resume Date and times.
        Boolean isEventConfigurationButtonSet = sharedPreferences.getBoolean(KooloApplication.EVENT_BUTTON_CONFIGURATION, false);
        if(isEventConfigurationButtonSet) {
            eventDate = sharedPreferences.getString(OnButtonClickedListener.DATE, null);
            eventType = sharedPreferences.getString(OnButtonClickedListener.BUTTON_CLICKED_ACTION, null);
            eventTime = sharedPreferences.getString(OnButtonClickedListener.TIME, null);
            dateButton.setText(sharedPreferences.getString(OnButtonClickedListener.SUBDATE, null) + "\n" + sharedPreferences.getString(OnButtonClickedListener.DAY, null));
            monthText.setText(sharedPreferences.getString(OnButtonClickedListener.MONTH, null));
            eventTypeTextView.setText(eventType);
            eventTagButton.setText("Kl."+eventTime);
        }

        Boolean isRemindMe = sharedPreferences.getBoolean(OnButtonClickedListener.REMINDME, false);
        reminderCheckbox.setChecked(isRemindMe);
    }


    private void addCalendarEvent() {
        Bundle bundle=new Bundle();


        SharedPreferences dateSelectionPreferences=mContext.getSharedPreferences(KooloApplication.DATE_BUTTON_CONFIGURATION, mContext.MODE_PRIVATE);
        Boolean isEventConfigurationButtonSet = dateSelectionPreferences.getBoolean(KooloApplication.EVENT_BUTTON_CONFIGURATION, false);
        if(isEventConfigurationButtonSet) {
            eventDate = dateSelectionPreferences.getString(OnButtonClickedListener.DATE, null);
            eventType = dateSelectionPreferences.getString(OnButtonClickedListener.BUTTON_CLICKED_ACTION, null);
            eventTime = dateSelectionPreferences.getString(OnButtonClickedListener.TIME, null);
        }

        eventText = eventEditText.getText().toString();

        List<Integer> tags = tagSpinner.getSelectedIndices();

        for(Integer tag : tags){
            switch(tag) {
                case 0:
                    isTough = true;
                    break;
                case 1:
                    isLong = true;
                    break;
                case 2:
                    isFaith = true;
                    break;
            }
        }

        if(canProceed()) {
            CalendarEvents newCalendarEvent = new CalendarEvents(eventText,eventDate,eventTime,eventType,isTough,isLong,isFaith,reminderCheckbox.isChecked(),colorType);
            if(addCalendarEventsToDataStore(newCalendarEvent)) {
                Toast.makeText(mContext,"New Calendar Event Added", Toast.LENGTH_LONG).show();
                bundle.putInt(KooloCalendarInteractionListener.KOOLO_CALENDAR_ACTION, KooloCalendarInteractionListener.CALENDAR_EVENT_DONE_ACTION);
            } else {
                Toast.makeText(mContext,"Calendar Event Add Failed !!!", Toast.LENGTH_LONG).show();
                bundle.putInt(KooloCalendarInteractionListener.KOOLO_CALENDAR_ACTION, KooloCalendarInteractionListener.CALENDAR_EVENT_CANCEL_ACTION);
            }
            mListener.onCalendarInteraction(bundle);

        }

    }

    @Override
    public void onButtonClicked(Bundle buttonBundle) {

        eventDate = buttonBundle.getString(OnButtonClickedListener.DATE, null);
        eventType = buttonBundle.getString(OnButtonClickedListener.BUTTON_CLICKED_ACTION, null);
        eventTime = buttonBundle.getString(OnButtonClickedListener.TIME, null);

        eventTagButton.setText("Kl."+eventTime);
        eventTypeTextView.setText(eventType);

        String[] dateComponents = DateAndTimeUtility.getSharedDateAndTimeUtility(mContext).getRefactoredDateFromString(eventDate);
        dateButton.setText(dateComponents[0]+"\n"+dateComponents[1]);
        monthText.setText(dateComponents[2]);

        //Store in shared Preferences.
        SharedPreferences configurationSharedPreferences=mContext.getSharedPreferences(KooloApplication.DATE_BUTTON_CONFIGURATION, mContext.MODE_PRIVATE);
        SharedPreferences.Editor configurationEditor=configurationSharedPreferences.edit();
        configurationEditor.putBoolean(KooloApplication.EVENT_BUTTON_CONFIGURATION, true);
        configurationEditor.putString(OnButtonClickedListener.BUTTON_CLICKED_ACTION, eventType);
        configurationEditor.putString(OnButtonClickedListener.DATE, eventDate);
        configurationEditor.putString(OnButtonClickedListener.TIME, eventTime);
        configurationEditor.putString(OnButtonClickedListener.MONTH, dateComponents[2]);
        configurationEditor.putString(OnButtonClickedListener.SUBDATE, dateComponents[0]);
        configurationEditor.putString(OnButtonClickedListener.DAY, dateComponents[1]);
        configurationEditor.commit();

        Toast.makeText(mContext,"Date - "+eventDate+"\nTIME-"+eventTime+"\nType -" + eventType, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }

    //TODO:
    private boolean addCalendarEventsToDataStore(CalendarEvents newCalendarEvent) {
        List<CalendarEvents> eventsList = null;
        boolean writeStatus = false;
        EventsDataStore sharedEventsDataStore;
        if(eventsList == null) {
            sharedEventsDataStore = EventsDataStore.getSharedEventsDataStore(mContext.getResources().getString(R.string.events_file_name), mContext);
            //First Get transfers from file.
            eventsList = sharedEventsDataStore.readEventsFromFile();
            if(eventsList == null) {
                eventsList = new ArrayList<CalendarEvents>();
            }
            eventsList.add(newCalendarEvent);

            writeStatus = sharedEventsDataStore.writeEventsToFile(eventsList);
        }
        return writeStatus;
    }

    private boolean canProceed(){
        boolean status = false;
        if(eventText != null && eventDate!= null && eventTime!= null && eventType != null) {
            status = true;
        }
        return status;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDoneAction(v.getText().toString());
        }
        return false;
    }

    private void onDoneAction(String itemText) {
        SharedPreferences configurationSharedPreferences=mContext.getSharedPreferences(KooloApplication.DATE_BUTTON_CONFIGURATION, mContext.MODE_PRIVATE);
        SharedPreferences.Editor configurationEditor=configurationSharedPreferences.edit();
        configurationEditor.putString(OnButtonClickedListener.EVENTNAME, itemText);
        configurationEditor.commit();

        eventText = itemText;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        SharedPreferences configurationSharedPreferences=mContext.getSharedPreferences(KooloApplication.DATE_BUTTON_CONFIGURATION, mContext.MODE_PRIVATE);
        SharedPreferences.Editor configurationEditor=configurationSharedPreferences.edit();
        configurationEditor.putBoolean(OnButtonClickedListener.REMINDME, isChecked);
        configurationEditor.commit();
        isRemindMe = isChecked;
    }
}
