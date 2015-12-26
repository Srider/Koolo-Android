package com.example.srravela.koolo.humor.adapters;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.humor.listeners.HumourTextUpdatedListener;

import java.util.List;

/**
 * Created by srravela on 11/16/2015.
 */
public class KooloHumourColorAdapter  extends BaseAdapter implements EditText.OnEditorActionListener {

    public List<Humour> items = null;
    LayoutInflater layoutInflater;
    public Context context;
    HumourTextUpdatedListener mHumourTextListener;

    /**
     * Constructor used for initializing the KooloChecklistItemAdapter.
     * @param context
     * @param items
     */
    public KooloHumourColorAdapter(List<Humour> items, Context context, HumourTextUpdatedListener humourTextListener ) {
        super();
        this.items = items;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.mHumourTextListener = humourTextListener;
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
        return position;
    }

    /**
     * Method that returns the view type count.
     * @return int
     */
    public int getViewTypeCount() {
        return items.size();
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
        convertView= layoutInflater.inflate(R.layout.view_humour_color, null);
        Humour item = items.get(position);
        String humourItemText = item.getHumourText();

        EditText humourText = (EditText)convertView.findViewById(R.id.humour_text);
        humourText.setOnEditorActionListener(this);
        humourText.setTag(""+position);
        if(humourText!= null) {
            humourText.setText(humourItemText);
        } else {
            humourText.setText("Unknown");
        }

        //Set Parcel Major Location.
        View hunmourColorView = (View)convertView.findViewById(R.id.color_view);
        Utils.ColorType colorType = item.getColorType();
        switch (colorType) {
            case RED:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_red);
                break;
            case GREEN:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_theme_green);
                break;
            case YELLOW:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_yellow);
                break;
            case ORANGE:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_orange);
                break;
            case GREY:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_grey);
                break;
            case BLACK:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_black);
                break;
            case BROWN:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_brown);
                break;
            case PINK:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_pink);
                break;
            case DARK_GREY:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_darkgrey);
                break;
            case BLUE:
                hunmourColorView.setBackgroundResource(R.drawable.drawable_color_round_blue);
                break;

            default:
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDoneAction(v);
        }
        return false;
    }

    private void onDoneAction(TextView editedTextView) {
        int editTextTag = Integer.parseInt(editedTextView.getTag().toString());
        Humour editedHumour = items.remove(editTextTag);
        editedHumour.setHumourText(editedTextView.getText().toString());
        items.add(editTextTag, editedHumour);
        mHumourTextListener.onHumourTextUpdated(items);
    }
}
