package com.example.srravela.koolo.checklists.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.srravela.koolo.entities.Checklist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by srikar on 22/11/15.
 */
public class GoalsDataStore {

    private static final String TAG = GoalsDataStore.class.getSimpleName();
    private static File goalsFile;
    private static String goalsFileName;
    private static Context mContext;
    public static GoalsDataStore sharedGoalsDataStore = null;

    /**
     * Constructor used for initializing the QuotesDataStore.
     */
    private GoalsDataStore() {
        super();
    }

    /**
     * Static method used for instantiating the QuotesDataStore using constructor.
     * @param goalsFileName
     * @return GoalsDataStore
     */
    public static GoalsDataStore getSharedGoalsDataStore(String goalsFileName, Context context) {
        if(sharedGoalsDataStore == null) {
            sharedGoalsDataStore = new GoalsDataStore();
            mContext = context;
            initializeGoalsFile(goalsFileName);
        }
        return sharedGoalsDataStore;
    }

    /**
     * Static method used for initializing the preferences file.
     * @param fileName
     */
    public static void initializeGoalsFile(String fileName) {
        goalsFileName = fileName;
        File sdCard = Environment.getExternalStorageDirectory();
        if(sdCard.exists() && sdCard.canRead()) {
            if(goalsFile == null || isGoalsFileAvailable()) {
                String filePath = sdCard.getAbsolutePath() + File.separator + goalsFileName;
//                String filePath = mContext.getFilesDir() + File.separator + goalsFileName;
                if (createGoalsFileAtPath(filePath)) {
                    Log.i(TAG, "GOALS File CREATED");
                } else {
                    Log.i(TAG, "GOALS File Not Available");
                }
            }
        }
    }

    /**
     * Static method used for getting the preferences file path.
     * @return String
     */
    public static String getSharedGoalsFilePath() {
        return goalsFile.getAbsolutePath();
    }

    /**
     *
     * @return boolean
     */
    public static boolean isGoalsFileReadable() {
        boolean readStatus = isGoalsFileAvailable() &&goalsFile.canRead();
        return (readStatus);
    }

    /**
     *
     * @return boolean
     */
    public static boolean isGoalsFileWritable() {
        boolean writeStatus = isGoalsFileAvailable() &&goalsFile.canWrite();
        return (writeStatus);
    }

    /**
     * Static method used for verifying if the the preferences file is readable or not.
     * @return boolean
     */
    private static boolean isGoalsFileAvailable() {
        return goalsFile.exists();
    }

    /**
     *
     * @return boolean
     */
    public static boolean createGoalsFileAtPath(String filePath){
        boolean creationStatus = false;
        goalsFile = new File(filePath);
        try {
            creationStatus = goalsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return creationStatus;
    }


    /**
     * Static method used for reading the humours file is readable or not.
     * @return boolean
     */
    public static boolean writeGoalsToFile(List<Checklist> goalsList) {
        boolean writeStatus = false;
        clearGoalsFile();
        if(isGoalsFileWritable()) {
            FileOutputStream fos = null;
            try {
                fos = mContext.openFileOutput(goalsFileName, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                int i;
                for(i = 0; i<goalsList.size(); i++) {
                    Checklist tempChecklist = goalsList.get(i);
                    os.writeObject(tempChecklist);
                }
                os.close();
                fos.close();
                if(i == goalsList.size()) {
                    writeStatus = true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return writeStatus;
    }


    /**
     * Static method used for verifying if the the preferences file is readable or not.
     * @return boolean
     */
    private static void clearGoalsFile() {
        if(isGoalsFileWritable()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(goalsFile);
                writer.print("");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Static method used for verifying if the the preferences file is readable or not.
     * @return boolean
     */
    public static List<Checklist> readGoalsFromFile() {
        List<Checklist> goalsList = new ArrayList<Checklist>();;
        if(isGoalsFileReadable()) {
            FileInputStream fis = null;
            try {
                fis = mContext.openFileInput(goalsFileName);
                ObjectInputStream istream = new ObjectInputStream(fis);

                while( fis.available() > 0) // check if the file stream is at the end
                {
                    Checklist tempChecklist = (Checklist) istream.readObject();
                    goalsList.add(tempChecklist); // read from the object stream,
                }
                istream.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return goalsList;
    }
}
