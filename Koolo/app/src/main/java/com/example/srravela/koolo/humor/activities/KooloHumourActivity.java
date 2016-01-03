package com.example.srravela.koolo.humor.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.humor.fragments.KooloHumourColorFragment;
import com.example.srravela.koolo.humor.utils.HumourDataStore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KooloHumourActivity extends KooloBaseActivity {
    private static final String TAG = KooloHumourActivity.class.getSimpleName();
    private FrameLayout frameLayoutFragmentContaner;
    public static  List<Humour> humourDetails = null;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_humours);
        mContext=getApplicationContext();
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Color Choices");
        initUI();
    }

    /**
     * Method for configuring the UI
     */
    private void initUI() {

        if(humourDetails == null) {
            humourDetails = new ArrayList<Humour>();
            humourDetails = loadHumourColours();
        }

        frameLayoutFragmentContaner = (FrameLayout) findViewById(R.id.fragment_humours_container);
        loadHumourListFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Method for loading Loacations and Management Fragment.
     */
    private void loadHumourListFragment(){

        Bundle humoursBundle =  new Bundle();
        humoursBundle.putSerializable("Humours", (Serializable) humourDetails);
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        Fragment fragment= KooloHumourColorFragment.newInstance();
        fragment.setArguments(humoursBundle);
        transaction.replace(R.id.fragment_humours_container, fragment, "humourlistfragment");
        transaction.commit();
    }

    public List<Humour> loadHumourColours() {

        HumourDataStore humourDataStore = HumourDataStore.getSharedHumoursDataStore(mContext.getResources().getString(R.string.humours_file_name), mContext);
        //First Get Humours from file.
        List<Humour> humourFileDetails = humourDataStore.readHumoursFromFile();
        //If file is empty
        if(humourFileDetails == null || humourFileDetails.isEmpty()) {
            humourFileDetails = new ArrayList<Humour>();
            String[] stringArray = (String[])getResources().getStringArray(R.array.koolo_humour_color_choices);
            if(stringArray.length >0 && stringArray.length==10) {
                Humour tempHumour1 = new Humour(stringArray[0], Utils.ColorType.BLACK);
                humourFileDetails.add(tempHumour1);

                Humour tempHumour2 = new Humour(stringArray[1], Utils.ColorType.BROWN);
                humourFileDetails.add(tempHumour2);

                Humour tempHumour3 = new Humour(stringArray[2], Utils.ColorType.YELLOW);
                humourFileDetails.add(tempHumour3);

                Humour tempHumour4 = new Humour(stringArray[3], Utils.ColorType.GREEN);
                humourFileDetails.add(tempHumour4);

                Humour tempHumour5 = new Humour(stringArray[4], Utils.ColorType.GREY);
                humourFileDetails.add(tempHumour5);

                Humour tempHumour6 = new Humour(stringArray[5], Utils.ColorType.BLUE);
                humourFileDetails.add(tempHumour6);

                Humour tempHumour7 = new Humour(stringArray[6], Utils.ColorType.ORANGE);
                humourFileDetails.add(tempHumour7);

                Humour tempHumour8 = new Humour(stringArray[7], Utils.ColorType.PINK);
                humourFileDetails.add(tempHumour8);

                Humour tempHumour9 = new Humour(stringArray[8], Utils.ColorType.RED);
                humourFileDetails.add(tempHumour9);

                Humour tempHumour10 = new Humour(stringArray[9], Utils.ColorType.DARK_GREY);
                humourFileDetails.add(tempHumour10);
            }
            //Add humours to file now.
            if( humourDataStore.writeHumoursToFile(humourDetails)) {
                Log.i(TAG, "HUMOURS WRITE SUCCESSFULL");
            } else {
                Log.i(TAG, "ERROR - WRITE FAILED !!!");
            }
        }
        return humourFileDetails;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
