package com.example.srravela.koolo.calendar.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.activities.KooloCalendarActivity;
import com.example.srravela.koolo.calendar.adapters.KooloTagSpinnerAdapter;
import com.example.srravela.koolo.calendar.listeners.KooloCalendarInteractionListener;
import com.example.srravela.koolo.calendar.listeners.OnButtonClickedListener;
import com.example.srravela.koolo.calendar.utils.DatePickerDialogPlus;
import com.example.srravela.koolo.checklists.activities.KooloChecklistActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
