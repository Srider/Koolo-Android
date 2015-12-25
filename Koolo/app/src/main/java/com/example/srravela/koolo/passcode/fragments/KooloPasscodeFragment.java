package com.example.srravela.koolo.passcode.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.Quotes;
import com.example.srravela.koolo.home.listeners.KooloHomeInteractionListener;
import com.example.srravela.koolo.passcode.adapters.CustomSpinnerAdapter;
import com.example.srravela.koolo.passcode.listeners.KooloPasscodeInteractionListener;
import com.example.srravela.koolo.quotes.adapters.KooloQuotesAdapter;
import com.example.srravela.koolo.quotes.listeners.KooloQuotesInteractionListener;
import com.example.srravela.koolo.quotes.utils.QuotesDataStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KooloPasscodeFragment extends Fragment implements AdapterView.OnItemSelectedListener, TextWatcher, View.OnClickListener, EditText.OnEditorActionListener {

    public static final String TAG=KooloPasscodeFragment.class.getSimpleName();
    private Context mContext;
    private Activity mActivity;
    private View rootView;
    public static String selectedSecurityQuestion;
    private String securityQuestionAnswer;
    Spinner securityQuestionSpinner;
    ToggleButton enablePasscodeButton;
    TextView enablePasscodeTextView;
    Button enterPasscodeButton;
    ImageView securityQuestionSelectedImage;
    EditText passcodeAnswerEditText;
    List <String>mSecurityQuestionsList;
    private KooloPasscodeInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloPasscodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloPasscodeFragment newInstance() {
        KooloPasscodeFragment fragment = new KooloPasscodeFragment();
        return fragment;
    }

    public KooloPasscodeFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_passcode,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=getActivity();
        mContext=mActivity.getApplicationContext();
        mListener =(KooloPasscodeInteractionListener) mActivity;
        intiUI();
    }

    private void intiUI(){

        //Get Passcode Toggle Button
        enablePasscodeButton = (ToggleButton) rootView.findViewById(R.id.passkode_toggle_button);
        enablePasscodeButton.setOnClickListener(this);

        SharedPreferences enableQuotePreferences=mContext.getSharedPreferences(KooloApplication.PASSCODE_ENABLED, mContext.MODE_PRIVATE);
        if(enableQuotePreferences.getBoolean(KooloApplication.PASSCODE_ENABLED, false)) {
            enablePasscodeButton.setChecked(true);
        } else {
            enablePasscodeButton.setChecked(false);
        }

        //Get Enter Passcode Button
        enterPasscodeButton = (Button) rootView.findViewById(R.id.enter_passkode_button);
        enterPasscodeButton.setOnClickListener(this);

        //Get Security Questions Array.
        String[] stringArray = (String[])getResources().getStringArray(R.array.koolo_passkode_security_questions);
        mSecurityQuestionsList = new ArrayList<String>(Arrays.asList(stringArray));

        //Configure Spinner and Adapter..
        securityQuestionSpinner=(Spinner)rootView.findViewById(R.id.security_question_spinner);
        CustomSpinnerAdapter majorLocations=new CustomSpinnerAdapter(mContext,mSecurityQuestionsList);
        securityQuestionSpinner.setAdapter(majorLocations);
        securityQuestionSpinner.setOnItemSelectedListener(this);

        //Security Answer Edit Text.
        passcodeAnswerEditText=(EditText)rootView.findViewById(R.id.security_answer_edit_text);
        passcodeAnswerEditText.addTextChangedListener(this);
        passcodeAnswerEditText.setOnEditorActionListener(this);

        securityQuestionSelectedImage = (ImageView) rootView.findViewById(R.id.status_imageview);

        //Security Question Enabled.
        SharedPreferences securityQuestionSharedPreferences=mContext.getSharedPreferences(KooloApplication.SECURITY_QUESTION, mContext.MODE_PRIVATE);

        Boolean isSecurityQuestionEnabled = securityQuestionSharedPreferences.getBoolean(KooloApplication.SECURITY_QUESTION_ENABLED, false);
        String selectedSecurityQuestion = securityQuestionSharedPreferences.getString(KooloApplication.SELECTED_SECURITY_QUESTION, mSecurityQuestionsList.get(0));
        String securityQuestionAnswer = securityQuestionSharedPreferences.getString(KooloApplication.SECURITY_QUESTION_ANSWER, "Not Available");

        if(isSecurityQuestionEnabled) {
            securityQuestionSelectedImage.setVisibility(View.VISIBLE); //Show image
            securityQuestionSpinner.setSelection(mSecurityQuestionsList.indexOf(selectedSecurityQuestion));
            passcodeAnswerEditText.setText(securityQuestionAnswer);
            passcodeAnswerEditText.setVisibility(View.VISIBLE);
        } else {
            securityQuestionSelectedImage.setVisibility(View.INVISIBLE); // hide Image
            passcodeAnswerEditText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedSecurityQuestion = mSecurityQuestionsList.get(position);
        passcodeAnswerEditText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        SharedPreferences securityQuestionSharedPreferences=mContext.getSharedPreferences(KooloApplication.SECURITY_QUESTION, mContext.MODE_PRIVATE);
        SharedPreferences.Editor enableSecurityQuestionEditor=securityQuestionSharedPreferences.edit();

        if(!securityQuestionSharedPreferences.getBoolean(KooloApplication.SECURITY_QUESTION_ENABLED, false)) {
            enableSecurityQuestionEditor.putBoolean(KooloApplication.SECURITY_QUESTION_ENABLED, true);
        }
        enableSecurityQuestionEditor.putString(KooloApplication.SELECTED_SECURITY_QUESTION, selectedSecurityQuestion);
        enableSecurityQuestionEditor.commit();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.enter_passkode_button:
                loadEnterPasscodeFragment();
                break;
            case R.id.passkode_toggle_button:
                configurePasscodePermission();
                break;
        }
    }

    void loadEnterPasscodeFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloPasscodeInteractionListener.KOOLO_PASSCODE_ACTION, KooloPasscodeInteractionListener.KOOLO_ENTER_PASSCODE_BUTTON_CLICKED_ACTION);
        mListener.onPasscodeInteraction(bundle);
    }


    private void onDoneAction(TextView editedTextView) {
        securityQuestionAnswer = passcodeAnswerEditText.getText().toString();
        if(securityQuestionAnswer != null) {
            SharedPreferences securityQuestionSharedPreferences=mContext.getSharedPreferences(KooloApplication.SECURITY_QUESTION, mContext.MODE_PRIVATE);
            SharedPreferences.Editor enableSecurityQuestionEditor=securityQuestionSharedPreferences.edit();
            enableSecurityQuestionEditor.putString(KooloApplication.SECURITY_QUESTION_ANSWER, securityQuestionAnswer);
            enableSecurityQuestionEditor.commit();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDoneAction(v);
        }
        return false;
    }

    private void configurePasscodePermission() {

        SharedPreferences enablePasscodePreferences=mContext.getSharedPreferences(KooloApplication.PASSCODE_ENABLED, mContext.MODE_PRIVATE);
        SharedPreferences.Editor enablePasscodeEditor=enablePasscodePreferences.edit();

        if(enablePasscodeButton.isChecked()) {
            enablePasscodeEditor.putBoolean(KooloApplication.PASSCODE_ENABLED, true);
            enablePasscodeEditor.commit();
        } else {
            enablePasscodeEditor.putBoolean(KooloApplication.PASSCODE_ENABLED, false);
            enablePasscodeEditor.commit();
        }
    }
}
