package com.example.srravela.koolo.passcode.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.view.Menu;
import android.widget.ImageView;


import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.passcode.fragments.KooloPasscodeVerificationFragment;
import com.example.srravela.koolo.passcode.fragments.KooloSecurityQuestionFragment;
import com.example.srravela.koolo.passcode.listeners.KooloPasscodeInteractionListener;
import com.example.srravela.koolo.passcode.listeners.KooloPasscodeVerificationListener;

public class KooloPasscodeVerificationActivity extends KooloBaseActivity implements KooloPasscodeVerificationListener{
    ImageView backgroundImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_passcode_verification);
        mContext=getApplicationContext();
        getSupportActionBar().hide();
        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_koolo_passcode, menu);
        return true;
    }

    void initUI() {
        backgroundImageView = (ImageView)findViewById(R.id.home_image);
        loadKooloPasscodeVerificationFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundImageView.setImageResource(R.drawable.background);
    }

    /**
     *  Method for loading Passcode Fragment.
     */
    private void loadKooloPasscodeVerificationFragment(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloPasscodeVerificationFragment.newInstance();
        transaction.replace(R.id.fragment_passcode_verification_container, fragment, "koolopasscodeverificationfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     *  Method for loading Passcode Fragment.
     */
    private void loadSecretQuestionFragment(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloSecurityQuestionFragment.newInstance();
        transaction.replace(R.id.fragment_passcode_verification_container, fragment, "koolosecretquestionfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPasscodeVerification(Bundle urlBundle) {
        int passcodeAction = urlBundle.getInt(KooloPasscodeVerificationListener.KOOLO_PASSCODE_VERIFICATION);
        switch(passcodeAction) {
            case KOOLO_PASSCODE_ENTERED_ACTION:
                finish();
                break;
            case KOOLO_PASSCODE_RETRY_EXPIRED:
                loadSecretQuestionFragment();
                break;
            case KOOLO_CORRECT_SECURITY_ANSWER_ENTERED:
                finish();
                break;
        }
    }
}
