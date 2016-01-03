package com.example.srravela.koolo.settings.listeners;

import android.os.Bundle;

/**
 * Created by srravela on 11/10/2015.
 */
public interface KooloSettingsInteractionListener {
    public static final String KOOLO_SETTINGS_ACTION="KOOLO_SETTINGS_ACTION";
    public static final int KOOLO_BACKGROUND_IMAGE_ACTION=0;
    public static final int KOOLO_PASSCODE_ACTION=1;
    public static final int KOOLO_QUOTES_ACTION=2;
    public static final int KOOLO_LICENSE_ACTION=3;
    public static final int KOOLO_HUMOUR_COLORS_ACTION=4;
    public static final int KOOLO_TUTORIAL_ACTION=5;
    public static final int KOOLO_UPDATES_ACTION=6;
    public static final int KOOLO_CONTRIBUTORS_ACTION=7;
    public static final int KOOLO_REVIEW_ACTION = 8;
    public void onSettingsInteraction(Bundle urlBundle);
}
