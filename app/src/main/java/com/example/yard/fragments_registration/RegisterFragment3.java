package com.example.yard.fragments_registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.yard.LoadingActivity;
import com.example.yard.R;
import com.example.yard.Register;

public class RegisterFragment3 extends Fragment {

    EditText mEmail, mPassword;
    String email, password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register3, container, false);

        mEmail = v.findViewById(R.id.editTextPersonEmail);
        mPassword = v.findViewById(R.id.editTextPersonPassword);

        Register.mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                Register.setDefaults("user-password", password, getActivity());
                Register.setDefaults("user-email", email, getActivity());
                startActivity(new Intent(getActivity(), LoadingActivity.class));
            }
        });

        Register.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.mProgressBar.setProgress(Register.mProgressBar.getProgress()-1);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new RegisterFragment2());
                fragmentTransaction.commit();
            }
        });

        return v;
    }

}