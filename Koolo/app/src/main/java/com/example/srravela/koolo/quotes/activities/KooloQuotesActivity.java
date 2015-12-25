package com.example.srravela.koolo.quotes.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.quotes.fragments.KooloQuotesFragment;
import com.example.srravela.koolo.quotes.listeners.KooloQuotesInteractionListener;
import com.example.srravela.koolo.settings.fragments.KooloSettingsFragment;

public class KooloQuotesActivity extends KooloBaseActivity implements KooloQuotesInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_quotes);
        mContext=getApplicationContext();
        getSupportActionBar().setTitle(getString(R.string.action_quotes));
        initUI();
    }

    void initUI() {
        loadKooloQuotesFragment();
    }

    /**
     *  Method for loading Quotes Fragment.
     */
    private void loadKooloQuotesFragment(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloQuotesFragment.newInstance();
        transaction.replace(R.id.fragment_quotes_container, fragment, "kooloquotesfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_koolo_quotes, menu);
        return true;
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

    @Override
    public void onQuotesInteraction(Bundle urlBundle) {

    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
