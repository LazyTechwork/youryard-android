package com.example.yard.fragments_bottom_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.yard.R;
import com.example.yard.application_services.CovidService;
import com.example.yard.application_services.MyPollsService;
import com.example.yard.application_services.MoneyService;
import com.example.yard.application_services.PollsService;

public class ServicesFragment extends Fragment {

    ImageView mImageCovid, mImageMoney, mImagePools, mImageMaps, mImageDobro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        View v = inflater.inflate(R.layout.fragment_services, container, false);

        mImageCovid = v.findViewById(R.id.covid_image);
        mImageMoney = v.findViewById(R.id.money_image);
        mImageMaps = v.findViewById(R.id.maps_image);
        mImagePools = v.findViewById(R.id.pools_image);

        //TO COVID SERVICE CLASS
        mImageCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CovidService.class));
            }
        });

        //TO MONEY CLASS
        mImageMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MoneyService.class));
            }
        });

        //TO MY POLLS CLASS
        mImageMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyPollsService.class));
            }
        });

        //TO POLLS CLASS
        mImagePools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PollsService.class));
            }
        });


        return v;
    }
}