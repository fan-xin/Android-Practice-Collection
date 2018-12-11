package com.fanxin.android.statedemo;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreFragment extends Fragment {

    private static final String TAG = "ScoreFragment-app";

    private int score;



    public ScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("score",this.score);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate() called with:"+"savedInstanceState = ["+ savedInstanceState +"]");
//        setRetainInstance(true);

        if (savedInstanceState != null){
            this.score = savedInstanceState.getInt("score");

        }



    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_score, container, false);

        final TextView tvScore = (TextView)view.findViewById(R.id.id_tvScrore);
        tvScore.setText(String.valueOf(this.score));

        final Button btnAdd = (Button)view.findViewById(R.id.id_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvScore.setText(String.valueOf(++ScoreFragment.this.score));
            }
        });




        return view;
    }

}
