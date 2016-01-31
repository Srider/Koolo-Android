package com.example.srravela.koolo.calendar.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.example.srravela.koolo.calendar.activities.KooloCalendarActivity;
import com.example.srravela.koolo.calendar.listeners.OnButtonClickedListener;
import com.example.srravela.koolo.calendar.utils.DatePickerDialogPlus;

public class KooloDateSetterFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener onDateSet;
    OnButtonClickedListener onButtonSet;
    private int year, month, day;
    Context mContext;
    KooloCalendarActivity mActivity;

    public KooloDateSetterFragment() {
        mActivity = (KooloCalendarActivity) getActivity();
        mContext = (KooloCalendarActivity)getActivity();
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener ondate) {
        onDateSet = ondate;
    }

    public void setOnButtonClickListener(OnButtonClickedListener onButtonSelected) {
        onButtonSet = onButtonSelected;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        DatePickerDialogPlus datePicker =   new DatePickerDialogPlus(getActivity(), onDateSet, onButtonSet, year, month, day);
        return datePicker;

    }
}
