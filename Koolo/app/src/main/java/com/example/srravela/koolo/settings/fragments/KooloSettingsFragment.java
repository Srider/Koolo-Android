package com.example.srravela.koolo.settings.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.home.listeners.KooloHomeInteractionListener;
import com.example.srravela.koolo.settings.adapters.KooloSettingsAdapter;
import com.example.srravela.koolo.settings.listeners.KooloSettingsInteractionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KooloSettingsFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String TAG=KooloSettingsFragment.class.getSimpleName();
    private Context mContext;
    private Activity mActivity;
    private View rootView;
    ListView mSettingsList;
    List<String> mSettingsListDetails;
    KooloSettingsAdapter mSettingsAdapter;
    KooloSettingsInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloSettingsFragment newInstance() {
        KooloSettingsFragment fragment = new KooloSettingsFragment();
        return fragment;
    }

    public KooloSettingsFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_settings,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=getActivity();
        mContext=mActivity.getApplicationContext();
        mListener =(KooloSettingsInteractionListener) mActivity;
        setHasOptionsMenu(true);
        intiUI();
    }

    private void intiUI(){
        String[] stringArray = (String[])getResources().getStringArray(R.array.koolo_settings);
        mSettingsListDetails = new ArrayList<String>(Arrays.asList(stringArray));
        mSettingsAdapter = new KooloSettingsAdapter(mSettingsListDetails, mContext);
        mSettingsList = (ListView)rootView.findViewById(R.id.settings_list);
        mSettingsList.setOnItemClickListener(this);
        mSettingsList.setAdapter(mSettingsAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        switch(position) {
            case 0:
                //This means Background Image is Clicked.
                bundle.putInt(KooloSettingsInteractionListener.KOOLO_SETTINGS_ACTION, KooloSettingsInteractionListener.KOOLO_BACKGROUND_IMAGE_ACTION);
                break;

            case 1:
                //This means Quotes is Clicked.
                bundle.putInt(KooloSettingsInteractionListener.KOOLO_SETTINGS_ACTION, KooloSettingsInteractionListener.KOOLO_PASSCODE_ACTION);
                break;

            case 2:
                //This means Passcode is Clicked.
                bundle.putInt(KooloSettingsInteractionListener.KOOLO_SETTINGS_ACTION, KooloSettingsInteractionListener.KOOLO_QUOTES_ACTION);
                break;

            case 3:
                //This means Passcode is Clicked.
                bundle.putInt(KooloSettingsInteractionListener.KOOLO_SETTINGS_ACTION, KooloSettingsInteractionListener.KOOLO_LICENSE_ACTION);
                break;

            case 4:
                //This means Review Of Applicatione is Clicked.
                bundle.putInt(KooloSettingsInteractionListener.KOOLO_SETTINGS_ACTION, KooloSettingsInteractionListener.KOOLO_HUMOUR_COLORS_ACTION);
                break;

            case 5:
                //This means Review Of Applicatione is Clicked.
                bundle.putInt(KooloSettingsInteractionListener.KOOLO_SETTINGS_ACTION, KooloSettingsInteractionListener.KOOLO_TUTORIAL_ACTION);
                break;

            case 6:
                //This means Review Of Applicatione is Clicked.
                bundle.putInt(KooloSettingsInteractionListener.KOOLO_SETTINGS_ACTION, KooloSettingsInteractionListener.KOOLO_UPDATES_ACTION);
                break;

            case 7:
                //This means Review Of Applicatione is Clicked.
                bundle.putInt(KooloSettingsInteractionListener.KOOLO_SETTINGS_ACTION, KooloSettingsInteractionListener.KOOLO_CONTRIBUTORS_ACTION);
                break;
        }
        mListener.onSettingsInteraction(bundle);
    }
}
