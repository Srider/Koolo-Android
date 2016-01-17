package com.example.srravela.koolo.calendar.utils;

import android.content.Context;
import android.util.Log;

import com.example.srravela.koolo.entities.CalendarDates;
import com.example.srravela.koolo.entities.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by srikar on 16/01/16.
 */
public class DateAndTimeUtility {

    private static final String TAG = DateAndTimeUtility.class.getSimpleName();
    private static Context mContext;
    public static DateAndTimeUtility sharedDateAndTimeUtility = null;

    /**
     * Constructor used for initializing the QuotesDataStore.
     */
    private DateAndTimeUtility() {
        super();
    }

    /**
     * Static method used for instantiating the QuotesDataStore using constructor.
     * @return GoalsDataStore
     */
    public static DateAndTimeUtility getSharedDateAndTimeUtility(Context context) {
        if(sharedDateAndTimeUtility == null) {
            sharedDateAndTimeUtility = new DateAndTimeUtility();
            mContext = context;
        }
        return sharedDateAndTimeUtility;
    }

    public static HashMap<String, List<CalendarDates>> getAllFormattedDates() {

        HashMap<String, List<CalendarDates>> calendarDatesMap = new HashMap<String, List<CalendarDates>>();

        for(int i = 0, count = 0; i<4; i++) {
            List<CalendarDates> datesSlot =  new ArrayList<CalendarDates>();
            for(int j=0; j<9; j++) {
                datesSlot.add(getFutureDatesByAddingOffset(count));
                count+=1;
            }
            calendarDatesMap.put(""+i, datesSlot);
        }
        return calendarDatesMap;
    }

    public static CalendarDates getFutureDatesByAddingOffset(int offset) {

        CalendarDates newCalendarDates =  null;
        String[] newDatecomponents = null;

        long futureDateSeconds = System.currentTimeMillis() + ((long)offset) *24*60*60*1000;
        Date futureDate = new Date();
        futureDate.setTime(futureDateSeconds);

        newDatecomponents = futureDate.toString().split(" ");
        newCalendarDates = new CalendarDates(newDatecomponents[2], newDatecomponents[0], newDatecomponents[1], Utils.ColorType.DARK_GREY);

        return newCalendarDates;
    }

    public String[] getDateComponents() {
        String currentDayString = null;
        String[] currentDateComponents = null;

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        currentDateComponents = d.toString().split(" ");
        for(String component : currentDateComponents ) {
            Log.d(TAG, "Day String:" + component);
        }
        return currentDateComponents;
    }

    public String[] getRefactoredDateFromString(String eventDate) {
        String month = null;
        String day = null;
        String date = null;
        String[] refactoredComponents = new String[3];
        String[] components = eventDate.split("-");
        refactoredComponents[0] = components[0];
        refactoredComponents[1] = getFormattedDayOfWeekForDate(eventDate);

        Log.d(TAG, components[0]+ components[1]+ components[2]);
        switch(Integer.parseInt(components[2])) {
            case 0:
                refactoredComponents[2] = "Jan";
                break;
            case 1:
        refactoredComponents[2] = "Feb";
                break;
            case 2:
        refactoredComponents[2] = "Mar";
                break;
            case 3:
        refactoredComponents[2] = "Apr";
                break;
            case 4:
        refactoredComponents[2] = "May";
                break;
            case 5:
        refactoredComponents[2] = "Jun";
                break;
            case 6:
        refactoredComponents[2] = "Jul";
                break;
            case 7:
        refactoredComponents[2] = "Aug";
                break;
            case 8:
        refactoredComponents[2] = "Sep";
                break;
            case 9:
        refactoredComponents[2] = "Oct";
                break;
            case 10:
        refactoredComponents[2] = "Nov";
                break;
            case 11:
        refactoredComponents[1] = "Dec";
                break;
        }

        return refactoredComponents;
    }

    private String getFormattedDayOfWeekForDate(String date) {
        Calendar c = Calendar.getInstance();
        String formattedDay = null;
        int dayOfWeek = 0;
        try {
            c.setTime(new SimpleDateFormat("dd-mm-yyyy").parse(date));
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        switch(dayOfWeek) {
            case Calendar.SUNDAY:
                formattedDay = "Sun";
                break;
            case Calendar.SATURDAY:
                formattedDay = "Sat";
                break;
            case Calendar.MONDAY:
                formattedDay = "Mon";
                break;
            case Calendar.TUESDAY:
                formattedDay = "Tue";
                break;
            case Calendar.WEDNESDAY:
                formattedDay = "Wed";
                break;
            case Calendar.THURSDAY:
                formattedDay = "Thu";
                break;
            case Calendar.FRIDAY:
                formattedDay = "Fri";
                break;
        }

        return formattedDay;
    }

}
