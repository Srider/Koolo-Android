package com.example.srravela.koolo.moods.listeners;

import android.os.Bundle;

import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.MoodShot;

/**
 * Created by viswanath on 28/12/2015.
 */
public interface KooloMoodSelectListener {

   /* public static final String KOOLO_MOODS_ACTION = "KOOLO_MOODS_ACTION";
    public static final int MOOD_SHOT_SELECTED = 0;
    public static final int MOODS_LAUNCH_CAMERA_ACTION = 1;
    public static final int MOODS_LAUNCH_GALLERY_ACTION = 2;
    public static final int MOODS_SHOT_FORMATTER = 3;
*/

    /**
     * Method to override for handling any layby enquiry event.
     * @param humour
     */
    public void onMoodSelectListener(Humour humour);
}
