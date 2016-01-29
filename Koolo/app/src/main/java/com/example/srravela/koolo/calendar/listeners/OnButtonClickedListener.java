package com.example.srravela.koolo.calendar.listeners;

import android.os.Bundle;

/**
 * Created by srikar on 16/01/16.
 */
public interface OnButtonClickedListener {
    public static final String BUTTON_CLICKED_ACTION="BUTTON_CLICKED_ACTION";
    public static final String MONTHLY_BUTTON_CLICKED="Monthly";
    public static final String WEEKLY_BUTTON_CLICKED="Weekly";
    public static final String DAILY_BUTTON_CLICKED="Daily";
    public static final String DATE = "DATE";
    public static final String TIME = "TIME";
    public static final String DAY = "DAY";
    public static final String MONTH = "MONTH";
    public static final String SUBDATE = "SUBDATE";
    public static final String REMINDME = "REMINDME";
    public static final String EVENTNAME= "EVENTNAME";
    public static final String TOUGH_TAG_STATUS= "TOUGH_TAG_STATUS";
    public static final String LONG_TAG_STATUS= "LONG_TAG_STATUS";
    public static final String FAITH_TAG_STATUS= "FAITH_TAG_STATUS";






    public void onButtonClicked(Bundle buttonBundle);
}
