package com.example.srravela.koolo.calendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.listeners.ChecklistItemsUpdatedListener;
import com.example.srravela.koolo.entities.CalendarDates;
import com.example.srravela.koolo.entities.Checklist;
import com.example.srravela.koolo.entities.Utils;

import java.util.List;

/**
 * Created by srikar on 4/1/16.
 */
public class KooloCalendarDatesAdapter extends BaseAdapter implements View.OnClickListener{

        private static final String TAG = KooloCalendarDatesAdapter.class.getSimpleName();

        public List<CalendarDates> items = null;
        LayoutInflater layoutInflater;
        public Context context;


        /**
         * Constructor used for initializing the KooloCalendarDatesAdapter.
         * @param context
         * @param items
         */


    /**
     * Constructor used for initializing the KooloChecklistItemAdapter.
     * @param context
     * @param items
     */

    public KooloCalendarDatesAdapter(List<CalendarDates> items, Context context ) {
        super();
        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setDates(List<CalendarDates> items) {
        this.items = items;
    }

        /**
         * Method that returns the dropdown view.
         * @param position
         * @param convertView
         * @param parent
         * @return itemView
         */
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View itemView = getView(position, convertView, parent);
        return itemView;
    }

    /**
     * Method that returns the item view type.
     * @param position
     * @return position
     */
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * Method that returns the view type count.
     * @return int
     */
    public int getViewTypeCount() {
        return 1;
    }

    /**
     * Method that returns the number of items.
     * @return int
     */
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView= layoutInflater.inflate(R.layout.view_date_cell, null);

        TextView dateView = (TextView)convertView.findViewById(R.id.calendar_date_cell);

        TextView monthText = (TextView)convertView.findViewById(R.id.month_text);

        CalendarDates item = items.get(position);


        if(item.getDayText()!= null && item.getDateText()!=null) {
            dateView.setText(item.getDateText()+"\n"+item.getDayText());
        }

        //TODO: add month here./
        monthText.setText(item.getMonthText()+" "+item.getYearText());

        switch (item.getColorType()) {

        }
        switch (item.getColorType()) {

            case DARK_GREY:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_darkgray);
                break;
            case YELLOW:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_yellow);
                break;
            case BLACK:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_black);
                break;
            case RED:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_red);
                break;
            case GREY:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_gray);
                break;
            case PINK:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_magenta);
                break;
            case ORANGE:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_orange);
                break;
            case BLUE:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_blue);
                break;
            case GREEN:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_theme_green);
                break;
            case BROWN:
                dateView.setBackgroundResource(R.drawable.drawable_mood_color_brown);
                break;
        }
        return convertView;
    }

    /**
     * Method to check whether if the list is empty or not.
     * @return boolean
     */
    public boolean isEmpty() {
        return getCount() == 0;
    }

    void onClick() {

    }

    @Override
    public void onClick(View v) {

    }

}
