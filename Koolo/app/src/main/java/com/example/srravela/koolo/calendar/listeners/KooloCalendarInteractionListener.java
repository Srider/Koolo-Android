package com.example.srravela.koolo.calendar.listeners;

import android.os.Bundle;

import com.example.srravela.koolo.entities.Utils;

/**
 * Created by srikar on 4/1/16.
 */
public interface KooloCalendarInteractionListener {
    public static final String KOOLO_CALENDAR_ACTION="KOOLO_CALENDAR_ACTION";
    public static final String KOOLO_BORDER_CONFIGURATION_LISTENER = "KOOLO_BORDER_CONFIGURATION_LISTENER";
    public static final int KOOLO_ADD_CALENDAR_EVENT_ACTION=0;
    public static final int KOOLO_DATE_PICKER_ACTION=1;
    public static final int KOOLO_LOAD_BORDER_CONFIGURATION_ACTION=2;
    public static final int KOOLO_BORDER_CONFIGURATION_SET_ACTION=3;
    public static final int CALENDAR_EVENT_DONE_ACTION = 4;
    public static final int CALENDAR_EVENT_CANCEL_ACTION = 5;

    public void onCalendarInteraction(Bundle urlBundle);

}
