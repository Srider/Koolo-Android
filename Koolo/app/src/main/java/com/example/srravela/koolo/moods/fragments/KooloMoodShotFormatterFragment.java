package com.example.srravela.koolo.moods.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.MoodShot;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.moods.activities.KooloMoodsActivity;
import com.example.srravela.koolo.moods.adapters.KooloMoodsListAdapter;
import com.example.srravela.koolo.moods.listeners.KooloMoodsListener;

import java.util.List;

public class KooloMoodShotFormatterFragment extends Fragment  implements KooloMoodsActivity.OnBackPressedListener {
    private static final String TAG = KooloMoodsListFragment.class.getSimpleName();
    private ImageView backgroundImageView;
    private ImageView moodshotImageView;
    private View rootView;
    KooloMoodsListAdapter moodsListAdapter;
    List<MoodShot> moodsListItems = null;
    private Context mContext;
    private KooloMoodsActivity mActivity;
    public static KooloMoodsListFragment fragmentMoodslist;
    private KooloMoodsListener mListener;
    String selectedImage;
    ImageView moodshot_image;

    public static KooloMoodShotFormatterFragment newInstance() {
        KooloMoodShotFormatterFragment fragment = new KooloMoodShotFormatterFragment();
        return fragment;
    }

    public KooloMoodShotFormatterFragment() {
        // Required empty public constructor
    }
    String[] colorNameArray;
    int[] colorCodeArray;
    // Declare Variables
    ViewPager viewPager;
    PagerAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        selectedImage =  getArguments().getString("selectedImage");
        colorNameArray = (String[])getResources().getStringArray(R.array.koolo_humour_color_choices);
        colorCodeArray = new int[] { R.drawable.drawable_color_round_black,R.drawable.drawable_color_round_brown,R.drawable.drawable_color_round_yellow,R.drawable.drawable_color_round_theme_green,
                R.drawable.drawable_color_round_grey,R.drawable.drawable_color_round_blue,R.drawable.drawable_color_round_orange,R.drawable.drawable_color_round_pink,
                R.drawable.drawable_color_round_brown,R.drawable.drawable_color_round_darkgrey};
// Locate the ViewPager in viewpager_main.xml


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
        adapter = new ViewPagerAdapter(view.getContext(),colorCodeArray,colorNameArray);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
        return view;
    }
    @Override
    public void onSaveInstanceState( Bundle outState ) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onBackPressed() {

    }
}
