package com.example.srravela.koolo.humor.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.srravela.koolo.entities.Humour;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by srravela on 11/19/2015.
 */
public class HumourDataStore {
    private static final String TAG = HumourDataStore.class.getSimpleName();
    private static File humoursFile;
    private static String humoursFileName;
    private static Context mContext;
    public static HumourDataStore sharedHumoursDataStore = null;

    /**
     * Constructor used for initializing the FileReaderAndWriter.
     */
    private HumourDataStore() {
        super();
    }

    /**
     * Static method used for instantiating the FileReaderAndWriter using constructor.
     * @param humoursFileName
     * @return FileReaderAndWriter
     */
    public static HumourDataStore getSharedHumoursDataStore(String humoursFileName, Context context) {

        if(sharedHumoursDataStore == null) {
            sharedHumoursDataStore = new HumourDataStore();
            mContext = context;
            initializeHumoursFile(humoursFileName);
        }
        return (HumourDataStore)sharedHumoursDataStore;
    }

    /**
     * Static method used for initializing the preferences file.
     * @param fileName
     */
    public static void initializeHumoursFile(String fileName) {
        humoursFileName = fileName;
        File sdCard = Environment.getExternalStorageDirectory();
        if(sdCard.exists() && sdCard.canRead()) {
            if(humoursFile == null || isHumoursFileAvailable()) {
                String filePath = sdCard.getAbsolutePath() + File.separator + humoursFileName;
                if (createQuotesFileAtPath(filePath)) {
                    Log.i(TAG, "Humours File CREATED");
                } else {
                    Log.i(TAG, "Humours File Not Available");
                }
            }
        }
    }

    /**
     * Static method used for getting the preferences file path.
     * @return String
     */
    public static String getSharedHumoursFilePath() {
        return humoursFile.getAbsolutePath();
    }

    /**
     *
     * @return boolean
     */
    public static boolean isHumoursFileReadable() {
        return (humoursFile.exists()&&humoursFile.canRead());
    }

    /**
     *
     * @return boolean
     */
    public static boolean isHumoursFileWritable() {
        return (humoursFile.exists()&&humoursFile.canWrite());
    }

    /**
     * Static method used for verifying if the the preferences file is readable or not.
     * @return boolean
     */
    private static boolean isHumoursFileAvailable() {
        boolean fileStatus = humoursFile.exists();
        return (fileStatus);
    }

    /**
     *
     * @return boolean
     */
    public static boolean createQuotesFileAtPath(String filePath){
        boolean creationStatus = false;
        humoursFile = new File(filePath);
        try {
            creationStatus = humoursFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return creationStatus;
    }

    /**
     * Static method used for reading the humours file is readable or not.
     * @return boolean
     */
    public static boolean writeHumoursToFile(List<Humour> humoursList) {
        boolean writeStatus = false;
        clearHumoursFile();
        if(isHumoursFileWritable()) {
            FileOutputStream fos = null;
            try {
                fos = mContext.openFileOutput(humoursFileName, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                int i;
                for(i = 0; i<humoursList.size(); i++) {
                    Humour tempHumour = humoursList.get(i);
                    os.writeObject(tempHumour);
                }
                os.close();
                fos.close();
                if(i == humoursList.size()) {
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
    private static void clearHumoursFile() {
        if(isHumoursFileWritable()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(humoursFile);
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
    public static List<Humour> readHumoursFromFile() {
        List<Humour> humoursList = null;
        if(isHumoursFileReadable()) {
            FileInputStream fis = null;
            try {
                fis = mContext.openFileInput(humoursFileName);
                ObjectInputStream istream = new ObjectInputStream(fis);
                humoursList = new ArrayList<Humour>();
                while( fis.available() > 0) // check if the file stream is at the end
                {
                    Humour tempHumour = (Humour) istream.readObject(); // read from the object stream,
                    humoursList.add(tempHumour);
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
        return humoursList;
    }
}
