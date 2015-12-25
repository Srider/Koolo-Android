package com.example.srravela.koolo.quotes.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.srravela.koolo.entities.Quotes;

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
 * Created by srravela on 11/19/2015.
 */
public class QuotesDataStore {
    private static final String TAG = QuotesDataStore.class.getSimpleName();
    private static File quotesFile;
    private static String quotesFileName;
    private static Context mContext;
    public static QuotesDataStore sharedQuotesDataStore = null;

    /**
     * Constructor used for initializing the QuotesDataStore.
     */
    private QuotesDataStore() {
        super();
    }

    /**
     * Static method used for instantiating the QuotesDataStore using constructor.
     * @param quotesFileName
     * @return QuotesDataStore
     */
    public static QuotesDataStore getSharedQuotesDataStore(String quotesFileName, Context context) {
        if(sharedQuotesDataStore == null) {
            sharedQuotesDataStore = new QuotesDataStore();
            mContext = context;
            initializeQuotesFile(quotesFileName);
        }
        return (QuotesDataStore)sharedQuotesDataStore;
    }

    /**
     * Static method used for initializing the preferences file.
     * @param fileName
     */
    public static void initializeQuotesFile(String fileName) {
        quotesFileName = fileName;
        File sdCard = Environment.getExternalStorageDirectory();
        if(sdCard.exists() && sdCard.canRead()) {
            if(quotesFile == null || isQuotesFileAvailable()) {
                String filePath = sdCard.getAbsolutePath() + File.separator + quotesFileName;
                if (createQuotesFileAtPath(filePath)) {
                    Log.i(TAG, "Quotes File CREATED");
                } else {
                    Log.i(TAG, "Quotes File Not Available");
                }
            }
        }
    }

    /**
     * Static method used for getting the preferences file path.
     * @return String
     */
    public static String getSharedQuotesFilePath() {
        return quotesFile.getAbsolutePath();
    }

    /**
     *
     * @return boolean
     */
    public static boolean isQuotesFileReadable() {
        boolean readStatus = isQuotesFileAvailable()&&quotesFile.canRead();
        return (readStatus);
    }

    /**
     *
     * @return boolean
     */
    public static boolean isQuotesFileWritable() {
        boolean writeStatus = isQuotesFileAvailable()&&quotesFile.canWrite();
        return (writeStatus);
    }

    /**
     * Static method used for verifying if the the preferences file is readable or not.
     * @return boolean
     */
    private static boolean isQuotesFileAvailable() {
       return quotesFile.exists();
    }

    /**
     *
     * @return boolean
     */
    public static boolean createQuotesFileAtPath(String filePath){
        boolean creationStatus = false;
        quotesFile = new File(filePath);
        try {
            creationStatus = quotesFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return creationStatus;
    }


    /**
     * Static method used for reading the humours file is readable or not.
     * @return boolean
     */
    public static boolean writeQuotesToFile(List<Quotes> quotesList) {
        boolean writeStatus = false;
        clearQuotesFile();
        if(isQuotesFileWritable()) {
            FileOutputStream fos = null;
            try {
                fos = mContext.openFileOutput(quotesFileName, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                int i;
                for(i = 0; i<quotesList.size(); i++) {
                    Quotes tempQuote = quotesList.get(i);
                    os.writeObject(tempQuote);
                }
                os.close();
                fos.close();
                if(i == quotesList.size()) {
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
    private static void clearQuotesFile() {
        if(isQuotesFileWritable()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(quotesFile);
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
    public static List<Quotes> readQuotesFromFile() {
        List<Quotes> quotesList = null;
        if(isQuotesFileReadable()) {
            FileInputStream fis = null;
            try {
                fis = mContext.openFileInput(quotesFileName);
                ObjectInputStream istream = new ObjectInputStream(fis);
                quotesList = new ArrayList<Quotes>();
                while( fis.available() > 0) // check if the file stream is at the end
                {
                    Quotes tempQuote = (Quotes) istream.readObject();
                    quotesList.add(tempQuote); // read from the object stream,
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
        return quotesList;
    }
}
