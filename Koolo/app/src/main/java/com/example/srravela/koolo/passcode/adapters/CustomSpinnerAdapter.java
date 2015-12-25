package com.example.srravela.koolo.passcode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.srravela.koolo.R;
import java.util.List;

/**
 * Created by anideshp on 8/20/2015.
 */
public class CustomSpinnerAdapter extends BaseAdapter {

    private static final String TAG=CustomSpinnerAdapter.class.getSimpleName();
    private Context mContext;

    public List<String> securityQuestionsList;

    /**
     * Constructor for initializing the CustomSpinnerAdapter
     * @param context Context passed from the invoking component.
     * @param securityQuestionsList Contains the list of major locations.
     */
    public CustomSpinnerAdapter(Context context, List<String> securityQuestionsList){
        this.mContext =context;
        this.securityQuestionsList=securityQuestionsList;
    }


    @Override
    public int getCount() {
        return securityQuestionsList.size();
    }

    @Override
    public Object getItem(int position) {
        return securityQuestionsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        AdapterHolder adapterHolder;
        if(view==null){
            adapterHolder=new AdapterHolder();
            LayoutInflater layoutInflater= LayoutInflater.from(mContext);
            view=layoutInflater.inflate(R.layout.view_security_question_spinner_cell,null);
            adapterHolder.textViewString=(TextView)view.findViewById(R.id.spinner_security_question_text);
            view.setTag(adapterHolder);
        }else{
            adapterHolder=(AdapterHolder)view.getTag();
        }
        adapterHolder.textViewString.setText((CharSequence) securityQuestionsList.get(position));

        return view;
    }

    /**
     * AdapterHolder
     */
    private class AdapterHolder{
        public TextView textViewString;
    }
}
