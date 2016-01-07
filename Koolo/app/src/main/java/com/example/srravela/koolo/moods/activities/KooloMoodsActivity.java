package com.example.srravela.koolo.moods.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.entities.MoodShot;
import com.example.srravela.koolo.entities.Utils;
import com.example.srravela.koolo.moods.database.DatabaseHandler;
import com.example.srravela.koolo.moods.fragments.KooloMoodLineFragment;
import com.example.srravela.koolo.moods.fragments.KooloMoodMapFragment;
import com.example.srravela.koolo.humor.listeners.KooloHumourColorListener;
import com.example.srravela.koolo.moods.fragments.KooloMoodShotFormatterFragment;
import com.example.srravela.koolo.moods.listeners.KooloMoodsListener;
import com.example.srravela.koolo.moods.utils.MoodsDataStore;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KooloMoodsActivity extends KooloBaseActivity implements KooloMoodsListener {

   // private static final String TAG = KooloMoodsActivity.class.getSimpleName();

    private static final int CAMERA_REQUEST = 1888;
    private static final int GALLERY_REQUEST = 1988;

   // private FrameLayout frameLayoutFragmentContaner;

    MoodsDataStore sharedMoodsDataStore;

    private ImageView photoImageView;
    private  DatabaseHandler databaseHandler;

    private MenuItem menuItem;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_moods);
        mContext=getApplicationContext();
        databaseHandler = new DatabaseHandler(mContext);
       // actionBar = getSupportActionBar();
       // actionBar.hide();
       // getSupportFragmentManager().addOnBackStackChangedListener(backStackChangedListener);

        initUI();
    }

    /**
     * Method for configuring the UI
     */
    private void initUI(){
       // frameLayoutFragmentContaner=(FrameLayout)findViewById(R.id.fragment_moods_container);
       // photoImageView = (ImageView) findViewById(R.id.photo_image);
        loadMoodListFragment();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_koolo_moodline, menu);
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
       /* mActivity.getActionBar().hide();*/
        actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.mood_line_title));

    }
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load:
                menuItem = item;
                menuItem.setActionView(R.layout.humor_color_screen);
                menuItem.expandActionView();
               *//* TestTask task = new TestTask();
                task.execute("test");*//*
                break;
            default:
                break;
        }
        return true;
    }*/
    private void showPhoto(Uri photoUri) {
        File imageFile = new File(photoUri.getPath());
        if (imageFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
          //  photoImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
          //  photoImageView.setImageDrawable(drawable);
            //bitMap = (Bitmap) extras.get("data");
          //  photoImageView.setImageBitmap(bitmap);
        }
    }

    private File getOutputPhotoFile() {
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName());
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e(TAG, "Failed to create storage directory.");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.UK).format(new Date());
        return new File(directory.getPath() + File.separator + "IMG_"
                + timeStamp + ".jpg");
    }
    private static final String TAG = "CallCamera";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;

    Uri fileUri = null;
    ImageView photoImage = null;
    /**
     * Method for loading Loacations and Management Fragment.
     */
    Fragment moodLinefragment;
    Fragment mapfragment;
    Fragment moodShotFormatterFragment;
    private void loadMoodListFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        moodLinefragment= KooloMoodLineFragment.newInstance();
        transaction.replace(R.id.fragment_moods_container, moodLinefragment, "moodLinefragment");
        transaction.addToBackStack("moodLinefragment");
        transaction.commitAllowingStateLoss();
    }
    /**
     * Method for loading Loacations and Management Fragment.
     */

    public void loadMoodMapFragment(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        mapfragment= KooloMoodMapFragment.newInstance();
        transaction.replace(R.id.fragment_moods_container, mapfragment, "moodmapfragment");
        transaction.addToBackStack("moodmapfragment");
        transaction.commitAllowingStateLoss();
    }

    private void loadMoodsCameraActivity() {
      /*  KooloApplication.isExternalIntentLoaded = true;
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);*/

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getOutputPhotoFile();
        fileUri = Uri.fromFile(getOutputPhotoFile());
        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ);
    }

    private void loadMoodsGalleryActivity() {
        KooloApplication.isExternalIntentLoaded = true;
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT <19){
            intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
        }
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST);
    }

    private void loadMoodshotFormatterFragment(Uri selectedImage) {
        Bundle bundle = new Bundle();
        bundle.putString("selectedImage", selectedImage.toString());
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
         moodShotFormatterFragment= KooloMoodShotFormatterFragment.newInstance();
        moodShotFormatterFragment.setArguments(bundle);

        transaction.replace(R.id.fragment_moods_container, moodShotFormatterFragment, "moodmapfragment");
        transaction.addToBackStack("MoodShotFormatterFragment");
        transaction.commitAllowingStateLoss();
    }
    public interface OnBackPressedListener {
        void onBackPressed();
    }


   @Override
   public void onBackPressed() {
       popAction();
   }

    public void popAction() {
        if(getFragmentManager().getBackStackEntryCount() ==0) {
            getFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public void onMoodsAction(Bundle bundle) {
        int action = (int)bundle.get(KooloMoodsListener.KOOLO_MOODS_ACTION);

        switch(action) {
            case MOOD_SHOT_SELECTED:
                loadMoodshotFormatterFragment(null);
                break;
            case MOODS_LAUNCH_CAMERA_ACTION:
                loadMoodsCameraActivity();
                break;
            case MOODS_LAUNCH_GALLERY_ACTION:
                loadMoodsGalleryActivity();
                break;
            case MOODS_SHOT_FORMATTER:
                loadMoodshotFormatterFragment(null);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* KooloApplication.isExternalIntentLoaded = false;

        if (data != null) {
            Uri capturedImage = data.getData();
            MoodShot newMoodShot = new MoodShot(capturedImage.toString(), Utils.ColorType.BLACK);

            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
                addNewMoodShotToDataStore( newMoodShot);

            } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
                addNewMoodShotToDataStore( newMoodShot);
            }
        }*/
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
            if (resultCode == RESULT_OK) {
                Uri photoUri = null;
                if (data == null) {
                    // A known bug here! The image should have saved in fileUri
                    Toast.makeText(this, "Image saved successfully",
                            Toast.LENGTH_LONG).show();
                    photoUri = fileUri;
                } else {
                    photoUri = data.getData();
                    Toast.makeText(this, "Image saved successfully in: " + data.getData(),
                            Toast.LENGTH_LONG).show();
                }
               //  showPhoto(photoUri);
               /* MoodShot moodShot = new MoodShot();
                moodShot.setMoodCaptureUri(photoUri.toString());
                moodShot.setMoodColor("BLACK");
                moodShot.setMoodCaptureDate(getDate());
                databaseHandler.addMoodShot(moodShot);
                loadMoodListFragment();*/

                //Uri selectedImage = data.getData();
                loadMoodshotFormatterFragment(Uri.parse(photoUri.toString()));

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Callout for image capture failed!",
                        Toast.LENGTH_LONG).show();
            }
        } else if(requestCode == GALLERY_REQUEST) {
            KooloApplication.isExternalIntentLoaded = false;

          //  super.onActivityResult(requestCode, resultCode, data);
            if (data != null) {
                Uri selectedImage = data.getData();
             /*   actionBar.show();
                actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_CUSTOM);*/
                loadMoodshotFormatterFragment(selectedImage);
               /* KooloApplication.setImageUri(selectedImage.toString());
                SharedPreferences backgroundImagePreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
                SharedPreferences.Editor backgroundImageEditor=backgroundImagePreferences.edit();
                backgroundImageEditor.putString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, selectedImage.toString());
                backgroundImageEditor.commit();

                SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);
                SharedPreferences.Editor backgroundImageFlagEditor=backgroundImageFlagPreferences.edit();
                backgroundImageFlagEditor.putBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, true);
                backgroundImageFlagEditor.commit();*/
              //  finish();
            }/* else if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQ){
                Uri selectedImage = data.getData();
                loadMoodshotFormatterFragment(selectedImage);
            }*/
        }

    }
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }
    private void addNewMoodShotToDataStore(MoodShot newMoodShot) {
        List<MoodShot> moodshotList = Utils.getSharedUtils(mContext).loadMoods();

        if(moodshotList != null && moodshotList.size() > 0) {
            if(Utils.getSharedUtils(mContext).isMoodShotAlreadyExists(newMoodShot)) {
                //Add Toast for item already exists.
                Toast.makeText(getApplicationContext(), "MoodShot already exists", Toast.LENGTH_SHORT).show();
            } else {
                moodshotList.add(newMoodShot);
                Utils.getSharedUtils(mContext).addMoodShotListToDataStore(moodshotList);
            }
        }
         else {
            moodshotList = new ArrayList<MoodShot>();
            moodshotList.add(newMoodShot);
            Utils.getSharedUtils(mContext).addMoodShotListToDataStore(moodshotList);
        }
    }



}
