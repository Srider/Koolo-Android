package com.example.srravela.koolo.calendar.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.calendar.fragments.KooloCalendarFragment;
import com.example.srravela.koolo.calendar.listeners.KooloCalendarInteractionListener;
import com.example.srravela.koolo.checklists.fragments.KooloChecklistOptionsFragment;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;

public class KooloCalendarActivity extends KooloBaseActivity implements KooloCalendarInteractionListener{
    private static final String TAG = KooloCalendarActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_calendar);
        mContext=getApplicationContext();
        getSupportActionBar().show();
        getSupportActionBar().setTitle(getResources().getString(R.string.checklist_title));
        initUI();
    }

    void initUI() {
        loadCalendarFragment();
    }


    private void loadCalendarFragment() {
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloCalendarFragment.newInstance();
        transaction.replace(R.id.fragment_calendar_container, fragment, "koolocalendarfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCalendarInteraction(Bundle urlBundle) {
        int action = (int)urlBundle.get(KooloCalendarInteractionListener.KOOLO_CALENDAR_ACTION);
        switch(action) {
//            case KOOLO_MYHEALTH_BUTTON_CLICKED:
//                loadGoalsOrTransferFragment(true);
//                break;
//            case KOOLO_THREE_SENTENCE_BUTTON_CLICKED:
//                loadThreeSentenceFragment();
//                break;
//            case KOOLO_READY_FOR_TRANSFER_BUTTON_CLICKED:
//                loadGoalsOrTransferFragment(false);
//                break;
//            case ENTRY_CHECKLIST_NEW_GOAL_ITEM:
//                loadNewGoalOrTransferEntryItem(true);
//                break;
//            case ENTRY_CHECKLIST_NEW_TRANSFER_ITEM:
//                loadNewGoalOrTransferEntryItem(false);
//                break;
//            case THREE_QUESTIONS_ANSWERED:
//                popAction();
//                break;
//            case KOOLO_INFO_BUTTON_CLICKED:
//                loadInfoFragment();
//                break;
//            case KOOLO_CHECKLIST_POP_ACTION:
//                popAction();
//                break;
        }
    }
}
