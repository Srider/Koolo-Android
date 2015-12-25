package com.example.srravela.koolo.moods.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.srravela.koolo.entities.MoodShot;

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
 * Created by srikar on 5/12/15.
 */
public class MoodsDataStore {
    private static final String TAG = MoodsDataStore.class.getSimpleName();
    private static File moodsFile;
    private static String moodsFileName;
    private static Context mContext;
    public static MoodsDataStore sharedMoodsDataStore = null;

    /**
     * Constructor used for initializing the MoodsDataStore.
     */
    private MoodsDataStore() {
        super();
    }

    /**
     * Static method used for instantiating the MoodsDataStore using constructor.
     * @param moodsFileName
     * @return MoodsDataStore
     */
    public static MoodsDataStore getSharedMoodsDataStore(String moodsFileName, Context context) {
        if(sharedMoodsDataStore == null) {
            sharedMoodsDataStore = new MoodsDataStore();
            mContext = context;
            initializeMoodsFile(moodsFileName);
        }
        return (MoodsDataStore)sharedMoodsDataStore;
    }

    /**
     * Static method used for initializing the preferences file.
     * @param fileName
     */
    public static void initializeMoodsFile(String fileName) {
        moodsFileName = fileName;
        File sdCard = Environment.getExternalStorageDirectory();
        if(sdCard.exists() && sdCard.canRead()) {
            if(moodsFile == null || isMoodsFileAvailable()) {
                String filePath = sdCard.getAbsolutePath() + File.separator + moodsFileName;
                if (createMoodsFileAtPath(filePath)) {
                    Log.i(TAG, "Moods File CREATED");
                } else {
                    Log.i(TAG, "Moods File Not Available");
                }
            }
        }
    }

    /**
     * Static method used for getting the preferences file path.
     * @return String
     */
    public static String getSharedMoodsFilePath() {
        return moodsFile.getAbsolutePath();
    }

    /**
     *
     * @return boolean
     */
    public static boolean isMoodsFileReadable() {
        boolean readStatus = isMoodsFileAvailable()&&moodsFile.canRead();
        return (readStatus);
    }

    /**
     *
     * @return boolean
     */
    public static boolean isMoodsFileWritable() {
        boolean writeStatus = isMoodsFileAvailable()&&moodsFile.canWrite();
        return (writeStatus);
    }

    /**
     * Static method used for verifying if the the preferences file is readable or not.
     * @return boolean
     */
    private static boolean isMoodsFileAvailable() {
        return moodsFile.exists();
    }

    /**
     *
     * @return boolean
     */
    public static boolean createMoodsFileAtPath(String filePath){
        boolean creationStatus = false;
        moodsFile = new File(filePath);
        try {
            creationStatus = moodsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return creationStatus;
    }


    /**
     * Static method used for reading the humours file is readable or not.
     * @return boolean
     */
    public static boolean writeMoodsToFile(List<MoodShot> moodsList) {
        boolean writeStatus = false;
        clearMoodsFile();
        if(isMoodsFileWritable()) {
            FileOutputStream fos = null;
            try {
                fos = mContext.openFileOutput(moodsFileName, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                int i;
                for(i = 0; i<moodsList.size(); i++) {
                    MoodShot tempMoodShot = moodsList.get(i);
                    os.writeObject(tempMoodShot);
                }
                os.close();
                fos.close();
                if(i == moodsList.size()) {
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
    private static void clearMoodsFile() {
        if(isMoodsFileWritable()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(moodsFile);
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
    public static List<MoodShot> readMoodsFromFile() {
        List<MoodShot> moodsList = null;
        if(isMoodsFileReadable()) {
            FileInputStream fis = null;
            try {
                fis = mContext.openFileInput(moodsFileName);
                ObjectInputStream istream = new ObjectInputStream(fis);
                moodsList = new ArrayList<MoodShot>();
                while( fis.available() > 0) // check if the file stream is at the end
                {
                    MoodShot tempMoodShot = (MoodShot) istream.readObject();
                    moodsList.add(tempMoodShot); // read from the object stream,
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
        return moodsList;
    }
}
