package com.example.srravela.koolo.quotes.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.UiThread;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.Quotes;
import com.example.srravela.koolo.humor.utils.HumourDataStore;
import com.example.srravela.koolo.quotes.activities.KooloQuotesActivity;
import com.example.srravela.koolo.quotes.adapters.KooloQuotesAdapter;
import com.example.srravela.koolo.quotes.listeners.KooloQuotesInteractionListener;
import com.example.srravela.koolo.quotes.utils.QuotesDataStore;
import com.example.srravela.koolo.settings.adapters.KooloSettingsAdapter;
import com.example.srravela.koolo.settings.listeners.KooloSettingsInteractionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KooloQuotesFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, TextWatcher, EditText.OnEditorActionListener {
    public static final String TAG=KooloQuotesFragment.class.getSimpleName();
    private Context mContext;
    private KooloQuotesActivity mActivity;
    private View rootView;
    ListView mQuotesList;
    List<Quotes> mQuotesDetails;
    KooloQuotesAdapter mQuotesAdapter;
    Button logQuoteButton;
    ToggleButton enableQuotesButton;
    EditText quoteEditText;
    private static int previousSelectedIndex = 0;
    private KooloQuotesInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloQuotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloQuotesFragment newInstance() {
        KooloQuotesFragment fragment = new KooloQuotesFragment();
        return fragment;
    }

    public KooloQuotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_koolo_quotes,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity= (KooloQuotesActivity) getActivity();
        mContext=mActivity.getApplicationContext();
        mListener =(KooloQuotesInteractionListener) mActivity;
        setHasOptionsMenu(false);
        intiUI();
    }

    private void intiUI(){
        //Enable Quotes Toggle button
        enableQuotesButton = (ToggleButton) rootView.findViewById(R.id.quotes_toggle_button);
        enableQuotesButton.setOnClickListener(this);

        SharedPreferences enableQuotePreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_HOME_QUOTE, mContext.MODE_PRIVATE);
        if(enableQuotePreferences.getBoolean(KooloApplication.QUOTE_DISPLAY_ENABLED, true)) {
            enableQuotesButton.setChecked(true);
        } else {
            enableQuotesButton.setChecked(false);
        }

        //Hide Log Quote Button.
        logQuoteButton = (Button)rootView.findViewById(R.id.log_quote_button);
        logQuoteButton.setVisibility(View.GONE);
        logQuoteButton.setOnClickListener(this);

        //Get Quote Edit Text.
        quoteEditText = (EditText)rootView.findViewById(R.id.edit_text_quote);
        quoteEditText.addTextChangedListener(this);
        quoteEditText.setOnEditorActionListener(this);

        QuotesDataStore sharedQuotesDataStore = QuotesDataStore.getSharedQuotesDataStore(mContext.getResources().getString(R.string.quotes_file_name), mContext);

        //First Get Quotes from file.
        mQuotesDetails = sharedQuotesDataStore.readQuotesFromFile();
        Quotes defaultQuote;
        //If file is empty
        if(mQuotesDetails == null || mQuotesDetails.isEmpty()) {
            mQuotesDetails = new ArrayList<Quotes>();
            defaultQuote =  new Quotes(getResources().getString(R.string.default_quote_string), true);
            mQuotesDetails.add(0, defaultQuote);
        }
        mQuotesAdapter = new KooloQuotesAdapter(mQuotesDetails, mContext);
        mQuotesList = (ListView)rootView.findViewById(R.id.quotes_list);
        mQuotesList.setOnItemClickListener(this);
        mQuotesList.setAdapter(mQuotesAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SharedPreferences quotePreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_QUOTE_INDEX, mContext.MODE_PRIVATE);
        SharedPreferences.Editor quoteEditor=quotePreferences.edit();
        quoteEditor.putInt(KooloApplication.SELECTED_QUOTE_INDEX,position);
        quoteEditor.commit();

        SharedPreferences homeQuotePreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_HOME_QUOTE, mContext.MODE_PRIVATE);
        SharedPreferences.Editor homeQuoteEditor=homeQuotePreferences.edit();
        String selectedQuoteString = mQuotesDetails.get(position).getQuoteText();
        homeQuoteEditor.putString(KooloApplication.SELECTED_HOME_QUOTE,selectedQuoteString);
        homeQuoteEditor.commit();

        mQuotesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.log_quote_button:
                if(validateQuote(quoteEditText.getText().toString())) {
                    //TODO: Add quote to database with selected to false.
                }
                break;
            case R.id.quotes_toggle_button:
                configureQuotePermission();
                break;
        }
    }

    //TODO: Add Minor Location check.
    private boolean validateQuote(String quoteText){
        boolean canProceed;
        if(quoteText!=null ||!(quoteText.equals(""))){
            canProceed = true;
        }else{
            canProceed=false;
        }
        return canProceed;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.i(TAG, "beforeTextChanged");
        if(logQuoteButton.getVisibility() == View.GONE){
            logQuoteButton.setEnabled(true);
            logQuoteButton.setVisibility(View.VISIBLE);
        }

   }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.i(TAG, "onTextChanged");
    }

    @Override
    public void afterTextChanged(Editable s) {
        Log.i(TAG, "afterTextChanged");
        if(logQuoteButton.getVisibility() == View.VISIBLE){
            logQuoteButton.setEnabled(false);
            logQuoteButton.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDoneAction(v);
        }
        return false;
    }

    private void onDoneAction(TextView editedTextView) {
        String newQuote = editedTextView.getText().toString();

        if(newQuote!= null && !(newQuote.isEmpty())) {
            mQuotesDetails.add(new Quotes(editedTextView.getText().toString(), false));
            QuotesDataStore sharedQuotesDataStore = QuotesDataStore.getSharedQuotesDataStore(mContext.getResources().getString(R.string.quotes_file_name), mContext);
            boolean writeStatus = sharedQuotesDataStore.writeQuotesToFile(mQuotesDetails);
            if(writeStatus) {
                Log.i(TAG, "QUOTES WRITE SUCCESS");
            } else {
                Log.i(TAG, "ERROR - QUOTES WRITE FAILED !!!");
            }
            quoteEditText.setText("");
            mQuotesAdapter.notifyDataSetChanged();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder((KooloQuotesActivity)getActivity()).create();
            alertDialog.setTitle("New Quote Alert");
            alertDialog.setMessage("Quote cannot be empty !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    private void configureQuotePermission() {

        SharedPreferences enableQuotePreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_HOME_QUOTE, mContext.MODE_PRIVATE);
        SharedPreferences.Editor enableQuoteEditor=enableQuotePreferences.edit();

        if(enableQuotesButton.isChecked()) {
            enableQuoteEditor.putBoolean(KooloApplication.QUOTE_DISPLAY_ENABLED, true);
            enableQuoteEditor.commit();
        } else {
            enableQuoteEditor.putBoolean(KooloApplication.QUOTE_DISPLAY_ENABLED, false);
            enableQuoteEditor.commit();
        }
    }


}
