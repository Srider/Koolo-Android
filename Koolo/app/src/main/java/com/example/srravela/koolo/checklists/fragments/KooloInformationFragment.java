package com.example.srravela.koolo.checklists.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.activities.KooloChecklistActivity;
import com.example.srravela.koolo.checklists.adapters.KooloChecklistAdapter;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.entities.Checklist;
import com.example.srravela.koolo.entities.Utils;

import java.util.List;


public class KooloInformationFragment extends Fragment {
    private ImageView backgroundImageView;
    private static final String TAG = KooloInformationFragment.class.getSimpleName();
    private View rootView;
    private Context mContext;
    private KooloChecklistActivity mActivity;
    public static KooloInformationFragment fragmentInformation;
    private KooloChecklistListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment KooloInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloInformationFragment newInstance() {
        KooloInformationFragment fragment = new KooloInformationFragment();
        return fragment;
    }

    public KooloInformationFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_information, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity= (KooloChecklistActivity) getActivity();
        mContext=mActivity.getApplicationContext();
        mListener =(KooloChecklistListener) mActivity;
        setHasOptionsMenu(true);
        intiUI();
    }

    private void intiUI(){

        backgroundImageView = (ImageView)rootView.findViewById(R.id.checklist_background_image);

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences backgroundSharedPreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
        SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);
        if(backgroundImageFlagPreferences.getBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, false)) {
            Uri myUri = Uri.parse(backgroundSharedPreferences.getString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, KooloApplication.getImageUri()));
            backgroundImageView.setImageURI(myUri);
        } else {
            backgroundImageView.setImageResource(R.drawable.background);
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


}
