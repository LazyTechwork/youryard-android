package com.example.yard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoadingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    String userID;
    String email, password, name, surname, city, street, region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        email = Register.getDefaults("user-email", LoadingActivity.this);
        password = Register.getDefaults("user-password", LoadingActivity.this);
        name = Register.getDefaults("user-name", LoadingActivity.this);
        surname = Register.getDefaults("user-surname", LoadingActivity.this);
        city = Register.getDefaults("user-city", LoadingActivity.this);
        street = Register.getDefaults("user-street", LoadingActivity.this);
        region = Register.getDefaults("region_name", LoadingActivity.this);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Register.setDefaults("visited", "visited", LoadingActivity.this);

                            userID = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", email);
                            user.put("name", name);
                            user.put("surname", surname);
                            user.put("region", region);
                            user.put("city", city);
                            user.put("index", Register.getDefaults("region_index", LoadingActivity.this));
                            user.put("street", street);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Maybe someday I will write something here.....
                                }
                            });

                            int milliseconds_delayed = 1000;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                                    finish();
                                }
                            }, milliseconds_delayed);
                        } else {
                            Toast.makeText(LoadingActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoadingActivity.this, Login.class));
                        }
                    }
                });

    }
}