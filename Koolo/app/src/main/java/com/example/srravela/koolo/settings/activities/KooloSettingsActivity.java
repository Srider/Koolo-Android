package com.example.srravela.koolo.settings.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.KooloBaseActivity;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.contributors.activities.KooloContributorsActivity;
import com.example.srravela.koolo.humor.activities.KooloHumourActivity;
import com.example.srravela.koolo.license.activities.KooloLicenseActivity;
import com.example.srravela.koolo.passcode.activities.KooloPasscodeActivity;
import com.example.srravela.koolo.quotes.activities.KooloQuotesActivity;
import com.example.srravela.koolo.review.activities.KooloReviewActivity;
import com.example.srravela.koolo.settings.fragments.KooloSettingsFragment;
import com.example.srravela.koolo.settings.listeners.KooloSettingsInteractionListener;
import com.example.srravela.koolo.updates.activities.KooloUpdatesActivity;

public class KooloSettingsActivity extends KooloBaseActivity implements KooloSettingsInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koolo_settings);
        mContext=getApplicationContext();
        getSupportActionBar().setTitle(getString(R.string.action_settings));
        initUI();
    }

    void initUI() {
        loadKooloSettingsFragment();
    }

    /**
     *  Method for loading Settings Fragment.
     */
    private void loadKooloSettingsFragment(){
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        //TODO:
        Fragment fragment= KooloSettingsFragment.newInstance();
        transaction.replace(R.id.fragment_settings_container, fragment, "koolosettingsfragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSettingsInteraction(Bundle urlBundle) {
        int action = urlBundle.getInt(KooloSettingsInteractionListener.KOOLO_SETTINGS_ACTION);
        switch(action) {
            case KOOLO_BACKGROUND_IMAGE_ACTION:
                loadBackgroundImageActivity();
                break;
            case KOOLO_QUOTES_ACTION:
                loadQuotesActivity();
                break;
            case KOOLO_PASSCODE_ACTION:
                loadPasscodeActivity();
                break;
            case KOOLO_HUMOUR_COLORS_ACTION:
                loadHumoursActivity();
                break;
            case KOOLO_TUTORIAL_ACTION:
                //TODO: add tutorial.
                break;
            case KOOLO_REVIEW_ACTION:
                //TODO: add Review
                break;
            case KOOLO_UPDATES_ACTION:
                //TODO: add Update
                break;
            case KOOLO_CONTRIBUTORS_ACTION:
                //TODO: add Contributors
                break;
            case KOOLO_LICENSE_ACTION:
                //TODO: add license
                break;
        }
    }

    //Passcode Activity.
    void loadPasscodeActivity() {
        Intent passcodeIntent = new Intent(KooloSettingsActivity.this, KooloPasscodeActivity.class);
        startActivity(passcodeIntent);
    }

    //Quotes Activity.
    void loadQuotesActivity() {
        Intent quotesIntent = new Intent(KooloSettingsActivity.this, KooloQuotesActivity.class);
        startActivity(quotesIntent);
    }

    //Background Image
    void loadBackgroundImageActivity() {
        KooloApplication.isExternalIntentLoaded = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 322);
    }

    //Review Activity
    void loadReviewActivity() {
        Intent reviewIntent = new Intent(KooloSettingsActivity.this, KooloReviewActivity.class);
        startActivity(reviewIntent);
    }

    //Contributors Activity
    void loadContributorsActivity() {
        Intent contributorsIntent = new Intent(KooloSettingsActivity.this, KooloContributorsActivity.class);
        startActivity(contributorsIntent);
    }

    //Updates Activity
    void loadUpdatesActivity() {
        Intent updatesIntent = new Intent(KooloSettingsActivity.this, KooloUpdatesActivity.class);
        startActivity(updatesIntent);
    }

    //License Activity
    void loadLicenseActivity() {
        Intent licenseIntent = new Intent(KooloSettingsActivity.this, KooloLicenseActivity.class);
        startActivity(licenseIntent);
    }

    //Humours Activity
    void loadHumoursActivity() {
        Intent licenseIntent = new Intent(KooloSettingsActivity.this, KooloHumourActivity.class);
        startActivity(licenseIntent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        KooloApplication.isExternalIntentLoaded = false;

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri selectedImage = data.getData();
            KooloApplication.setImageUri(selectedImage.toString());
            SharedPreferences backgroundImagePreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
            SharedPreferences.Editor backgroundImageEditor=backgroundImagePreferences.edit();
            backgroundImageEditor.putString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, selectedImage.toString());
            backgroundImageEditor.commit();

            SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);
            SharedPreferences.Editor backgroundImageFlagEditor=backgroundImageFlagPreferences.edit();
            backgroundImageFlagEditor.putBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, true);
            backgroundImageFlagEditor.commit();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
