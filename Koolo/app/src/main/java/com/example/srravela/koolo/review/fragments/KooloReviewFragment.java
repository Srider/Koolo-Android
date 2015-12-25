package com.example.srravela.koolo.review.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.srravela.koolo.R;
import com.example.srravela.koolo.review.listeners.KooloReviewInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link KooloReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KooloReviewFragment extends Fragment {

    private KooloReviewInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment KooloReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KooloReviewFragment newInstance(String param1, String param2) {
        KooloReviewFragment fragment = new KooloReviewFragment();
        return fragment;
    }

    public KooloReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_koolo_review, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
