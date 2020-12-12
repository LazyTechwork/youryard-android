package com.example.yard.fragments_bottom_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.yard.Login;
import com.example.yard.R;
import com.example.yard.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfileFragment extends Fragment {

    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private TextView mName, mSurname, mEmail, mCity, mStreet;
    private String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        mName = v.findViewById(R.id.name);
        mSurname = v.findViewById(R.id.surname);
        mCity = v.findViewById(R.id.city);
        mEmail = v.findViewById(R.id.email);
        mStreet = v.findViewById(R.id.street);

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                mName.setText(value.getString("name"));
                mSurname.setText(value.getString("surname"));
                mCity.setText(value.getString("city"));
                mEmail.setText(value.getString("email"));
                mStreet.setText(value.getString("street"));

            }
        });

        Button mExit = v.findViewById(R.id.exit);

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.setDefaults("visited", null, getActivity());
                startActivity(new Intent(getActivity(), Login.class));
            }
        });

        return v;
    }
}