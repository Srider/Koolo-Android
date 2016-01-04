package com.example.srravela.koolo.calendar.listeners;

import android.os.Bundle;

/**
 * Created by srikar on 4/1/16.
 */
public interface KooloCalendarInteractionListener {
    public static final String KOOLO_CALENDAR_ACTION="KOOLO_CALENDAR_ACTION";

    public void onCalendarInteraction(Bundle urlBundle);
}
