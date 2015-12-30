package com.example.srravela.koolo.moods.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.MoodShot;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.humor.utils.HumourDataStore;
import com.example.srravela.koolo.moods.activities.KooloMoodsActivity;
import com.example.srravela.koolo.moods.database.DatabaseHandler;
import com.example.srravela.koolo.moods.listeners.KooloMoodSelectListener;
import com.example.srravela.koolo.moods.listeners.KooloMoodsListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KooloMoodShotFormatterFragment extends Fragment  implements KooloMoodsActivity.OnBackPressedListener, KooloMoodSelectListener {
    private static final String TAG = KooloMoodsListFragment.class.getSimpleName();
    private ImageView backgroundImageView;
    private ImageView moodshotImageView;
    private View rootView;
    private List<MoodShot> moodsListItems = null;
    private Context mContext;
    private KooloMoodsActivity mActivity;
    private KooloMoodsListener mListener;
    private String selectedImage;
    private ImageView moodshot_image;
    public static KooloMoodSelectListener kooloMoodSelectListener;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private DatabaseHandler databaseHandler;
    public static  List<Humour> humourDetails = null;
    private View actionBarButtons1;
    private View cancelHumorActionView,doneHumorActionView;
    private MoodShot mSelectedMoodShot;

    public static KooloMoodShotFormatterFragment newInstance() {
        KooloMoodShotFormatterFragment fragment = new KooloMoodShotFormatterFragment();
        kooloMoodSelectListener = (KooloMoodSelectListener)fragment;
        return fragment;
    }

    public KooloMoodShotFormatterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=(KooloMoodsActivity)getActivity();
        databaseHandler = new DatabaseHandler(mContext);
        selectedImage =  getArguments().getString("selectedImage");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_koolo_mood_shot_formatter, container, false);
        moodshot_image = (ImageView) view.findViewById(R.id.moodshot_image);
        moodshot_image.setImageURI(Uri.parse(selectedImage));
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        mListener = mActivity;
       
        if(humourDetails == null) {
            humourDetails = new ArrayList<Humour>();
            humourDetails = loadHumourColours();
        }
        adapter = new ViewPagerAdapter(view.getContext(),humourDetails,kooloMoodSelectListener);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
        actionBarButtons1 = inflater.inflate(R.layout.action_bar_humor_color,
                container, false);

        cancelHumorActionView = actionBarButtons1.findViewById(R.id.action_bar_humor_cancel_tv);
        cancelHumorActionView.setOnClickListener(mActionBarListener);

        doneHumorActionView = actionBarButtons1.findViewById(R.id.action_bar_humor_done_tv);
        doneHumorActionView.setOnClickListener(mActionBarListener);

        mActivity.getSupportActionBar().setCustomView(actionBarButtons1);
        View v = mActivity.getSupportActionBar().getCustomView();
        Toolbar parent =(Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0,0);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=(KooloMoodsActivity)getActivity();
        mListener = mActivity;
    }

    private final View.OnClickListener mActionBarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onActionBarItemSelected(v.getId());
        }
    };

    private boolean onActionBarItemSelected(int itemId) {
        switch (itemId) {
            case R.id.action_bar_humor_cancel_tv:
                getFragmentManager().popBackStack();
                break;
            case R.id.action_bar_humor_done_tv:
                if(mSelectedMoodShot != null) {
                    databaseHandler = new DatabaseHandler(mActivity);
                    databaseHandler.addMoodShot(mSelectedMoodShot);
                }
                getFragmentManager().popBackStack();
                break;
        }
        return true;
    }

    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }
    public List<Humour> loadHumourColours() {

        HumourDataStore humourDataStore = HumourDataStore.getSharedHumoursDataStore(mActivity.getResources().getString(R.string.humours_file_name), mActivity);
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
    public void onMoodSelectListener(Humour selectedMoodShot) {
        mSelectedMoodShot = new MoodShot();
        mSelectedMoodShot.setMoodColor(selectedMoodShot.getColorType().toString());
        mSelectedMoodShot.setMoodCaptureUri(selectedImage);
        mSelectedMoodShot.setMoodCaptureDate(getDate());
        //mSelectedMoodShot = selectedMoodShot;
    }

    @Override
    public void onBackPressed() {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
