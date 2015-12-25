package com.example.srravela.koolo.entities;

import android.content.Context;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.utils.GoalsDataStore;
import com.example.srravela.koolo.checklists.utils.TransfersDataStore;
import com.example.srravela.koolo.moods.utils.MoodsDataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srravela on 11/16/2015.
 */
public class Utils {
    public static Context mContext;
    public enum StatusType {
        FINISHED, NOT_DONE, ONGOING, UNCOUNTED};
    public enum ColorType {
        RED, GREEN, YELLOW, ORANGE, MAGENTA, DARK_GREY, GREY, BLACK, BROWN, VIOLET, CYAN, BLUE, PINK};
    public static Utils sharedUtils = null;

    /**
     * Constructor for StandardServiceManager
     * @param context
     */
    private Utils(Context context) {
        super();
        mContext=context;
    }

    /**
     * Singleton instance for StandardServiceManager
     * @param context
     */
    public static Utils getSharedUtils(Context context) {
        if(sharedUtils == null) {
            sharedUtils = new Utils(context);
        }
        return (Utils)sharedUtils;
    }

    public static List<Checklist> sortChecklistItems(List<Checklist> oldChecklist) {
        List<Checklist> newChecklist = new ArrayList<Checklist>();
        if(!oldChecklist.isEmpty()) {
            for(int i = 0;i<4&&(newChecklist.size()<oldChecklist.size());i++) {
                for(Checklist checklist : oldChecklist) {
                    if(i==0) {
                        if(checklist.getStatusType()== StatusType.NOT_DONE) {
                            newChecklist.add(checklist);
                        }
                    } else if(i == 1) {
                        if(checklist.getStatusType()== StatusType.ONGOING) {
                            newChecklist.add(checklist);
                        }
                    } else if (i == 2) {
                        if(checklist.getStatusType()== StatusType.FINISHED) {
                            newChecklist.add(checklist);
                        }
                    } else {
                        if(checklist.getStatusType()== StatusType.UNCOUNTED) {
                            newChecklist.add(checklist);
                        }
                    }
                }
            }
        }
        return newChecklist;
    }


    //TODO:
    public static String getItemsCount(boolean isGoals) {
        List<Checklist> itemsList = null;
        int upperCount = 0;
        int lowerCount = 0;
        if (itemsList == null) {
            if (isGoals) {
                GoalsDataStore sharedGoalsDataStore = GoalsDataStore.getSharedGoalsDataStore(mContext.getResources().getString(R.string.goals_file_name), mContext);
                //First Get transfers from file.
                itemsList = sharedGoalsDataStore.readGoalsFromFile();
            } else {
                TransfersDataStore sharedTransfersDataStore = TransfersDataStore.getSharedTransfersDataStore(mContext.getResources().getString(R.string.transfers_file_name), mContext);
                //First Get transfers from file.
                itemsList = sharedTransfersDataStore.readTransfersFromFile();
            }
            for(Checklist tempItem : itemsList) {
                if(tempItem.getStatusType() != Utils.StatusType.UNCOUNTED) {
                    lowerCount+=1;
                }
                if(tempItem.getStatusType() == Utils.StatusType.FINISHED) {
                    upperCount +=1;
                }
            }
         }

        return ""+upperCount+"/"+lowerCount;
    }

    public static boolean isMoodShotAlreadyExists(MoodShot newMoodShot) {
        boolean status  = false;
        List<MoodShot> moodsList = null;
        MoodsDataStore sharedMoodsDataStore = MoodsDataStore.getSharedMoodsDataStore(mContext.getResources().getString(R.string.moods_file_name), mContext);

        //First Get Moods from file.
        moodsList = sharedMoodsDataStore.readMoodsFromFile();

        if(moodsList != null && (moodsList.size() >0)) {
            for(MoodShot tempMoodShot : moodsList) {
                if(tempMoodShot.getMoodCaptureUri()==newMoodShot.getMoodCaptureUri()) {
                    status = true;
                    break;
                }
            }
        }
       return status;
    }

    //TODO:
    public static List<MoodShot> loadMoods() {
        List<MoodShot> moodsList = null;
        if(moodsList == null) {
            MoodsDataStore sharedMoodsDataStore = MoodsDataStore.getSharedMoodsDataStore(mContext.getResources().getString(R.string.moods_file_name), mContext);
            //First Get transfers from file.
            moodsList = sharedMoodsDataStore.readMoodsFromFile();
        }
        return moodsList;
    }

    public static void addMoodShotListToDataStore(List<MoodShot>moodshotList) {
        MoodsDataStore sharedMoodsDataStore = MoodsDataStore.getSharedMoodsDataStore(mContext.getResources().getString(R.string.moods_file_name), mContext);
        sharedMoodsDataStore.writeMoodsToFile(moodshotList);
    }

}
