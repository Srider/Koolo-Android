package com.example.srravela.koolo.quotes.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.Quotes;

import java.util.List;

/**
 * Created by srravela on 11/17/2015.
 */
public class KooloQuotesAdapter extends BaseAdapter {
    public List<Quotes> quotesList = null;
    public Context mContext;
    LayoutInflater layoutInflater;

    public KooloQuotesAdapter(List<Quotes> quotesList, Context context) {
        super();
        this.quotesList = quotesList;
        this.mContext = context;
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
        return quotesList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= layoutInflater.inflate(R.layout.view_quotes_cell, null);
        Quotes tempQuote = quotesList.get(position);

        TextView quoteTextView = (TextView)convertView.findViewById(R.id.quote_text);
        ImageView tickImageView = (ImageView) convertView.findViewById(R.id.quote_radio_image);
        if(tempQuote.getQuoteText()!= null) {
            quoteTextView.setText(tempQuote.getQuoteText());
        } else {
            quoteTextView.setText("Not Available");
        }
        SharedPreferences configurationSharedPreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_QUOTE_INDEX, mContext.MODE_PRIVATE);
        int selectedQuoteIndex = configurationSharedPreferences.getInt(KooloApplication.SELECTED_QUOTE_INDEX, 0);
        if(selectedQuoteIndex == position) {
            tickImageView.setVisibility(View.VISIBLE);
        }        else {
            tickImageView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}