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
import android.widget.TextView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.checklists.utils.GoalsDataStore;
import com.example.srravela.koolo.checklists.utils.TransfersDataStore;
import com.example.srravela.koolo.entities.Checklist;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.home.listeners.KooloHomeInteractionListener;

import java.util.List;

public class KooloChecklistOptionsFragment extends Fragment implements View.OnClickListener{
    private ImageView backgroundImageView;

    private Context mContext;
    private Activity mActivity;
    Button mMyHealth;
    Button mThreeSentenceButton;
    Button mTransferButton;
    private View rootView;
    private KooloChecklistListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloChecklistOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloChecklistOptionsFragment newInstance() {
        KooloChecklistOptionsFragment fragment = new KooloChecklistOptionsFragment();
        return fragment;
    }

    public KooloChecklistOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_koolo_checklist_options,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=getActivity();
        mContext=mActivity.getApplicationContext();
        mListener =(KooloChecklistListener) mActivity;
        setHasOptionsMenu(true);
    }

    private void intiUI(){

        backgroundImageView = (ImageView)rootView.findViewById(R.id.checklist_background_image);
        SharedPreferences backgroundSharedPreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
        SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);

        if(backgroundImageFlagPreferences.getBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, false)) {
            Uri myUri = Uri.parse(backgroundSharedPreferences.getString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, KooloApplication.getImageUri()));
            backgroundImageView.setImageURI(myUri);
        } else {
            backgroundImageView.setImageResource(R.drawable.background);
        }

        mMyHealth  = (Button) rootView.findViewById(R.id.health_button);
        mMyHealth.setText("My Health"+"\n"+Utils.getSharedUtils(mContext).getItemsCount(true));
        mMyHealth.setOnClickListener(this);

        mThreeSentenceButton  = (Button) rootView.findViewById(R.id.sentence_button);
        mThreeSentenceButton.setOnClickListener(this);

        mTransferButton  = (Button) rootView.findViewById(R.id.trasnfer_button);
        mTransferButton.setText("My Transfers" + "\n" + Utils.getSharedUtils(mContext).getItemsCount(false));
        mTransferButton.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        intiUI();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.health_button:
                triggerMyGoalsEvent();
                break;
            case R.id.sentence_button:
                triggerThreeSentenceEvent();
                break;
            case R.id.trasnfer_button:
                triggerTransferEvent();
                break;
        }
    }

    void triggerMyGoalsEvent() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.KOOLO_MYHEALTH_BUTTON_CLICKED);
        mListener.onChecklistInteraction(bundle);
    }

    void triggerThreeSentenceEvent() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.KOOLO_THREE_SENTENCE_BUTTON_CLICKED);
        mListener.onChecklistInteraction(bundle);
    }
    void triggerTransferEvent() {
        Bundle bundle = new Bundle();
        bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.KOOLO_READY_FOR_TRANSFER_BUTTON_CLICKED);
        mListener.onChecklistInteraction(bundle);
    }
}
