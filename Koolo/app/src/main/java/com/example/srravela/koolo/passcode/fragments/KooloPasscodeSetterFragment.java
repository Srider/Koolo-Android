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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.passcode.activities.KooloPasscodeActivity;
import com.example.srravela.koolo.passcode.listeners.KooloPasscodeInteractionListener;
import com.example.srravela.koolo.passcode.listeners.KooloPasscodeVerificationListener;

public class KooloPasscodeSetterFragment extends Fragment implements EditText.OnEditorActionListener{

    public static final String TAG=KooloPasscodeSetterFragment.class.getSimpleName();
    private KooloPasscodeActivity mActivity;
    EditText digit1EditText, digit2EditText, digit3EditText, digit4EditText;
    private static Context mContext;
    private boolean isDigit1Entered = false;
    private boolean isDigit2Entered = false;
    private boolean isDigit3Entered = false;
    private boolean isDigit4Entered = false;
    private View rootView;
    private KooloPasscodeInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloPasscodeSetterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloPasscodeSetterFragment newInstance() {
        KooloPasscodeSetterFragment fragment = new KooloPasscodeSetterFragment();

        return fragment;
    }

    public KooloPasscodeSetterFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_passcode_setter,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=(KooloPasscodeActivity)getActivity();
        mContext=mActivity.getApplicationContext();
        mListener =(KooloPasscodeInteractionListener) mActivity;

        intiUI();

