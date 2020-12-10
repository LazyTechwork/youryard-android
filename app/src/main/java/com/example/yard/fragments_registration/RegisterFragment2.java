package com.example.yard.fragments_registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.yard.Login;
import com.example.yard.R;
import com.example.yard.Register;


public class RegisterFragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register2, container, false);

        EditText mCity = v.findViewById(R.id.editTextPersonCity);
        EditText mStreet = v.findViewById(R.id.editTextPersonStreet);

        Register.mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SET DEFAULTS
                Register.setDefaults("user-name", mCity.getText().toString().trim(), getActivity());
                Register.setDefaults("user-surname", mStreet.getText().toString().trim(), getActivity());
                Register.setDefaults("user-city", "", getActivity());
                Register.setDefaults("user-street", "", getActivity());
                //CHANGE FRAGMENT
                Register.mProgressBar.setProgress(Register.mProgressBar.getProgress()+1);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new RegisterFragment3());
                fragmentTransaction.commit();
            }
        });

        Register.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.mProgressBar.setProgress(Register.mProgressBar.getProgress()-1);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new RegisterFragment1());
                fragmentTransaction.commit();
            }
        });

        return v;
    }
}