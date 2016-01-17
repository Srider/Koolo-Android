package com.example.srravela.koolo.calendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.CalendarEvents;

import java.util.List;

/**
 * Created by srikar on 16/01/16.
 */
public class KooloTagSpinnerAdapter  extends BaseAdapter{


    private static final String TAG = KooloTagSpinnerAdapter.class.getSimpleName();

    public List<String> tags = null;
    LayoutInflater layoutInflater;
    public Context context;

    /**
     * Constructor used for initializing the KooloChecklistItemAdapter.
     * @param context
     * @param tags
     */

    public KooloTagSpinnerAdapter(List<String> tags, Context context ) {
        super();
        this.tags = tags;
        this.context = context;
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
        return tags.size();
    }

    @Override
    public Object getItem(int position) {
        return tags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= layoutInflater.inflate(R.layout.view_spinner_tag_item, null);

        TextView tagView = (TextView)convertView.findViewById(R.id.tag_text);
        tagView.setText(tags.get(position));
        ImageView tickImage = (ImageView)convertView.findViewById(R.id.tag_tick_image);

        //TODO: add month here./


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
}