        setHasOptionsMenu(false);
    }

    private void intiUI(){
        digit1EditText = (EditText)rootView.findViewById(R.id.digit1_edit_text);
        digit1EditText.setTag("1");
        digit1EditText.setOnEditorActionListener(this);
        digit1EditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        digit1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(isDigit1Entered) {
                    digit1EditText.setBackgroundResource(R.drawable.drawable_passcode_filled_color_style);
                } else {
                    digit1EditText.setBackgroundResource(R.drawable.drawable_passcode_empty_color_style);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = digit1EditText.getText().length();
                if (textlength1 >= 1) {
                    isDigit1Entered = true;
                    digit2EditText.requestFocus();
                } else if(textlength1 == 0) {
                    isDigit1Entered = false;
                    digit1EditText.requestFocus();
                }
            }
        });

        digit2EditText = (EditText)rootView.findViewById(R.id.digit2_edit_text);
        digit2EditText.setTag("2");
        digit2EditText.setOnEditorActionListener(this);
        digit2EditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        digit2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if (isDigit2Entered) {
                    digit2EditText.setBackgroundResource(R.drawable.drawable_passcode_filled_color_style);
                } else {
                    digit2EditText.setBackgroundResource(R.drawable.drawable_passcode_empty_color_style);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength2 = digit2EditText.getText().length();
                if (textlength2 >= 1) {
                    isDigit2Entered = true;
                    digit3EditText.requestFocus();
                } else if (textlength2 == 0) {
                    isDigit2Entered = false;
                    digit1EditText.requestFocus();
                }
            }
        });

        digit3EditText = (EditText)rootView.findViewById(R.id.digit3_edit_text);
        digit3EditText.setTag("3");
        digit3EditText.setOnEditorActionListener(this);
        digit3EditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        digit3EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(isDigit3Entered) {
                    digit3EditText.setBackgroundResource(R.drawable.drawable_passcode_filled_color_style);
                } else {
                    digit3EditText.setBackgroundResource(R.drawable.drawable_passcode_empty_color_style);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength3 = digit3EditText.getText().length();
                if (textlength3 >= 1) {
                    isDigit3Entered = true;
                    digit4EditText.requestFocus();
                } else if (textlength3 == 0) {
                    isDigit3Entered = false;
                    digit2EditText.requestFocus();
                }
            }
        });

        digit4EditText = (EditText)rootView.findViewById(R.id.digit4_edit_text);
        digit4EditText.setTag("4");
        digit4EditText.setOnEditorActionListener(this);
        digit4EditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        digit4EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(isDigit4Entered) {
                    digit4EditText.setBackgroundResource(R.drawable.drawable_passcode_filled_color_style);
                } else {
                    digit4EditText.setBackgroundResource(R.drawable.drawable_passcode_empty_color_style);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = digit4EditText.getText().length();
                if (textlength4 >= 1) {
                    isDigit4Entered = true;
                } else if (textlength4 == 0) {
                    isDigit4Entered = false;
                    digit3EditText.requestFocus();
                }
            }
        });
    }
    private void onBackPressForEditText(EditText editText) {
        //this is for backspace
        int tag = Integer.parseInt((String) editText.getTag());
        switch(tag) {
            case 1:
                digit1EditText.setBackgroundResource(R.drawable.drawable_passcode_empty_color_style);
                digit1EditText.getText().clear();
                digit1EditText.clearFocus();
                break;
            case 2:
                digit2EditText.setBackgroundResource(R.drawable.drawable_passcode_empty_color_style);
                digit2EditText.getText().clear();
                digit2EditText.clearFocus();
                digit1EditText.requestFocus();
                break;
            case 3:
                digit3EditText.setBackgroundResource(R.drawable.drawable_passcode_empty_color_style);
                digit3EditText.getText().clear();
                digit3EditText.clearFocus();
                digit2EditText.requestFocus();
                break;
            case 4:
                digit4EditText.setBackgroundResource(R.drawable.drawable_passcode_empty_color_style);
                digit4EditText.getText().clear();
                digit4EditText.clearFocus();
                digit3EditText.requestFocus();
                break;
        }
    }

    private void onOtherKeyPressForEditText(EditText editText) {
        //this is for backspace
        int tag = Integer.parseInt((String) editText.getTag());
        switch(tag) {
            case 1:
                digit1EditText.setBackgroundResource(R.drawable.drawable_passcode_filled_color_style);
                digit1EditText.clearFocus();
                digit2EditText.requestFocus();
                break;
            case 2:
                digit2EditText.setBackgroundResource(R.drawable.drawable_passcode_filled_color_style);
                digit2EditText.clearFocus();
                digit3EditText.requestFocus();
                break;
            case 3:
                digit3EditText.setBackgroundResource(R.drawable.drawable_passcode_filled_color_style);
                digit3EditText.clearFocus();
                digit4EditText.requestFocus();
                break;
            case 4:
                digit4EditText.setBackgroundResource(R.drawable.drawable_passcode_filled_color_style);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            onDoneAction((EditText)v);
        }
        return false;
    }
    private void onDoneAction(EditText editedTextView) {
        Boolean canProceed = validatePasscodeDigits();
        if(canProceed) {
            //Get 4 digit passcode.
            String passcode = digit1EditText.getText().toString()+digit2EditText.getText().toString()+digit3EditText.getText().toString()+digit4EditText.getText().toString();

            SharedPreferences enablePasscodePreferences=mContext.getSharedPreferences(KooloApplication.PASSCODE_ENABLED, mContext.MODE_PRIVATE);
            SharedPreferences.Editor enablePasscodeEditor=enablePasscodePreferences.edit();
            enablePasscodeEditor.putString(KooloApplication.SELECTED_PASSCODE, passcode);
            enablePasscodeEditor.commit();
            Toast.makeText(mContext, "Passcode set", Toast.LENGTH_SHORT).show();

            Bundle bundle=new Bundle();
            bundle.putInt(KooloPasscodeInteractionListener.KOOLO_PASSCODE_ACTION, KooloPasscodeInteractionListener.KOOLO_PASSCODE_SET_ACTION);
            mListener.onPasscodeInteraction(bundle);
        } else {
            Toast.makeText(mContext, "Passcode should be 4 characters", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validatePasscodeDigits() {
        boolean isPasscodeFormatValid = false;
        if(digit1EditText.getText().length() ==1 && digit2EditText.getText().length() ==1&&digit3EditText.getText().length() ==1 && digit4EditText.getText().length() ==1) {
            isPasscodeFormatValid = true;
        }
        return isPasscodeFormatValid;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
