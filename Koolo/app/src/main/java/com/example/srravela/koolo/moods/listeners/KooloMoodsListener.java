package com.example.srravela.koolo.moods.listeners;

import android.os.Bundle;

/**
 * Created by srravela on 11/16/2015.
 */
public interface KooloMoodsListener {

    public static final String KOOLO_MOODS_ACTION = "KOOLO_MOODS_ACTION";
    public static final int MOOD_SHOT_SELECTED = 0;
    public static final int MOODS_LAUNCH_CAMERA_ACTION = 1;
    public static final int MOODS_LAUNCH_GALLERY_ACTION = 2;
    public static final int MOODS_SHOT_FORMATTER = 3;


    /**
     * Method to override for handling any layby enquiry event.
     * @param bundle
     */
    public void onMoodsAction(Bundle bundle);
}
