package com.example.srravela.koolo.moods.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.activities.KooloChecklistActivity;
import com.example.srravela.koolo.checklists.adapters.KooloChecklistAdapter;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.checklists.utils.GoalsDataStore;
import com.example.srravela.koolo.entities.Checklist;
import com.example.srravela.koolo.entities.MoodShot;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.moods.activities.KooloMoodsActivity;
import com.example.srravela.koolo.moods.adapters.KooloMoodsListAdapter;
import com.example.srravela.koolo.moods.database.DatabaseHandler;
import com.example.srravela.koolo.moods.listeners.KooloMoodsListener;
import com.example.srravela.koolo.moods.utils.MoodsDataStore;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by srikar on 5/12/15.
 * Updated by tippi on 22/12/15.

 */
public class KooloMoodsListFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    private static final String TAG = KooloMoodsListFragment.class.getSimpleName();
    private ImageView backgroundImageView;
    private View rootView;
    ListView moodslist;
    KooloMoodsListAdapter moodsListAdapter;
    List<MoodShot> moodsListItems = null;
    private Button cameraButton, addButton;
    private Context mContext;
    private KooloMoodsActivity mActivity;
    public static KooloMoodsListFragment fragmentMoodslist;
    private KooloMoodsListener mListener;

    static MoodShot moodShot;
    public static KooloMoodsListFragment newInstance() {
        KooloMoodsListFragment fragment = new KooloMoodsListFragment();
        //moodShot = new MoodShot(photoUri,Utils.ColorType.BLACK);

        return fragment;
    }

    public KooloMoodsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_koolo_moods_list, container, false);
        return rootView;
    }
    DatabaseHandler databaseHandler;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=(KooloMoodsActivity)getActivity();
        mListener = mActivity;
        mContext=mActivity.getApplicationContext();
        databaseHandler = new DatabaseHandler(mContext);
        initUI();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Method for configuring the UI
     */
    List<MoodShot> moodShots;
    private void initUI(){
        setHasOptionsMenu(false);

        mActivity.getSupportActionBar().setTitle(getResources().getString(R.string.mood_line_title));

        //Image
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
      // moodsListItems= Utils.getSharedUtils(mContext).loadMoods();
        moodShots = new ArrayList<MoodShot>();
        moodShots= databaseHandler.getAllMoodShots();
       /* moodShots = new ArrayList<MoodShot>();
        moodShots.add(moodShot);*/

        if(moodShots.size() > 0){
            moodsListAdapter = new KooloMoodsListAdapter(moodShots, mContext, mListener);
            moodslist.setAdapter(moodsListAdapter);
            moodslist.setOnItemClickListener(this);
        } else {
            Log.i(TAG, "NO ITEMS");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.moods_camera_button:
                triggerCameraMoodCaptureActivity();
                break;
            case R.id.moods_add_button:
                triggerGalleryActivity();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MoodShot tempMoodShot = moodsListItems.get(position);
        triggerMoodShotFormatterFragment();
    }

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

}
