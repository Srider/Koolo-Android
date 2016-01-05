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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.moods.activities.KooloMoodsActivity;
import com.example.srravela.koolo.moods.database.DatabaseHandler;

public class KooloMoodMapFragment extends Fragment implements View.OnClickListener, KooloMoodsActivity.OnBackPressedListener {

    private View rootView;
    private Context mContext;
    private KooloMoodsActivity mActivity;
    public static KooloMoodMapFragment fragmentKooloMoodMap;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment KooloMoodMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloMoodMapFragment newInstance() {
        KooloMoodMapFragment fragment = new KooloMoodMapFragment();
        return fragment;
    }

    public KooloMoodMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mActivity.getSupportActionBar().setTitle("Select Humor Color");
        mActivity = (KooloMoodsActivity) getActivity();

    }

    ImageView backgroundImageView;

    //View actionBarButtons;
    View greenActionView,yellowActionView,blueActionView,magentaActionView,redActionView,blackActionView,grayActionView,whiteActionView,orangeActionView,brownActionView;
    View doneActionView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_koolo_mood_map, container, false);

        whiteActionView = rootView.findViewById(R.id.white_color_view);
        whiteActionView.setOnClickListener(this);

        greenActionView = rootView.findViewById(R.id.theme_green_color_view);
        greenActionView.setOnClickListener(this);

        yellowActionView = rootView.findViewById(R.id.yellow_color_view);
        yellowActionView.setOnClickListener(this);

        blueActionView = rootView.findViewById(R.id.blue_color_view);
        blueActionView.setOnClickListener(this);

        magentaActionView = rootView.findViewById(R.id.magenta_color_view);
        magentaActionView.setOnClickListener(this);

        redActionView = rootView.findViewById(R.id.red_color_view);
        redActionView.setOnClickListener(this);


        blackActionView = rootView.findViewById(R.id.black_color_view);
        blackActionView.setOnClickListener(this);


        grayActionView = rootView.findViewById(R.id.gray_color_view);
        grayActionView.setOnClickListener(this);


        orangeActionView = rootView.findViewById(R.id.orange_color_view);
        orangeActionView.setOnClickListener(this);

        brownActionView = rootView.findViewById(R.id.brown_color_view);
        brownActionView.setOnClickListener(this);
        backgroundImageView = (ImageView) rootView.findViewById(R.id.moods_background_image);

        SharedPreferences backgroundSharedPreferences = mActivity.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mActivity.MODE_PRIVATE);
        SharedPreferences backgroundImageFlagPreferences = mActivity.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mActivity.MODE_PRIVATE);
        if (backgroundImageFlagPreferences.getBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, false)) {
            Uri myUri = Uri.parse(backgroundSharedPreferences.getString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, KooloApplication.getImageUri()));
            backgroundImageView.setImageURI(myUri);
        } else {
            backgroundImageView.setImageResource(R.drawable.background);
        }

       /* actionBarButtons = mActivity.getLayoutInflater().inflate(R.layout.action_bar_mood_map, null, false);
        doneActionView = actionBarButtons.findViewById(R.id.action_bar_mood_map_cancel_tv);

        doneActionView.setOnClickListener(mActionBarListener);
        mActivity.getSupportActionBar().setCustomView(actionBarButtons);
        View v = mActivity.getSupportActionBar().getCustomView();
        Toolbar parent = (Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0, 0);*/
       /* lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        v.setLayoutParams(lp);*/
        //mActivity.getSupportActionBar().setDisplayShowCustomEnabled(true);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar =mActivity.getSupportActionBar();
        actionBar.setTitle("Mood Map");

    }

    private final View.OnClickListener mActionBarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onActionBarItemSelected(v.getId());
        }
    };

    private boolean onActionBarItemSelected(int itemId) {
        switch (itemId) {
            case R.id.action_bar_mood_map_cancel_tv:
                getFragmentManager().popBackStack();
                break;
        }
        return true;
    }

    private void loadMoodListFragment(String selectedColor) {
        //  actionBar.hide();
        // setTitle("MoodLine");
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = KooloMoodLineFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putString("COLOR_CHOOSER", selectedColor);
        bundle.putBoolean("SELECTED_COLOR", true);
        fragment.setArguments(bundle);
        transaction.replace(R.id.fragment_moods_container, fragment, "moodlistfragment");
        transaction.commitAllowingStateLoss();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.white_color_view:
                loadMoodListFragment("ALL");
                break;
            case R.id.theme_green_color_view:
                loadMoodListFragment("GREEN");
                break;
            case R.id.yellow_color_view:
                loadMoodListFragment("YELLOW");
                break;

            case R.id.blue_color_view:
                loadMoodListFragment("BLUE");
                break;

            case R.id.magenta_color_view:
                loadMoodListFragment("PINK");
                break;

            case R.id.red_color_view:
                loadMoodListFragment("RED");
                break;

            case R.id.black_color_view:
                loadMoodListFragment("BLACK");
                break;

            case R.id.gray_color_view:
                loadMoodListFragment("DARK_GREY");
                break;

            case R.id.orange_color_view:
                loadMoodListFragment("ORANGE");
                break;

            case R.id.brown_color_view:
                loadMoodListFragment("BROWN");
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(KooloMoodLineFragment.FRAGMENT_KEY,KooloMoodLineFragment.FRAGMENT_VALUE);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        getFragmentManager().popBackStack();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
