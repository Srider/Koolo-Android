package com.example.srravela.koolo.moods.database;

/**
 * Created by tviswana on 12/23/2015.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.srravela.koolo.entities.MoodShot;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "kooloManager";
    private static final String TABLE_MOODSHOTS = "moodshots";
    private static final String KEY_ID = "id";
    private static final String MOOD_COLOR = "mood_color";
    private static final String MOOD_CAPTURE_DATE= "mood_capture_date";
    private static final String MOOD_CAPTURE_URI = "mood_capture_uri";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
     /*   String CREATE_MOODSHOTS_TABLE = "CREATE TABLE " + TABLE_MOODSHOTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + MOOD_COLOR + " TEXT,"
                + MOOD_CAPTURE_DATE + "CURRENT_DATE,"+ MOOD_CAPTURE_URI + " TEXT"+ ")";
        db.execSQL(CREATE_MOODSHOTS_TABLE);*/



        db.execSQL("create table " + TABLE_MOODSHOTS +
                        "("
                        + KEY_ID + " integer primary key autoincrement,"
                        + MOOD_COLOR + " text not null,"
                        + MOOD_CAPTURE_DATE + " CURRENT_TIMESTAMP,"
                        + MOOD_CAPTURE_URI + " text not null);"
        );
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOODSHOTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new moodShot
   public void addMoodShot(MoodShot moodShot) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MOOD_COLOR, moodShot.getMoodColor());
        values.put(MOOD_CAPTURE_DATE, moodShot.getMoodCaptureDate());
        values.put(MOOD_CAPTURE_URI, moodShot.getMoodCaptureUri());

        // Inserting Row
        db.insert(TABLE_MOODSHOTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    /*// code to get the single moodShot
    public MoodShot getMoodShots(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MOODSHOTS, new String[] {MOOD_COLOR,MOOD_CAPTURE_DATE,MOOD_CAPTURE_URI}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();


       // Uri modCaptureUri = Uri.parse(cursor.getString(0));
        MoodShot moodShot = new MoodShot( cursor.getString(0),
                cursor.getString(1), cursor.getString(2));
        // return moodShot
        return moodShot;
    }*/

    // code to get the single moodShot
    public List<MoodShot> getMoodShots(String moodColor) {
        List<MoodShot> moodShotList = new ArrayList<MoodShot>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MOODSHOTS, new String[] {MOOD_COLOR,MOOD_CAPTURE_DATE,MOOD_CAPTURE_URI}, MOOD_COLOR + "=?",
                new String[] { moodColor }, null, null, MOOD_CAPTURE_DATE+" DESC", null);
        if (cursor != null)
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    MoodShot moodShot = new MoodShot();
                    moodShot.setMoodColor(cursor.getString(0));
                    moodShot.setMoodCaptureDate(cursor.getString(1));
                    moodShot.setMoodCaptureUri(cursor.getString(2));
                    // Adding moodShot to list
                    moodShotList.add(moodShot);
                } while (cursor.moveToNext());
            }

        // return moodShot
        return moodShotList;
    }

    // code to get all MoodShot in a list view
    public List<MoodShot> getAllMoodShots() {
        List<MoodShot> moodShotList = new ArrayList<MoodShot>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MOODSHOTS +" ORDER BY "+ MOOD_CAPTURE_DATE+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                MoodShot moodShot = new MoodShot();
                moodShot.setMoodShotId(cursor.getInt(0));
                moodShot.setMoodColor(cursor.getString(1));
                moodShot.setMoodCaptureDate(cursor.getString(2));
                moodShot.setMoodCaptureUri(cursor.getString(3));
                // Adding moodShot to list
                moodShotList.add(moodShot);
            } while (cursor.moveToNext());
        }

        // return moodShot list
        return moodShotList;
    }

    // code to update the single moodShot
    public int updateMoodShot(MoodShot moodShot) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MOOD_COLOR, moodShot.getMoodColor());
        values.put(MOOD_CAPTURE_DATE, moodShot.getMoodCaptureDate());
        values.put(MOOD_CAPTURE_URI, moodShot.getMoodCaptureUri());

        // updating row
        return db.update(TABLE_MOODSHOTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(moodShot.getMoodShotId()) });
    }

    // Deleting single MoodShot
    public void deleteMoodShot(MoodShot moodShot) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOODSHOTS, KEY_ID + " = ?",
                new String[] { String.valueOf(moodShot.getMoodShotId()) });
        db.close();
    }

    // Getting MoodShot Count
    public int getMoodShotCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MOODSHOTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}