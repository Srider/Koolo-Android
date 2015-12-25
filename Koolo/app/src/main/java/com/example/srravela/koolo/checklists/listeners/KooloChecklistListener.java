package com.example.srravela.koolo.checklists.listeners;

import android.os.Bundle;

/**
 * Created by srikar on 14/11/15.
 */
public interface KooloChecklistListener {
    public static final String KOOLO_CHECKLIST_ACTION="KOOLO_CHECKIST_ACTION";
    public static final String CHECKLIST_FRAGMENT_TYPE = "CHECKLIST_FRAGMENT_TYPE";
    public static final String CHECKLIST_ITEM_ENTRY_FRAGMENT_TYPE = "CHECKLIST_ITEM_ENTRY_FRAGMENT_TYPE";
    public static final int KOOLO_MYHEALTH_BUTTON_CLICKED=0;
    public static final int KOOLO_THREE_SENTENCE_BUTTON_CLICKED=1;
    public static final int KOOLO_READY_FOR_TRANSFER_BUTTON_CLICKED=2;
    public static final int ENTRY_CHECKLIST_NEW_GOAL_ITEM=3;
    public static final int ENTRY_CHECKLIST_NEW_TRANSFER_ITEM=4;
    public static final int THREE_QUESTIONS_ANSWERED = 5;
    public static final int ADD_NEW_GOAL_ITEM_CLICKED=6;
    public static final int ADD_NEW_TRANSFER_ITEM_CLICKED=7;
    public static final int KOOLO_INFO_BUTTON_CLICKED=8;
    public static final int KOOLO_CHECKLIST_POP_ACTION = 9;

    public void onChecklistInteraction(Bundle urlBundle);
}
