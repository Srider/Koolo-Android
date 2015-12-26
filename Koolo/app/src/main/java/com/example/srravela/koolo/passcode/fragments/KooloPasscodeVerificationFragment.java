package com.example.srravela.koolo.passcode.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.passcode.activities.KooloPasscodeActivity;
import com.example.srravela.koolo.passcode.activities.KooloPasscodeVerificationActivity;
import com.example.srravela.koolo.passcode.listeners.KooloPasscodeInteractionListener;
import com.example.srravela.koolo.passcode.listeners.KooloPasscodeVerificationListener;

public class KooloPasscodeVerificationFragment extends Fragment implements View.OnClickListener {

    public static final String TAG=KooloPasscodeSetterFragment.class.getSimpleName();
    private KooloPasscodeVerificationActivity mActivity;
    public static int retryCount = 0;
    private static Context mContext;
    private StringBuilder passcodeBuilder = null;
    Button digit1;
    Button digit2;
    Button digit3;
    Button digit4;
    Button digit5;
    Button digit6;
    Button digit7;
    Button digit8;
    Button digit9;
    Button digit0;


    private View rootView;
    private KooloPasscodeVerificationListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloPasscodeVerificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloPasscodeVerificationFragment newInstance() {
        KooloPasscodeVerificationFragment fragment = new KooloPasscodeVerificationFragment();
        return fragment;
    }

    public KooloPasscodeVerificationFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_passcode_verification,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=(KooloPasscodeVerificationActivity)getActivity();
        mContext=mActivity.getApplicationContext();
        mListener =(KooloPasscodeVerificationListener) mActivity;
        intiUI();
        setHasOptionsMenu(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        retryCount = 0;
    }

    private void intiUI(){

        digit0=(Button)rootView.findViewById(R.id.digit_0_button);
        digit0.setOnClickListener(this);

        digit1=(Button)rootView.findViewById(R.id.digit_1_button);
        digit1.setOnClickListener(this);

        digit2=(Button)rootView.findViewById(R.id.digit_2_button);
        digit2.setOnClickListener(this);

        digit3=(Button)rootView.findViewById(R.id.digit_3_button);
        digit3.setOnClickListener(this);

        digit4=(Button)rootView.findViewById(R.id.digit_4_button);
        digit4.setOnClickListener(this);

        digit5=(Button)rootView.findViewById(R.id.digit_5_button);
        digit5.setOnClickListener(this);

        digit6=(Button)rootView.findViewById(R.id.digit_6_button);
        digit6.setOnClickListener(this);

        digit7=(Button)rootView.findViewById(R.id.digit_7_button);
        digit7.setOnClickListener(this);

        digit8=(Button)rootView.findViewById(R.id.digit_8_button);
        digit8.setOnClickListener(this);

        digit9=(Button)rootView.findViewById(R.id.digit_9_button);
        digit9.setOnClickListener(this);

    }

//    private void onDoneAction(String passcodeEntered) {
//        SharedPreferences enablePasscodePreferences=mContext.getSharedPreferences(KooloApplication.PASSCODE_ENABLED, mContext.MODE_PRIVATE);
//        String actualPasscode = enablePasscodePreferences.getString(KooloApplication.SELECTED_PASSCODE, null);
//
//
//        if(retryCount<3) {
//            if (actualPasscode.equals(passcodeEntered)) {
//                Toast.makeText(mContext, "Access Granted", Toast.LENGTH_SHORT).show();
//                Bundle bundle=new Bundle();
//                bundle.putInt(KooloPasscodeVerificationListener.KOOLO_PASSCODE_VERIFICATION, KooloPasscodeVerificationListener.KOOLO_PASSCODE_ENTERED_ACTION);
//                mListener.onPasscodeVerification(bundle);
//            } else {
//                retryCount += 1;
//                Toast.makeText(mContext, "Wrong passcode entered", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            //Trigger security question page.
//            if(canProceedToSecurityQuestion()) {
//                Bundle bundle = new Bundle();
//                bundle.putInt(KooloPasscodeVerificationListener.KOOLO_PASSCODE_VERIFICATION, KooloPasscodeVerificationListener.KOOLO_PASSCODE_RETRY_EXPIRED);
//                mListener.onPasscodeVerification(bundle);
//            }
//
//        }
//    }

    private void onDoneAction(String passcodeEntered) {
        SharedPreferences enablePasscodePreferences=mContext.getSharedPreferences(KooloApplication.PASSCODE_ENABLED, mContext.MODE_PRIVATE);
        String actualPasscode = enablePasscodePreferences.getString(KooloApplication.SELECTED_PASSCODE, null);

        if (actualPasscode.equals(passcodeEntered)) {
            Toast.makeText(mContext, "Access Granted", Toast.LENGTH_SHORT).show();
            Bundle bundle=new Bundle();
            bundle.putInt(KooloPasscodeVerificationListener.KOOLO_PASSCODE_VERIFICATION, KooloPasscodeVerificationListener.KOOLO_PASSCODE_ENTERED_ACTION);
            mListener.onPasscodeVerification(bundle);
        } else {
            retryCount += 1;
            if(retryCount>=3) {
                //Trigger security question page.
                if(canProceedToSecurityQuestion()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(KooloPasscodeVerificationListener.KOOLO_PASSCODE_VERIFICATION, KooloPasscodeVerificationListener.KOOLO_PASSCODE_RETRY_EXPIRED);
                    mListener.onPasscodeVerification(bundle);
                }
            } else {
                Toast toast = Toast.makeText(mContext,"Wrong passcode entered", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER|Gravity.TOP, 0, 0);
                toast.show();
            }
        }
    }

    private boolean canProceedToSecurityQuestion() {
        boolean canProceed = false;
        SharedPreferences securityQuestionSharedPreferences=mContext.getSharedPreferences(KooloApplication.SECURITY_QUESTION, mContext.MODE_PRIVATE);
        String securityQuestion = securityQuestionSharedPreferences.getString(KooloApplication.SELECTED_SECURITY_QUESTION, null);
        String actualAnswer = securityQuestionSharedPreferences.getString(KooloApplication.SECURITY_QUESTION_ANSWER, null);
        if(securityQuestion != null && !(securityQuestion.isEmpty()) && actualAnswer != null && !(actualAnswer.isEmpty())) {
            canProceed = true;
        }
        return canProceed;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.digit_0_button:
                checkAndAddDigit(0);
                break;

            case R.id.digit_1_button:
                checkAndAddDigit(1);
                break;

            case R.id.digit_2_button:
                checkAndAddDigit(2);
                break;

            case R.id.digit_3_button:
                checkAndAddDigit(3);
                break;

            case R.id.digit_4_button:
                checkAndAddDigit(4);
                break;

            case R.id.digit_5_button:
                checkAndAddDigit(5);
                break;

            case R.id.digit_6_button:
                checkAndAddDigit(6);
                break;

            case R.id.digit_7_button:
                checkAndAddDigit(7);
                break;

            case R.id.digit_8_button:
                checkAndAddDigit(8);
                break;

            case R.id.digit_9_button:
                checkAndAddDigit(9);
                break;
        }
    }

    private void checkAndAddDigit(int digit) {
        String passcode = null;
        String actualPasscode = null;
        if (passcodeBuilder == null) {
            passcodeBuilder = new StringBuilder();
        }
        passcodeBuilder.append(digit);
        if (passcodeBuilder.length() == 4) {
            passcode = passcodeBuilder.toString();
            onDoneAction(passcode);
            passcodeBuilder = null;
        }
    }
}
