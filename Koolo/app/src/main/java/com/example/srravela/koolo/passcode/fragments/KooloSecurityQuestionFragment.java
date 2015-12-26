package com.example.srravela.koolo.passcode.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.passcode.activities.KooloPasscodeVerificationActivity;
import com.example.srravela.koolo.passcode.listeners.KooloPasscodeVerificationListener;

public class KooloSecurityQuestionFragment extends Fragment implements EditText.OnEditorActionListener {
    public static final String TAG=KooloSecurityQuestionFragment.class.getSimpleName();
    private KooloPasscodeVerificationActivity mActivity;
    private static Context mContext;
    TextView securityQuestionText;
    EditText securityQuestionAnswerText;
    private View rootView;
    private KooloPasscodeVerificationListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloSecurityQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloSecurityQuestionFragment newInstance() {
        KooloSecurityQuestionFragment fragment = new KooloSecurityQuestionFragment();
        return fragment;
    }

    public KooloSecurityQuestionFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_security_question,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=(KooloPasscodeVerificationActivity)getActivity();
        mContext=mActivity.getApplicationContext();
        mListener=(KooloPasscodeVerificationListener) mActivity;
        intiUI();
        setHasOptionsMenu(false);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void intiUI() {
        SharedPreferences securityQuestionSharedPreferences=mContext.getSharedPreferences(KooloApplication.SECURITY_QUESTION, mContext.MODE_PRIVATE);
        String securityQuestion = securityQuestionSharedPreferences.getString(KooloApplication.SELECTED_SECURITY_QUESTION, null);

        securityQuestionText = (TextView)rootView.findViewById(R.id.security_question_text);
        securityQuestionText.setText(securityQuestion);
        securityQuestionAnswerText = (EditText) rootView.findViewById(R.id.security_question_answer_edit_text);
        securityQuestionAnswerText.setOnEditorActionListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDoneAction((EditText)v);
        }
        return false;
    }

    private void onDoneAction(EditText editedTextView) {
        String securityQuestionAnswer = editedTextView.getText().toString();
        String actualAnswer = null;

        SharedPreferences securityQuestionSharedPreferences=mContext.getSharedPreferences(KooloApplication.SECURITY_QUESTION, mContext.MODE_PRIVATE);
        actualAnswer = securityQuestionSharedPreferences.getString(KooloApplication.SECURITY_QUESTION_ANSWER, null);

        if(securityQuestionAnswer != null && !(securityQuestionAnswer.isEmpty())) {
            if(securityQuestionAnswer.equals(actualAnswer)) {
                //TODO: GO back to previous screen.
                Bundle bundle=new Bundle();
                bundle.putInt(KooloPasscodeVerificationListener.KOOLO_PASSCODE_VERIFICATION, KooloPasscodeVerificationListener.KOOLO_CORRECT_SECURITY_ANSWER_ENTERED);
                mListener.onPasscodeVerification(bundle);
            } else {
                Toast toast = Toast.makeText(mContext,"Wrong security answer entered", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER|Gravity.TOP, 0, 0);
                toast.show();
                securityQuestionAnswerText.setText(null);
            }
        } else {
            Toast toast = Toast.makeText(mContext,"Security answer cannot be empty .", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER|Gravity.TOP, 0, 0);
            toast.show();
        }
    }
}
