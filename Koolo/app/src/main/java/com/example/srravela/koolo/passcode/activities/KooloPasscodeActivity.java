package com.example.srravela.koolo.passcode.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.passcode.fragments.KooloPasscodeFragment;
import com.example.srravela.koolo.passcode.fragments.KooloPasscodeSetterFragment;
import com.example.srravela.koolo.passcode.listeners.KooloPasscodeInteractionListener;

public class KooloPasscodeActivity extends KooloBaseActivity implements KooloPasscodeInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_passcode);
        mContext=getApplicationContext();
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Passkode");

        initUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_koolo_passcode, menu);
        return true;
    }


    void initUI() {
        loadKooloPasscodesFragment();
    }

    /**
     *  Method for loading Passcode Fragment.
     */
    private void loadKooloPasscodesFragment(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloPasscodeFragment.newInstance();
        transaction.replace(R.id.fragment_passcode_container, fragment, "koolopasscodesfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPasscodeInteraction(Bundle urlBundle) {
        int passcodeAction = urlBundle.getInt(KooloPasscodeInteractionListener.KOOLO_PASSCODE_ACTION);
        switch(passcodeAction) {
            case KOOLO_ENTER_PASSCODE_BUTTON_CLICKED_ACTION:
                loadEnterPasscodeFragment();
                break;
        }
    }

    /*Load Set Passcode Fragment*/
    void loadEnterPasscodeFragment() {
        //TODO: Add Enter Passcode Fragment here.
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloPasscodeSetterFragment.newInstance();
        transaction.replace(R.id.fragment_passcode_container, fragment, "koolosetpasscodefragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
