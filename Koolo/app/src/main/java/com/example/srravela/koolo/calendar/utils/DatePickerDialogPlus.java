package com.example.srravela.koolo.calendar.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.listeners.OnButtonClickedListener;

/**
 * Created by srikar on 16/01/16.
 */
public class DatePickerDialogPlus extends DatePickerDialog {

    private final DatePicker mDatePicker;
    private final TimePicker mTimePicker;
    private final OnDateSetListener mCallBack;
    private final OnButtonClickedListener mButtonCallBack;


    Button dailyButton, monthlyButton, weeklybutton;

    private static final String TAG = DatePickerDialogPlus.class.getSimpleName();

    /**
     * @param context The context the dialog is to run in.
     * @param callBack How the parent is notified that the date is set.
     * @param year The initial year of the dialog.
     * @param monthOfYear The initial month of the dialog.
     * @param dayOfMonth The initial day of the dialog.
     */
    public DatePickerDialogPlus(Context context, OnDateSetListener callBack, OnButtonClickedListener buttonCallback, int year, int monthOfYear, int dayOfMonth) {
        super(context, 0, callBack, year, monthOfYear, dayOfMonth);


        mCallBack = callBack;
        mButtonCallBack = buttonCallback;

        Context themeContext = getContext();

        LayoutInflater inflater = (LayoutInflater)
                themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_date_picker_dialog, null);

        setButton(DatePickerDialog.BUTTON_POSITIVE, "Daily", this);
        setButton(DatePickerDialog.BUTTON_NEUTRAL, "Weekly", this);
        setButton(DatePickerDialog.BUTTON_NEGATIVE, "Monthly", this);

        setView(view);

        mDatePicker = (DatePicker) view.findViewById(R.id.date_picker);
        mDatePicker.init(year, monthOfYear, dayOfMonth, this);

        mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);

    }

    @Override
    public void onClick(final DialogInterface dialog, final int which) {
        // Prevent calling onDateSet handler when clicking to dialog buttons other, then "OK"
        super.onClick(dialog, which);
        Bundle buttonBundle = new Bundle();
        if (which == DialogInterface.BUTTON_POSITIVE) {
            Log.d(TAG, "Daily clicked");
            buttonBundle.putSerializable(OnButtonClickedListener.BUTTON_CLICKED_ACTION, OnButtonClickedListener.DAILY_BUTTON_CLICKED);
        } else if(which == DialogInterface.BUTTON_NEGATIVE) {
            Log.d(TAG, "Monthly clicked");
            buttonBundle.putSerializable(OnButtonClickedListener.BUTTON_CLICKED_ACTION, OnButtonClickedListener.MONTHLY_BUTTON_CLICKED);
        } else if(which == DialogInterface.BUTTON_NEUTRAL) {
            Log.d(TAG, "Weekly clicked");
            buttonBundle.putSerializable(OnButtonClickedListener.BUTTON_CLICKED_ACTION, OnButtonClickedListener.WEEKLY_BUTTON_CLICKED);
        }

        String date = mDatePicker.getDayOfMonth()+"-"+mDatePicker.getMonth()+"-"+mDatePicker.getYear();
        String time = ""+mTimePicker.getCurrentHour()+":"+mTimePicker.getCurrentMinute();
        buttonBundle.putString(OnButtonClickedListener.DATE, date);
        buttonBundle.putString(OnButtonClickedListener.TIME, time);
        mButtonCallBack.onButtonClicked(buttonBundle);
        dismiss();
    }

}
