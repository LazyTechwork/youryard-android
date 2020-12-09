package com.example.yard.fragments_bottom_menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yard.application_services.CovidService;
import com.example.yard.application_services.DobroService;
import com.example.yard.application_services.MapsService;
import com.example.yard.application_services.MoneyService;
import com.example.yard.application_services.PoolsService;
import com.example.yard.R;

public class ServicesFragment extends Fragment {

    ImageView mImageCovid, mImageMoney, mImagePools, mImageMaps, mImageDobro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_services, container, false);

        mImageCovid = v.findViewById(R.id.covid_image);
        mImageMoney = v.findViewById(R.id.money_image);
        mImageDobro = v.findViewById(R.id.dobro_image);
        mImageMaps = v.findViewById(R.id.maps_image);
        mImagePools = v.findViewById(R.id.pools_image);

        mImageCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CovidService.class));
            }
        });

        mImageMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MoneyService.class));
            }
        });

        mImageDobro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DobroService.class));
            }
        });

        mImageMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MapsService.class));
            }
        });

        mImagePools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PoolsService.class));
            }
        });


        return v;
    }
}