package com.example.srravela.koolo.settings.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.settings.listeners.KooloSettingsInteractionListener;

import java.util.List;

/**
 * Created by srravela on 11/10/2015.
 */
public class KooloSettingsAdapter extends BaseAdapter{
    public List<String> settingsListArray = null;
    public Context context;
    LayoutInflater layoutInflater;

    public KooloSettingsAdapter(List<String> settingsListArray, Context context) {
        super();
        this.settingsListArray = settingsListArray;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View itemView = getView(position, convertView, parent);
        return itemView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        return settingsListArray.size();
    }

    @Override
    public Object getItem(int position) {
        return settingsListArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= layoutInflater.inflate(R.layout.view_settings_types, null);

        String settingsType = settingsListArray.get(position);
        TextView settingsTypeTextView = (TextView)convertView.findViewById(R.id.settings_type_text);
        settingsTypeTextView.setText(settingsType);

        return convertView;
    }

    /**
     * Method to check whether if the list is empty or not.
     * @return boolean
     */
    public boolean isEmpty() {
        return getCount() == 0;
    }
}
