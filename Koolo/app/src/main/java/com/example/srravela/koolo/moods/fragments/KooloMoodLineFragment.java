package com.example.srravela.koolo.moods.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.MoodShot;
import com.example.srravela.koolo.moods.activities.KooloMoodsActivity;
import com.example.srravela.koolo.moods.adapters.KooloMoodsListAdapter;
import com.example.srravela.koolo.moods.database.DatabaseHandler;
import com.example.srravela.koolo.moods.listeners.KooloMoodsListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikar on 5/12/15.
 * Updated by tippi on 22/12/15.
 */
public class KooloMoodLineFragment extends Fragment implements View.OnClickListener,
        KooloMoodsActivity.OnBackPressedListener {
    private ImageView backgroundImageView;
    ListView moodslist;
    KooloMoodsListAdapter moodsListAdapter;
    List<MoodShot> moodsListItems = null;
    private Button cameraButton, addButton;
    private Context mContext;
    private KooloMoodsActivity mActivity;
    private KooloMoodsListener mListener;
    private List<MoodShot> moodShots;
    private String colorChooser;
    private boolean isColorSelected;
    private DatabaseHandler databaseHandler;

    public static final String FRAGMENT_KEY = "fragment_mood_map";
    public static final int FRAGMENT_VALUE = 2015;

    private static final String TAG = KooloMoodLineFragment.class.getSimpleName();


    public static KooloMoodLineFragment newInstance() {
        KooloMoodLineFragment fragment = new KooloMoodLineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mActivity=(KooloMoodsActivity)getActivity();
        databaseHandler = new DatabaseHandler(mActivity);
        if(getArguments() != null &&  getArguments().getString("COLOR_CHOOSER") !=null ) {
            colorChooser = getArguments().getString("COLOR_CHOOSER");
            isColorSelected = getArguments().getBoolean("SELECTED_COLOR");
        }
    }
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_koolo_moodlinefragment_item, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_moodline_map:
                mActivity.loadMoodMapFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_koolo_moods_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListener = mActivity;
        mContext=mActivity.getApplicationContext();

        initUI();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == FRAGMENT_VALUE && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                String value = data.getStringExtra(FRAGMENT_KEY);
                if(value != null) {
                    Log.v(TAG, "Data passed from Child fragment = " + value);
                    ActionBar actionBar =mActivity.getSupportActionBar();
                    actionBar.setTitle("Mood Line");

                    if(moodShots != null ){
                        moodShots.clear();
                    }
                    moodShots = new ArrayList<MoodShot>();
                    if(isColorSelected && colorChooser != null && !colorChooser.equalsIgnoreCase("ALL")) {
                        moodShots= databaseHandler.getMoodShots(colorChooser);
                    } else {
                        moodShots = databaseHandler.getAllMoodShots();
                        backgroundImageView.setImageResource(R.drawable.background);
                    }

                    //Button
                    cameraButton = (Button) rootView.findViewById(R.id.moods_camera_button);
                    cameraButton.setOnClickListener(this);

                    addButton = (Button) rootView.findViewById(R.id.moods_add_button);
                    addButton.setOnClickListener(this);

                    //Checklist
                    moodslist=(ListView)rootView.findViewById(R.id.moods_list);
                    if(moodShots.size() > 0){
                        moodsListAdapter = new KooloMoodsListAdapter(moodShots, mContext, mListener);
                        moodslist.setAdapter(moodsListAdapter);
                      //  moodslist.setOnItemClickListener(this);
                    } else {
                        Log.i(TAG, "NO ITEMS");
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar =mActivity.getSupportActionBar();
        actionBar.setTitle("Mood Line");

        if(moodShots != null ){
            moodShots.clear();
        }
        moodShots = new ArrayList<MoodShot>();
        if(isColorSelected && colorChooser != null && !colorChooser.equalsIgnoreCase("ALL")) {
            moodShots= databaseHandler.getMoodShots(colorChooser);
        } else {
            moodShots = databaseHandler.getAllMoodShots();
            backgroundImageView.setImageResource(R.drawable.background);
        }
        backgroundImageView = (ImageView)rootView.findViewById(R.id.moods_background_image);
        SharedPreferences backgroundSharedPreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
        SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);
        if(backgroundImageFlagPreferences.getBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, false)) {
            Uri myUri = Uri.parse(backgroundSharedPreferences.getString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, KooloApplication.getImageUri()));
            backgroundImageView.setImageURI(myUri);
        } else {
            backgroundImageView.setImageResource(R.drawable.background);
        }
        //Button
        cameraButton = (Button) rootView.findViewById(R.id.moods_camera_button);
        cameraButton.setOnClickListener(this);

        addButton = (Button) rootView.findViewById(R.id.moods_add_button);
        addButton.setOnClickListener(this);

        //Checklist
        moodslist=(ListView)rootView.findViewById(R.id.moods_list);
        if(moodShots.size() > 0){
            moodsListAdapter = new KooloMoodsListAdapter(moodShots, mContext, mListener);
            moodslist.setAdapter(moodsListAdapter);
          //  moodslist.setOnItemClickListener(this);
        } else {
            Log.i(TAG, "NO ITEMS");
        }
    }

    private void initUI() {
        setHasOptionsMenu(true);

        backgroundImageView = (ImageView)rootView.findViewById(R.id.moods_background_image);
        SharedPreferences backgroundSharedPreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
        SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);
        if(backgroundImageFlagPreferences.getBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, false)) {
            Uri myUri = Uri.parse(backgroundSharedPreferences.getString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, KooloApplication.getImageUri()));
            backgroundImageView.setImageURI(myUri);
        } else {
            backgroundImageView.setImageResource(R.drawable.background);
        }

        cameraButton = (Button) rootView.findViewById(R.id.moods_camera_button);
        cameraButton.setOnClickListener(this);
        addButton = (Button) rootView.findViewById(R.id.moods_add_button);
        addButton.setOnClickListener(this);
        //Checklist
        moodslist=(ListView)rootView.findViewById(R.id.moods_list);
        moodslist.setOnItemClickListener(null);

    }

   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MoodShot tempMoodShot = moodsListItems.get(position);
        triggerMoodShotFormatterFragment();
    }*/

    private void triggerMoodShotFormatterFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloMoodsListener.KOOLO_MOODS_ACTION, KooloMoodsListener.MOODS_SHOT_FORMATTER);
        mListener.onMoodsAction(bundle);
    }

    private void triggerCameraMoodCaptureActivity() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloMoodsListener.KOOLO_MOODS_ACTION, KooloMoodsListener.MOODS_LAUNCH_CAMERA_ACTION);
        mListener.onMoodsAction(bundle);
    }
    private void triggerGalleryActivity() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloMoodsListener.KOOLO_MOODS_ACTION, KooloMoodsListener.MOODS_LAUNCH_GALLERY_ACTION);
        mListener.onMoodsAction(bundle);
    }

    @Override
    public void onClick(View view) {
        isColorSelected = false;
        colorChooser = null;
        switch(view.getId()) {
            case R.id.moods_camera_button:

                triggerCameraMoodCaptureActivity();
                break;
            case R.id.moods_add_button:

                triggerGalleryActivity();
                break;

            /*case R.id.action_bar_moodline_done_tv:
                //save();
                //  getFragmentManager().popBackStack();
                mActivity.finish();
                break;*/
            case R.id.action_bar_moodline_map_tv:
                //System.err.println("cancel");
                // getActivity().onBackPressed();

                mActivity.loadMoodMapFragment();
               /* FragmentManager fragmentManager=mActivity.getSupportFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                Fragment fragment= KooloMoodMapFragment.newInstance();
                transaction.replace(R.id.fragment_moods_container, fragment, "moodmapfragment");
                transaction.addToBackStack("moodmapfragment");
                transaction.commitAllowingStateLoss();*/
                // getFragmentManager().popBackStack();
                break;

        }
    }



    @Override
    public void onBackPressed() {
        // getActivity().onBackPressed();
       // getActivity().finish();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        databaseHandler.close();
        mListener = null;
    }
}
