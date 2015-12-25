package com.example.srravela.koolo.checklists.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srravela.koolo.KooloApplication;
import com.example.srravela.koolo.R;
import com.example.srravela.koolo.checklists.activities.KooloChecklistActivity;
import com.example.srravela.koolo.checklists.listeners.KooloChecklistListener;
import com.example.srravela.koolo.entities.Quotes;
import com.example.srravela.koolo.quotes.utils.QuotesDataStore;


public class KooloThreeSentenceFragment extends Fragment implements EditText.OnEditorActionListener {

    private View rootView;
    private Context mContext;
    private KooloChecklistActivity mActivity;
    public static KooloThreeSentenceFragment fragmentThreeSentence;
    private KooloChecklistListener mListener;
    View firstQuestionView, secondQuestionView, thirdQuestionView, questionsView;
    TextView firstQuestionText, secondQuestionText, thirdQuestionText;
    EditText firstAnswerEditText, secondAnswerEditText, thirdAnswerEditText;
    private ImageView backgroundImageView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment KooloThreeSentenceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloThreeSentenceFragment newInstance() {
        KooloThreeSentenceFragment fragment = new KooloThreeSentenceFragment();
        return fragment;
    }

    public KooloThreeSentenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_koolo_three_sentence, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (KooloChecklistActivity) getActivity();
        mListener = mActivity;
        mContext = mActivity.getApplicationContext();
        mActivity.getSupportActionBar().setTitle(getResources().getString(R.string.three_sentence));
        initUI();
    }

    /**
     * Method for configuring the UI
     */
    private void initUI() {
        setHasOptionsMenu(true);
        backgroundImageView = (ImageView)rootView.findViewById(R.id.checklist_background_image);

        questionsView = (View) rootView.findViewById(R.id.question_view);

        SharedPreferences threeSentenceReadPreferences = mContext.getSharedPreferences(KooloApplication.SELECTED_QUOTE_INDEX, mContext.MODE_PRIVATE);

        firstAnswerEditText = (EditText) questionsView.findViewById(R.id.answer_1_text);
        firstAnswerEditText.setOnEditorActionListener(this);

        if (threeSentenceReadPreferences.getString("answer1", null) != null) {
            firstAnswerEditText.setText(threeSentenceReadPreferences.getString("answer1", null));
        }

        secondAnswerEditText = (EditText) questionsView.findViewById(R.id.answer_2_text);
        secondAnswerEditText.setOnEditorActionListener(this);

        if (threeSentenceReadPreferences.getString("answer2", null) != null) {
            secondAnswerEditText.setText(threeSentenceReadPreferences.getString("answer2", null));
        }

        thirdAnswerEditText = (EditText) questionsView.findViewById(R.id.answer_3_text);
        thirdAnswerEditText.setOnEditorActionListener(this);
        if (threeSentenceReadPreferences.getString("answer3", null) != null) {
            thirdAnswerEditText.setText(threeSentenceReadPreferences.getString("answer3", null));
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences backgroundSharedPreferences=mContext.getSharedPreferences(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, mContext.MODE_PRIVATE);
        SharedPreferences backgroundImageFlagPreferences=mContext.getSharedPreferences(KooloApplication.BACKGROUND_IMAGE_SELECTED, mContext.MODE_PRIVATE);
        if(backgroundImageFlagPreferences.getBoolean(KooloApplication.BACKGROUND_IMAGE_SELECTED, false)) {
            Uri myUri = Uri.parse(backgroundSharedPreferences.getString(KooloApplication.SELECTED_BACKGROUND_IMAGE_URI, KooloApplication.getImageUri()));
            backgroundImageView.setImageURI(myUri);
        } else {
            backgroundImageView.setImageResource(R.drawable.background);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_koolo_three_sentence_options, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = new Bundle();
        switch (item.getItemId()) {
            case R.id.three_sentence_save_option:
                onSaveThreeSentencesAction();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void onSaveThreeSentencesAction() {

        String answer1 = firstAnswerEditText.getText().toString();
        String answer2 = secondAnswerEditText.getText().toString();
        String answer3 = thirdAnswerEditText.getText().toString();

        //Add to shared preferences.
        if (answer1 != null && answer2 != null && answer3 != null) {
            SharedPreferences threeSentenceEditPreferences = mContext.getSharedPreferences(KooloApplication.SELECTED_QUOTE_INDEX, mContext.MODE_PRIVATE);
            SharedPreferences.Editor threeSentenceEditor = threeSentenceEditPreferences.edit();
            threeSentenceEditor.putString("answer1", answer1);
            threeSentenceEditor.putString("answer2", answer2);
            threeSentenceEditor.putString("answer3", answer3);
            threeSentenceEditor.commit();
            Bundle bundle=new Bundle();
            bundle.putInt(KooloChecklistListener.KOOLO_CHECKLIST_ACTION, KooloChecklistListener.THREE_QUESTIONS_ANSWERED);
            mListener.onChecklistInteraction(bundle);
        } else {
            Toast.makeText(mContext, "Answer all questions", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onSaveThreeSentencesAction();
        }
        return false;
    }
}
