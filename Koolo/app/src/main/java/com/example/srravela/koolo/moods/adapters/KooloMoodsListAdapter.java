package com.example.srravela.koolo.moods.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.moods.listeners.KooloMoodsListener;
import com.example.srravela.koolo.moods.utils.MoodsDataStore;
import com.example.srravela.koolo.entities.MoodShot;
import com.example.srravela.koolo.entities.Utils;
import java.util.List;

/**
 * Created by srikar on 5/12/15.
 */
public class KooloMoodsListAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = KooloMoodsListAdapter.class.getSimpleName();
    public List<MoodShot> items = null;
    LayoutInflater layoutInflater;
    public Context context;
    private KooloMoodsListener mListener;


    /**
     * Constructor used for initializing the KooloChecklistItemAdapter.
     * @param context
     * @param items
     */
    public KooloMoodsListAdapter(List<MoodShot> items, Context context, KooloMoodsListener updateListener ) {
        super();
        this.items = items;
        this.context = context;
        this.mListener = updateListener;
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



        ViewHolder holder;
        if (convertView == null) {
            convertView= layoutInflater.inflate(R.layout.view_moodshot_item, null);
            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.moodShotImageView = (ImageView)convertView.findViewById(R.id.moodshot_image);
            holder.moodShotTextView = (TextView)convertView.findViewById(R.id.moodshot_date_tv);
            holder.moodShotColorButton = (Button) convertView.findViewById(R.id.moodshot_color_button);
           holder.moodShotColorButton.setTag(position);
            convertView.setTag(holder);
            holder.moodShotImageView.setOnClickListener(this);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.


            holder = (ViewHolder) convertView.getTag();

        }
        MoodShot item = items.get(position);
       // Uri moodShotUri = Uri.parse(item.getMoodCaptureUri());
        if(item.getMoodCaptureUri()!= null) {
            holder.moodShotImageView.setImageURI(Uri.parse(item.getMoodCaptureUri()));
        }
        holder.moodShotTextView.setText(item.getMoodCaptureDate());

        holder.moodShotColorButton.setOnClickListener(this);
        Utils.ColorType colorType =  Utils.ColorType.valueOf(item.getMoodColor());
        //Utils.ColorType colorType = item.getMoodColor();
        switch (colorType) {
            case RED:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_red);
                break;
            case GREEN:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_theme_green);
                break;
            case YELLOW:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_yellow);
                break;
            case ORANGE:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_orange);
                break;
            case GREY:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_grey);
                break;
            case BLACK:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_black);
                break;
            case BROWN:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_brown);
                break;
            case PINK:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_pink);
                break;
            case DARK_GREY:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_darkgrey);
                break;
            case BLUE:
                holder.moodShotColorButton.setBackgroundResource(R.drawable.drawable_color_round_blue);
                break;

            default:
                break;
        }

        return convertView;
    }
    static class ViewHolder {
        Button moodShotColorButton;
        TextView moodShotTextView;
        ImageView moodShotImageView;
    }
    /**
     * Method to check whether if the list is empty or not.
     * @return boolean
     */
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        MoodShot selectedMoodShot = items.get(position.intValue());
        Bundle bundle = new Bundle();
        bundle.putInt(KooloMoodsListener.KOOLO_MOODS_ACTION, KooloMoodsListener.MOOD_SHOT_SELECTED);
        mListener.onMoodsAction(bundle);
    }
}
