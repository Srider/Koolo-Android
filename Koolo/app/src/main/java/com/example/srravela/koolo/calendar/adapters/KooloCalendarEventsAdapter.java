package com.example.srravela.koolo.calendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.utils.DateAndTimeUtility;
import com.example.srravela.koolo.entities.CalendarDates;
import com.example.srravela.koolo.entities.CalendarEvents;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by srikar on 05/01/16.
 */
public class KooloCalendarEventsAdapter  extends BaseAdapter implements View.OnClickListener{

    private static final String TAG = KooloCalendarEventsAdapter.class.getSimpleName();

    public List<CalendarEvents> items = null;
    LayoutInflater layoutInflater;
    public Context context;
    boolean isHomeFragment = false;


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

    public KooloCalendarEventsAdapter(List<CalendarEvents> items, Context context, boolean isHomeFragment ) {
        super();
        this.items = items;
        this.context = context;
        this.isHomeFragment = isHomeFragment;
        layoutInflater = LayoutInflater.from(context);
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
        convertView= layoutInflater.inflate(R.layout.view_events_cell, null);

        TextView eventHeaderText = (TextView)convertView.findViewById(R.id.event_header_text);

        TextView eventClockText = (TextView) convertView.findViewById(R.id.event_clock_text);

        TextView frequencyText = (TextView) convertView.findViewById(R.id.event_frequency_text);

        TextView toughTextView = (TextView) convertView.findViewById(R.id.tough_tag_text);
        TextView longTextView = (TextView) convertView.findViewById(R.id.long_tag_text);
        TextView faithTextView = (TextView) convertView.findViewById(R.id.faith_tag_text);

        CalendarEvents item = items.get(position);
        String eventHeader = item.getEventName();
        String timeText = item.getEventTime();
        String frequency = item.getEventType();

        //Set Event Header.
        if(eventHeader != null) {
            eventHeaderText.setText(eventHeader);
        }

        //Set Event Time
        if(timeText != null) {
            eventClockText.setText("Kl:"+timeText);
        }

        //Set Frequency Text.
        if(frequency != null) {
            frequencyText.setText(frequency);
        }

        if(!isHomeFragment) {
            //        if(item.isTough()) {
            toughTextView.setVisibility(View.VISIBLE);
//        } else {
//            toughTextView.setVisibility(View.INVISIBLE);
//        }
//
//        if(item.isFaith()) {
            faithTextView.setVisibility(View.VISIBLE);
//        } else {
//            faithTextView.setVisibility(View.INVISIBLE);
//        }
//
//        if(item.isLong()) {
            longTextView.setVisibility(View.VISIBLE);
//        } else {
//            longTextView.setVisibility(View.INVISIBLE);
//        }
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
