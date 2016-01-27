package com.example.srravela.koolo.checklists.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.listeners.ChecklistItemsUpdatedListener;
import com.example.srravela.koolo.checklists.utils.GoalsDataStore;
import com.example.srravela.koolo.checklists.utils.OnSwipeTouchListener;
import com.example.srravela.koolo.checklists.utils.TransfersDataStore;
import com.example.srravela.koolo.entities.Checklist;
import com.example.srravela.koolo.entities.Utils;

import java.util.List;

/**
 * Created by srikar on 23/11/15.
 */
public class KooloChecklistAdapter  extends BaseAdapter implements View.OnClickListener{

    private static final String TAG = KooloChecklistAdapter.class.getSimpleName();
    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    public List<Checklist> items = null;
    private boolean isGoals;
    LayoutInflater layoutInflater;
    public Context context;
    private ChecklistItemsUpdatedListener mChecklistItemsUpdatedListener;


    /**
     * Constructor used for initializing the KooloChecklistItemAdapter.
     * @param context
     * @param items
     */
    public KooloChecklistAdapter(List<Checklist> items, Context context, boolean isGoals,ChecklistItemsUpdatedListener updateListener ) {
        super();
        this.items = items;
        this.context = context;
        this.isGoals = isGoals;
        this.mChecklistItemsUpdatedListener = updateListener;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * Method that returns the dropdown view.
     * @param position
     * @param convertView
     * @param parent
     * @return itemView
     */
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View itemView = getView(position, convertView, parent);
        return itemView;
    }

    /**
     * Method that returns the item view type.
     * @param position
     * @return position
     */
    public int getItemViewType(int position) {
        return 0;
    }

    /**
     * Method that returns the view type count.
     * @return int
     */
    public int getViewTypeCount() {
        return 1;
    }

    /**
     * Method that returns the number of items.
     * @return int
     */
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= layoutInflater.inflate(R.layout.view_checklist_item, null);
        TextView checklistText = (TextView)convertView.findViewById(R.id.checklist_text);
        checklistText.setTag(Integer.valueOf(position));
        checklistText.setOnTouchListener(new OnSwipeTouchListener(position) {
            public void onSwipeRight(int cellPosition) {
                updateStatus(cellPosition);
            }

            public void onSwipeTop(int cellPosition) {
            }

            public void onSwipeLeft(int cellPosition) {
            }

            public void onSwipeBottom(int cellPosition) {
            }

        });

        Button statusView = (Button)convertView.findViewById(R.id.checklist_status_button);
        statusView.setTag(Integer.valueOf(position));
        statusView.setOnClickListener(this);

        Checklist item = items.get(position);
        String checklistItemText = item.getItemText();
        if(checklistItemText!= null) {
            checklistText.setText(checklistItemText);
        } else {
            checklistText.setText("Unknown");
        }

        Utils.StatusType statusType = item.getStatusType();
        switch (statusType) {
            case FINISHED:
                statusView.setBackgroundResource(R.drawable.green);
                break;
            case NOT_DONE:
                statusView.setBackgroundResource(R.drawable.red);
                break;
            case ONGOING:
                statusView.setBackgroundResource(R.drawable.yellow);
                break;
            case UNCOUNTED:
                statusView.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
        return convertView;
    }

    /**
     * Method to check whether if the list is empty or not.
     * @return boolean
     */
    public boolean isEmpty() {
        return getCount() == 0;
    }

    void onClick() {

    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        Checklist modifiedChecklist = items.get(position.intValue());
        Utils.StatusType newStatus = null;
        if(modifiedChecklist.getStatusType()!=Utils.StatusType.FINISHED ) {
            switch (modifiedChecklist.getStatusType()) {
                case ONGOING:
                    newStatus = Utils.StatusType.FINISHED;
                    break;
                case NOT_DONE:
                    newStatus = Utils.StatusType.ONGOING;
                    break;
            }
            modifiedChecklist.setStatusType(newStatus);

            items.set(position.intValue(), modifiedChecklist);
            items = Utils.getSharedUtils(context).sortChecklistItems(items);
            if (isGoals) {
                GoalsDataStore sharedGoalsDataStore = GoalsDataStore.getSharedGoalsDataStore(context.getResources().getString(R.string.goals_file_name), context);
                //First Get transfers from file.
                if (sharedGoalsDataStore.writeGoalsToFile(items)) {
                    mChecklistItemsUpdatedListener.onChecklistItemsUpdated(items);
                }
            } else {
                TransfersDataStore sharedTransfersDataStore = TransfersDataStore.getSharedTransfersDataStore(context.getResources().getString(R.string.transfers_file_name), context);
                //First Get transfers from file.
                if (sharedTransfersDataStore.writeTransfersToFile(items)) {
                    mChecklistItemsUpdatedListener.onChecklistItemsUpdated(items);
                }
            }
        }
    }

    public void updateStatus(int position) {
        Checklist modifiedChecklist = items.get(position);
        if(modifiedChecklist.getStatusType()!= Utils.StatusType.UNCOUNTED) {
            Utils.StatusType newStatus = Utils.StatusType.UNCOUNTED;
            modifiedChecklist.setStatusType(newStatus);
            items.set(position, modifiedChecklist);
            items = Utils.getSharedUtils(context).sortChecklistItems(items);
            if (isGoals) {
                GoalsDataStore sharedGoalsDataStore = GoalsDataStore.getSharedGoalsDataStore(context.getResources().getString(R.string.goals_file_name), context);
                //First Get transfers from file.
                if (sharedGoalsDataStore.writeGoalsToFile(items)) {
                    mChecklistItemsUpdatedListener.onChecklistItemsUpdated(items);
                }
            } else {
                TransfersDataStore sharedTransfersDataStore = TransfersDataStore.getSharedTransfersDataStore(context.getResources().getString(R.string.transfers_file_name), context);
                //First Get transfers from file.
                if (sharedTransfersDataStore.writeTransfersToFile(items)) {
                    mChecklistItemsUpdatedListener.onChecklistItemsUpdated(items);
                }
            }
        }
    }
}
