package com.example.srravela.koolo.checklists.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.fragments.KooloChecklistFragment;
import com.example.srravela.koolo.checklists.fragments.KooloInformationFragment;
import com.example.srravela.koolo.checklists.fragments.KooloNewChecklistItemEntryFragment;
import com.example.srravela.koolo.checklists.fragments.KooloChecklistOptionsFragment;
import com.example.srravela.koolo.checklists.fragments.KooloThreeSentenceFragment;
import com.example.srravela.koolo.checklists.listeners.ChecklistItemsUpdatedListener;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.entities.Checklist;

import java.util.List;


public class KooloChecklistActivity extends KooloBaseActivity implements KooloChecklistListener {

    private static final String TAG = KooloChecklistActivity.class.getSimpleName();
    private FrameLayout kooloChecklistFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_checklist);
        mContext=getApplicationContext();
        getSupportActionBar().show();
        getSupportActionBar().setTitle(getResources().getString(R.string.checklist_title));
        initUI();
    }

    void initUI() {
        loadChecklistOptionsFragment();
    }


    private void loadChecklistOptionsFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloChecklistOptionsFragment.newInstance();
        transaction.replace(R.id.fragment_checklist_container, fragment, "koolochecklistoptionsfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onChecklistInteraction(Bundle urlBundle) {
        int action = (int)urlBundle.get(KooloChecklistListener.KOOLO_CHECKLIST_ACTION);
        switch(action) {
            case KOOLO_MYHEALTH_BUTTON_CLICKED:
                loadGoalsOrTransferFragment(true);
                break;
            case KOOLO_THREE_SENTENCE_BUTTON_CLICKED:
                loadThreeSentenceFragment();
                break;
            case KOOLO_READY_FOR_TRANSFER_BUTTON_CLICKED:
                loadGoalsOrTransferFragment(false);
                break;
            case ENTRY_CHECKLIST_NEW_GOAL_ITEM:
                loadNewGoalOrTransferEntryItem(true);
                break;
            case ENTRY_CHECKLIST_NEW_TRANSFER_ITEM:
                loadNewGoalOrTransferEntryItem(false);
                break;
            case THREE_QUESTIONS_ANSWERED:
                popAction();
                break;
            case KOOLO_INFO_BUTTON_CLICKED:
                loadInfoFragment();
                break;
            case KOOLO_CHECKLIST_POP_ACTION:
                popAction();
                break;
        }
    }

    private void loadGoalsOrTransferFragment(boolean isGoalsTriggered) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment = KooloChecklistFragment.newInstance();
        Bundle checklistTypeBundle = new Bundle();
        checklistTypeBundle.putBoolean(KooloChecklistListener.CHECKLIST_FRAGMENT_TYPE,isGoalsTriggered);
        fragment.setArguments(checklistTypeBundle);
        transaction.replace(R.id.fragment_checklist_container, fragment, "koologoalsfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadThreeSentenceFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment=KooloThreeSentenceFragment.newInstance();
        transaction.replace(R.id.fragment_checklist_container, fragment, "koolothreesentencefragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadNewGoalOrTransferEntryItem(boolean isGoalsEntryTriggered) {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment = KooloNewChecklistItemEntryFragment.newInstance();
        Bundle checklistTypeBundle = new Bundle();
        checklistTypeBundle.putBoolean(KooloChecklistListener.CHECKLIST_ITEM_ENTRY_FRAGMENT_TYPE, isGoalsEntryTriggered);
        fragment.setArguments(checklistTypeBundle);
        transaction.replace(R.id.fragment_checklist_container, fragment, "kooloitementryfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void loadInfoFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment = KooloInformationFragment.newInstance();
        transaction.replace(R.id.fragment_checklist_container, fragment, "kooloinfofragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    public void popAction() {
        if(getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
