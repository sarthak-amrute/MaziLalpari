package com.example.mazilalpari;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class UserHomeFragment extends Fragment {

    CardView cvBustopnearMe, cvSearchBus,cvTrackBus,cvFeedBack;
    @Override
    public void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_user_home, container, false);

        cvBustopnearMe=v.findViewById(R.id.cv_Home_BustopNearMe);
        cvSearchBus=v.findViewById(R.id.cv_Home_SearchMyBus);
        cvFeedBack=v.findViewById(R.id.cv_Home_FeedBack);
        cvTrackBus=v.findViewById(R.id.cv_Home_TrackMyBus);

        cvSearchBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),SearchMyBusActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}