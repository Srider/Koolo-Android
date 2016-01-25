package com.example.srravela.koolo.calendar.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.srravela.koolo.entities.CalendarDates;
import com.example.srravela.koolo.entities.CalendarEvents;

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
 * Created by srikar on 16/01/16.
 */
public class EventsDataStore {

    private static final String TAG = EventsDataStore.class.getSimpleName();
    private static File eventsFile;
    private static String eventsFileName;
    private static Context mContext;
    public static EventsDataStore sharedEventsDataStore = null;

    /**
     * Constructor used for initializing the QuotesDataStore.
     */
    private EventsDataStore() {
        super();
    }

    /**
     * Static method used for instantiating the QuotesDataStore using constructor.
     * @param eventsFileName
     * @return EventsDataStore
     */
    public static EventsDataStore getSharedEventsDataStore(String eventsFileName, Context context) {
        if(sharedEventsDataStore == null) {
            sharedEventsDataStore = new EventsDataStore();
            mContext = context;
            initializeEventsFile(eventsFileName);
        }
        return sharedEventsDataStore;
    }

    /**
     * Static method used for initializing the preferences file.
     * @param fileName
     */
    public static void initializeEventsFile(String fileName) {
        eventsFileName = fileName;
        File sdCard = Environment.getExternalStorageDirectory();
        if(sdCard.exists() && sdCard.canRead()) {
            if(eventsFile == null || isEventsFileAvailable()) {
                String filePath = sdCard.getAbsolutePath() + File.separator + eventsFileName;
//                String filePath = mContext.getFilesDir() + File.separator + goalsFileName;
                if (createEventsFileAtPath(filePath)) {
                    Log.i(TAG, "EVENTS File CREATED");
                } else {
                    Log.i(TAG, "EVENTS File Not Available");
                }
            }
        }
    }

    /**
     * Static method used for getting the preferences file path.
     * @return String
     */
    public static String getSharedEventsFilePath() {
        return eventsFile.getAbsolutePath();
    }

    /**
     *
     * @return boolean
     */
    public static boolean isEventsFileReadable() {
        boolean readStatus = isEventsFileAvailable() &&eventsFile.canRead();
        return (readStatus);
    }

    /**
     *
     * @return boolean
     */
    public static boolean isEventsFileWritable() {
        boolean writeStatus = isEventsFileAvailable() &&eventsFile.canWrite();
        return (writeStatus);
    }

    /**
     * Static method used for verifying if the the preferences file is readable or not.
     * @return boolean
     */
    private static boolean isEventsFileAvailable() {
        return eventsFile.exists();
    }

    /**
     *
     * @return boolean
     */
    public static boolean createEventsFileAtPath(String filePath){
        boolean creationStatus = false;
        eventsFile = new File(filePath);
        try {
            creationStatus = eventsFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return creationStatus;
    }


    /**
     * Static method used for reading the humours file is readable or not.
     * @return boolean
     */
    public static boolean writeEventsToFile(List<CalendarEvents> eventsList) {
        boolean writeStatus = false;
        clearEventsFile();
        if(isEventsFileWritable()) {
            FileOutputStream fos = null;
            try {
                fos = mContext.openFileOutput(eventsFileName, Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                int i;
                for(i = 0; i<eventsList.size(); i++) {
                    CalendarEvents tempEvent = eventsList.get(i);
                    os.writeObject(tempEvent);
                }
                os.close();
                fos.close();
                if(i == eventsList.size()) {
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
    private static void clearEventsFile() {
        if(isEventsFileWritable()) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(eventsFile);
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
    public static List<CalendarEvents> readEventsFromFile() {
        List<CalendarEvents> eventsList = new ArrayList<CalendarEvents>();
        if(isEventsFileReadable()) {
            FileInputStream fis = null;
            try {
                fis = mContext.openFileInput(eventsFileName);
                ObjectInputStream istream = new ObjectInputStream(fis);

                while( fis.available() > 0) // check if the file stream is at the end
                {
                    CalendarEvents tempEvent = (CalendarEvents) istream.readObject();
                    eventsList.add(tempEvent); // read from the object stream,
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
        return eventsList;
    }

    public static List<CalendarEvents>  readEventsForDate(CalendarDates date) {
        List<CalendarEvents> eventsList = readEventsFromFile();
        List<CalendarEvents> selectedDateEventsList = new ArrayList<CalendarEvents>();
        DateAndTimeUtility sharedDateAndTimeUtility = DateAndTimeUtility.getSharedDateAndTimeUtility(mContext);

        if(eventsList != null) {
            for(CalendarEvents calendarEvent : eventsList) {
                String[] dateComponents = sharedDateAndTimeUtility.getRefactoredDateFromString(calendarEvent.getEventDate());
                if(dateComponents[0].equals(date.getDateText()) && dateComponents[1].equals(date.getDayText()) && dateComponents[2].equals(date.getMonthText())) {
                    selectedDateEventsList.add(calendarEvent);
                }
            }
        }
        return selectedDateEventsList;
    }
}
