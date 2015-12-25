package com.example.srravela.koolo.checklists.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import com.example.srravela.koolo.checklists.activities.KooloChecklistActivity;
import com.example.srravela.koolo.checklists.adapters.KooloChecklistAdapter;
import com.example.srravela.koolo.checklists.listeners.ChecklistItemsUpdatedListener;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.checklists.utils.GoalsDataStore;
import com.example.srravela.koolo.checklists.utils.TransfersDataStore;
import com.example.srravela.koolo.entities.Checklist;
import com.example.srravela.koolo.entities.Utils;

import java.util.List;

public class KooloChecklistFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, ChecklistItemsUpdatedListener {
    private static final String TAG = KooloChecklistFragment.class.getSimpleName();
    private ImageView backgroundImageView;
    private View rootView;
    private Boolean isGoalsFragment;
    ListView checklist;
    KooloChecklistAdapter checklistAdapter;
    List<Checklist> checklistItems = null;
    private Button infoButton;
    private Context mContext;
    private KooloChecklistActivity mActivity;
    public static KooloChecklistFragment fragmentChecklist;
    private KooloChecklistListener mListener;
    private Bundle checklistFragmentTypeBundle;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ChecklistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloChecklistFragment newInstance() {
        KooloChecklistFragment fragment = new KooloChecklistFragment();
        return fragment;
    }

    public KooloChecklistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_checklist, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=(KooloChecklistActivity)getActivity();
        mListener = mActivity;
        mContext=mActivity.getApplicationContext();

    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    /**
     * Method for configuring the UI
     */
    private void initUI(){
        setHasOptionsMenu(true);

        checklistFragmentTypeBundle=getArguments();
        if(checklistFragmentTypeBundle!=null){
            isGoalsFragment=checklistFragmentTypeBundle.getBoolean(KooloChecklistListener.CHECKLIST_FRAGMENT_TYPE);
        }
        if(isGoalsFragment) {
            mActivity.getSupportActionBar().setTitle(getResources().getString(R.string.my_health));
        } else {
            mActivity.getSupportActionBar().setTitle(getResources().getString(R.string.ready_to_transfer));
        }

        //Image
        backgroundImageView = (ImageView)rootView.findViewById(R.id.checklist_background_image);

        SharedPreferences backgroundSharedPreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
        SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);
        if(backgroundImageFlagPreferences.getBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, false)) {
            Uri myUri = Uri.parse(backgroundSharedPreferences.getString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, KooloApplication.getImageUri()));
            backgroundImageView.setImageURI(myUri);
        } else {
            backgroundImageView.setImageResource(R.drawable.background);
        }

        //Button
        infoButton = (Button) rootView.findViewById(R.id.info_button);
        infoButton.setOnClickListener(this);

        //Checklist
        checklist=(ListView)rootView.findViewById(R.id.checklist_detail);
        if(isGoalsFragment) {
            checklistItems= loadGoals();
        } else {
            checklistItems = loadTransfers();
        }
        if(checklistItems!=null){
            checklistAdapter = new KooloChecklistAdapter(checklistItems, mContext, isGoalsFragment, this);
            checklist.setAdapter(checklistAdapter);
            checklist.setOnItemClickListener(this);
        } else {
            Log.i(TAG, "NO ITEMS");
        }
    }

    //TODO:
    private List<Checklist> loadGoals() {
        List<Checklist> goalsList = null;
        GoalsDataStore sharedGoalsDataStore;
        if(goalsList == null) {
            sharedGoalsDataStore = GoalsDataStore.getSharedGoalsDataStore(mContext.getResources().getString(R.string.goals_file_name), mContext);
            //First Get transfers from file.
            goalsList = sharedGoalsDataStore.readGoalsFromFile();
        }
        return Utils.getSharedUtils(mContext).sortChecklistItems(goalsList);
    }

    //TODO: Delete after integration
    private List<Checklist> loadTransfers() {
        List<Checklist> transfersList = null;
        TransfersDataStore sharedTransfersDataStore;
        if(transfersList == null) {
            sharedTransfersDataStore = TransfersDataStore.getSharedTransfersDataStore(mContext.getResources().getString(R.string.transfers_file_name), mContext);
            //First Get transfers from file.
            transfersList = sharedTransfersDataStore.readTransfersFromFile();
        }
        return Utils.getSharedUtils(mContext).sortChecklistItems(transfersList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        if(isGoalsFragment) {
            inflater.inflate(R.menu.menu_koolo_goals_options, menu);
        } else {
            inflater.inflate(R.menu.menu_koolo_transfers_options, menu);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle=new Bundle();
        switch (item.getItemId()){
            case R.id.transfer_option:
                bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.ENTRY_CHECKLIST_NEW_TRANSFER_ITEM);
                break;
            case R.id.goal_option:
                bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.ENTRY_CHECKLIST_NEW_GOAL_ITEM);
                break;
        }
        mListener.onChecklistInteraction(bundle);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.info_button:
                triggerInfoEvent();
                break;
        }
    }

    private void triggerInfoEvent() {
        Bundle bundle=new Bundle();
        bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.KOOLO_INFO_BUTTON_CLICKED);
        mListener.onChecklistInteraction(bundle);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


    @Override
    public void onChecklistItemsUpdated(List<Checklist>items) {
        checklistItems = items;
        checklistAdapter.notifyDataSetChanged();
    }
}
