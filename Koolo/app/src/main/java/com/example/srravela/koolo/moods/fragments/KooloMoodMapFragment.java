package com.example.srravela.koolo.moods.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.moods.activities.KooloMoodsActivity;

public class KooloMoodMapFragment extends Fragment {

    private View rootView;
    private Context mContext;
    private KooloMoodsActivity mActivity;
    public static KooloMoodMapFragment fragmentKooloMoodMap;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_koolo_mood_map, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
