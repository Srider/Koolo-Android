package com.example.srravela.koolo.entities;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by srikar on 5/12/15.
 * Updated by tippi on 22/12/15.
 */
public class MoodShot implements Serializable {

   /* private Uri moodCaptureUri;
    private Utils.ColorType colorType;
    public MoodShot(Uri moodCaptureUri, Utils.ColorType colorType, String moodCaptureDate ) {

        this.moodCaptureUri = moodCaptureUri;
        this.colorType = colorType;
        this.moodCaptureDate = moodCaptureDate;
    }
    public Uri getMoodCaptureUri() {
        return moodCaptureUri;
    }

    public void setMoodCaptureUri(Uri moodCaptureUri) {
        this.moodCaptureUri = moodCaptureUri;
    }

    public Utils.ColorType getColorType() {
        return colorType;
    }

    public void setColorType(Utils.ColorType colorType) {
        this.colorType = colorType;
    }*/



    private int moodShotId;
    private String moodColor;
    private String moodCaptureDate;
    private String moodCaptureUri;

    public MoodShot( ) {
    }

    public MoodShot(String moodColor, String moodCaptureDate, String moodCaptureUri ) {
        this.moodColor = moodColor;
        this.moodCaptureDate = moodCaptureDate;
        this.moodCaptureUri = moodCaptureUri;
    }

    public int getMoodShotId() {
        return moodShotId;
    }

    public void setMoodShotId(int moodShotId) {
        this.moodShotId = moodShotId;
    }

    public String getMoodColor() {
        return moodColor;
    }

    public void setMoodColor(String moodColor) {
        this.moodColor = moodColor;
    }

    public String getMoodCaptureDate() {
        return moodCaptureDate;
    }

    public void setMoodCaptureDate(String moodCaptureDate) {
        this.moodCaptureDate = moodCaptureDate;
    }

    public String getMoodCaptureUri() {
        return moodCaptureUri;
    }

    public void setMoodCaptureUri(String moodCaptureUri) {
        this.moodCaptureUri = moodCaptureUri;
    }

}
