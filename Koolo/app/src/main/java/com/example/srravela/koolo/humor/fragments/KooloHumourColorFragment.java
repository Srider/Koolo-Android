package com.example.srravela.koolo.humor.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.entities.Humour;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.humor.activities.KooloHumourActivity;
import com.example.srravela.koolo.humor.listeners.HumourTextUpdatedListener;
import com.example.srravela.koolo.humor.utils.HumourDataStore;
import com.example.srravela.koolo.moods.activities.KooloMoodsActivity;
import com.example.srravela.koolo.humor.adapters.KooloHumourColorAdapter;
import com.example.srravela.koolo.humor.listeners.KooloHumourColorListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KooloHumourColorFragment extends Fragment implements HumourTextUpdatedListener {
    private View rootView;
    ListView hunourColorsList;
    KooloHumourColorAdapter humourColorAdapter;
    List<Humour> humourDetails;
    private Context mContext;
    private KooloHumourActivity mActivity;
    public static KooloHumourColorFragment fragmentKooloHumorColor;
    private KooloHumourColorListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloHumourColorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloHumourColorFragment newInstance() {
        KooloHumourColorFragment fragment = new KooloHumourColorFragment();
        return fragment;
    }

    public KooloHumourColorFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_humour_color, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=(KooloHumourActivity)getActivity();
        mContext=mActivity.getApplicationContext();
        Bundle humoursBundle = getArguments();
        if(humoursBundle!=null) {
            humourDetails = (List<Humour>) humoursBundle.getSerializable("Humours");
        }
        initUI();
    }

    /**
     * Method for configuring the UI
     */
    private void initUI(){
        setHasOptionsMenu(true);
        hunourColorsList=(ListView)rootView.findViewById(R.id.humour_detail);
        if(humourDetails!=null){
            humourColorAdapter = new KooloHumourColorAdapter(humourDetails, mContext, this);
            hunourColorsList.setAdapter(humourColorAdapter);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onHumourTextUpdated(List<Humour> updatedHumoursList) {
        humourDetails = updatedHumoursList;
        HumourDataStore sharedHumoursDataStore = HumourDataStore.getSharedHumoursDataStore(mContext.getResources().getString(R.string.humours_file_name), mContext);
        sharedHumoursDataStore.writeHumoursToFile(humourDetails);
        humourColorAdapter.notifyDataSetChanged();
    }
}
