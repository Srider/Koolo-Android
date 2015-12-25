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
public class TransfersDataStore {

    private static final String TAG = TransfersDataStore.class.getSimpleName();
    private static File transfersFile;
    private static String transfersFileName;
    private static Context mContext;
    public static TransfersDataStore sharedTransfersDataStore = null;

    /**
     * Constructor used for initializing the QuotesDataStore.
     */
    private TransfersDataStore() {
        super();
    }

    /**
     * Static method used for instantiating the QuotesDataStore using constructor.
     * @return QuotesDataStore
     */
    public static TransfersDataStore getSharedTransfersDataStore(String transfersFileName, Context context) {
        if(sharedTransfersDataStore == null) {
            sharedTransfersDataStore = new TransfersDataStore();
            mContext = context;
            initializeTransfersFile(transfersFileName);
        }
        return sharedTransfersDataStore;
    }

    /**
     * Static method used for initializing the preferences file.
     * @param fileName
     */
    public static void initializeTransfersFile(String fileName) {
        transfersFileName = fileName;
        File sdCard = Environment.getExternalStorageDirectory();
        if(sdCard.exists() && sdCard.canRead()) {
            if(transfersFile == null || isTransfersFileAvailable()) {
                String filePath = sdCard.getAbsolutePath() + File.separator + transfersFileName;
                if (createTransfersFileAtPath(filePath)) {
                    Log.i(TAG, "Transfers File CREATED");
                } else {
                    Log.i(TAG, "Transfers File Not Available");
                }
            }
        }
    }

    /**
     * Static method used for getting the preferences file path.
     * @return String
     */
    public static String getSharedTransfersFilePath() {
        return transfersFile.getAbsolutePath();
    }

    /**
     *
     * @return boolean
     */
    public static boolean isTransfersFileReadable() {
        boolean readStatus = isTransfersFileAvailable()&&transfersFile.canRead();
        return (readStatus);
    }

    /**
     *
     * @return boolean
     */
    public static boolean isTransfersFileWritable() {
        boolean writeStatus = isTransfersFileAvailable()&&transfersFile.canWrite();
        return (writeStatus);
    }

    /**
     * Static method used for verifying if the the preferences file is readable or not.
     * @return boolean
     */
    private static boolean isTransfersFileAvailable() {
        return transfersFile.exists();
    }

    /**
     *
     * @return boolean
     */
    public static boolean createTransfersFileAtPath(String filePath){
        boolean creationStatus = false;
        transfersFile = new File(filePath);
        try {
            creationStatus = transfersFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return creationStatus;
    }


    /**
     * Static method used for reading the humours file is readable or not.
     * @return boolean
     */
    public static boolean writeTransfersToFile(List<Checklist> transfersList) {
        boolean writeStatus = false;
        clearTransfersFile();
        if(isTransfersFileWritable()) {
            FileOutputStream fos = null;
            try {
                fos = mContext.openFileOutput(transfersFileName, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                int i;
                for(i = 0; i<transfersList.size(); i++) {
                    Checklist tempChecklist = transfersList.get(i);
                    os.writeObject(tempChecklist);
                }
                os.close();
                fos.close();
                if(i == transfersList.size()) {
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
    private static void clearTransfersFile() {
        if(isTransfersFileWritable()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(transfersFile);
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
    public static List<Checklist> readTransfersFromFile() {
        List<Checklist>  transfersList = new ArrayList<Checklist>();
        if(isTransfersFileReadable()) {
            FileInputStream fis = null;
            try {
                fis = mContext.openFileInput(transfersFileName);
                ObjectInputStream istream = new ObjectInputStream(fis);

                while( fis.available() > 0) // check if the file stream is at the end
                {
                    Checklist tempTransfer = (Checklist) istream.readObject();
                    transfersList.add(tempTransfer); // read from the object stream,
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
        return transfersList;
    }
}
