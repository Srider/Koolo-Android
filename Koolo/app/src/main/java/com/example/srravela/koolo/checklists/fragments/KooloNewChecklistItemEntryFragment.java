package com.example.srravela.koolo.checklists.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.activities.KooloChecklistActivity;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.checklists.utils.GoalsDataStore;
import com.example.srravela.koolo.checklists.utils.TransfersDataStore;
import com.example.srravela.koolo.entities.Checklist;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.quotes.activities.KooloQuotesActivity;

import java.util.ArrayList;
import java.util.List;


public class KooloNewChecklistItemEntryFragment extends Fragment implements EditText.OnEditorActionListener {
    public static final String TAG=KooloNewChecklistItemEntryFragment.class.getSimpleName();
    private ImageView backgroundImageView;
    private View rootView;
    private Boolean isGoalsEntryFragment;
    private Context mContext;
    private KooloChecklistActivity mActivity;
    public static KooloNewChecklistItemEntryFragment fragmentChecklistItemEntry;
    private KooloChecklistListener mListener;
    private Bundle checklistFragmentEntryTypeBundle;
    EditText editTextChecklistItemEntry;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloNewChecklistItemEntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloNewChecklistItemEntryFragment newInstance() {
        KooloNewChecklistItemEntryFragment fragment = new KooloNewChecklistItemEntryFragment();
        return fragment;
    }

    public KooloNewChecklistItemEntryFragment() {
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
        rootView=inflater.inflate(R.layout.fragment_koolo_new_checklist_item_entry,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity=(KooloChecklistActivity)getActivity();
        mContext=mActivity.getApplicationContext();
        mListener = mActivity;
        checklistFragmentEntryTypeBundle=getArguments();
        if(checklistFragmentEntryTypeBundle!=null){
            isGoalsEntryFragment=checklistFragmentEntryTypeBundle.getBoolean(KooloChecklistListener.CHECKLIST_ITEM_ENTRY_FRAGMENT_TYPE);
        }
        if(isGoalsEntryFragment) {
            mActivity.getSupportActionBar().setTitle(getResources().getString(R.string.new_goal_options));
        } else {
            mActivity.getSupportActionBar().setTitle(getResources().getString(R.string.new_transfer_options));
        }
        initUI();
    }

    /**
     * Method for configuring the UI
     */
    private void initUI(){
        setHasOptionsMenu(true);
        backgroundImageView = (ImageView)rootView.findViewById(R.id.checklist_background_image);

        editTextChecklistItemEntry = (EditText)rootView.findViewById(R.id.edit_text_checklist_item);
        editTextChecklistItemEntry.setOnEditorActionListener(this);
        if(isGoalsEntryFragment) {
            editTextChecklistItemEntry.setHint("Enter New Goal");
        } else {
            editTextChecklistItemEntry.setHint("Enter New Transfer");
        }
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        if(isGoalsEntryFragment) {
            inflater.inflate(R.menu.menu_koolo_add_goal, menu);
        } else {
            inflater.inflate(R.menu.menu_koolo_add_transfer, menu);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Bundle bundle=new Bundle();
//        switch (item.getItemId()){
//            case R.id.add_new_transfer_option:
//                bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.ADD_NEW_TRANSFER_ITEM_CLICKED);
//                break;
//            case R.id.add_new_goal_option:
//                bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.ADD_NEW_GOAL_ITEM_CLICKED);
//                break;
//        }
//        mListener.onChecklistInteraction(bundle);
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle=new Bundle();
        switch (item.getItemId()){
            case R.id.add_new_transfer_option:
                onDoneAction(editTextChecklistItemEntry.getText().toString());
                break;
            case R.id.add_new_goal_option:
                onDoneAction(editTextChecklistItemEntry.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onDoneAction(v.getText().toString());
        }
        return false;
    }


    private void onDoneAction(String itemText) {
        Bundle bundle = new Bundle();
        if(itemText!= null&&!(itemText.isEmpty())) {
            Checklist newChecklist = new Checklist(itemText, Utils.StatusType.NOT_DONE);
            List<Checklist> checklistItems = null;
            if (isGoalsEntryFragment) {
                GoalsDataStore sharedGoalsDataStore = GoalsDataStore.getSharedGoalsDataStore(mContext.getResources().getString(R.string.goals_file_name), mContext);
                checklistItems = sharedGoalsDataStore.readGoalsFromFile();
                if(checklistItems == null) {
                    checklistItems = new ArrayList<Checklist>();
                }
                checklistItems.add(newChecklist);
                if(sharedGoalsDataStore.writeGoalsToFile(checklistItems)) {
                    Log.i(TAG, "GOAL WRITE SUCCESS");
                } else {
                    Log.i(TAG, "GOAL WRITE FAILED");
                }
                bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.KOOLO_CHECKLIST_POP_ACTION);
            } else {
                TransfersDataStore sharedTransfersDataStore = TransfersDataStore.getSharedTransfersDataStore(mContext.getResources().getString(R.string.transfers_file_name), mContext);
                checklistItems = sharedTransfersDataStore.readTransfersFromFile();
                if(checklistItems == null) {
                    checklistItems = new ArrayList<Checklist>();
                }
                checklistItems.add(newChecklist);
                if(sharedTransfersDataStore.writeTransfersToFile(checklistItems)) {
                    Log.i(TAG, "TRANSFER WRITE SUCCESS");
                } else {
                    Log.i(TAG, "TRANSFER WRITE FAILED");
                }
                bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.KOOLO_CHECKLIST_POP_ACTION);
            }
            mListener.onChecklistInteraction(bundle);
        } else {
            String itemType = null;
            if(isGoalsEntryFragment) {
                itemType = "Goal";
            } else {
                itemType = "Transfer";
            }
            AlertDialog alertDialog = new AlertDialog.Builder((KooloChecklistActivity)getActivity()).create();
            alertDialog.setTitle("New "+itemType+" Alert");
            alertDialog.setMessage(itemType+" cannot be empty !");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }
}
