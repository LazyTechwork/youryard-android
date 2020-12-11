package com.example.yard.fragments_registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yard.LoadingActivity;
import com.example.yard.Login;
import com.example.yard.R;
import com.example.yard.Register;


public class RegisterFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register1, container, false);

        EditText mName = v.findViewById(R.id.editTextPersonName);
        EditText mSurname = v.findViewById(R.id.editTextPersonSurname);

        Register.mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SET DEFAULTS
                if (mName.getText().toString().trim().length() < 3){
                    Toast.makeText(getActivity(),"Имя должно быть длинее", Toast.LENGTH_SHORT).show();
                } else if (mName.getText().toString().trim().length() < 3){
                    Toast.makeText(getActivity(),"Фамилия должна быть длинее", Toast.LENGTH_SHORT).show();
                } else{
                    Register.setDefaults("user-name", mName.getText().toString().trim(), getActivity());
                    Register.setDefaults("user-surname", mSurname.getText().toString().trim(), getActivity());
                    //CHANGE FRAGMENT
                    Register.mProgressBar.setProgress(Register.mProgressBar.getProgress()+1);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, new RegisterFragment2());
                    fragmentTransaction.commit();
                }
            }
        });

        Register.mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        return v;
    }
}