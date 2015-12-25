package com.example.srravela.koolo.home.listeners;

import android.os.Bundle;

/**
 * Created by srravela on 11/10/2015.
 */
public interface KooloHomeInteractionListener {
    public static final String KOOLO_HOME_ACTION="KOOLO_HOME_ACTION";
    public static final int KOOLO_LOCK_BUTTON_CLICKED=0;
    public static final int KOOLO_SETTINGS_BUTTON_CLICKED=1;
    public static final int KOOLO_CALENDAR_DATE_BUTTON_CLICKED=2;
    public static final int KOOLO_CALENDAR_DATE_CONFIGURATION_CHANGED=3;
    public static final int KOOLO_POPUP_CALENDAR_BUTTON_CLICKED = 4;
    public static final int KOOLO_POPUP_CHECKLIST_BUTTON_CLICKED =5;
    public static final int KOOLO_POPUP_CAMERA_BUTTON_CLICKED=6;

    public void onHomeInteraction(Bundle urlBundle);
}
