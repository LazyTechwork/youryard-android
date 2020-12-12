package com.example.yard.fragments_bottom_menu;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yard.adapters.MessagesAdapter;
import com.example.yard.application_services.MapsService;
import com.example.yard.data.Message;
import com.example.yard.data.Poll;
import com.example.yard.messages_fragments.MessagesList;
import com.example.yard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MessagesFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    String userID, current_user_name, user_name;
    String recipient, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        View v = inflater.inflate(R.layout.fragment_messages, container, false);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //WORK WITH MESSAGES VIEW
        userID = mAuth.getCurrentUser().getUid();

        ArrayList<Message> messages = new ArrayList<>();
        MessagesAdapter messagesAdapter = new MessagesAdapter(getActivity());
        RecyclerView recyclerView = v.findViewById(R.id.messagesView);
        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(messagesAdapter);

        ((Button) v.findViewById(R.id.refresh)).setOnClickListener((View.OnClickListener) v1 -> {
            DocumentReference documentReferenceUser = fStore.collection("users").document(userID);
            documentReferenceUser.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    current_user_name = value.getString("name") + " " + value.getString("surname");

                    fStore.collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String user_sender_data = document.getString("message_sender");
                                    String user_recipient_data = document.getString("message_recipient");
                                    if (user_recipient_data.equals(current_user_name) || user_sender_data.equals("admin")) {
                                        messages.add(new Message(document.getString("message_text"), document.getString("message_date"), document.getString("message_sender")));
                                        Log.d("Messages", document.getString("message_date") + Long.parseLong(document.getString("message_date")));
                                    }
                                }
                                messages.sort((m1, m2) -> m2.getTimestamp().compareTo(m1.getTimestamp()));
                                messagesAdapter.updateData(messages);
                            } else {
                                Toast.makeText(getActivity(), "Something wrong with getting document names", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        });
        ((Button) v.findViewById(R.id.refresh)).performClick();
        //WORK WITH WRITE MESSAGE BUTTON

        Button mNewMessage = v.findViewById(R.id.new_message);
        mNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userID = mAuth.getCurrentUser().getUid();
                String ind = Long.toString(new Date().getTime());

                DocumentReference documentReferenceUser = fStore.collection("users").document(userID);
                documentReferenceUser.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        user_name = value.getString("name") + " " + value.getString("surname");
                    }
                });

                DocumentReference documentReference = fStore.collection("messages").document("messages" + userID + ind);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = getLayoutInflater().inflate(R.layout.alert_message, null);
                builder.setView(view);

                EditText mEditTextMessage = view.findViewById(R.id.message_text);
                Spinner mSpinner = view.findViewById(R.id.recipient);

                ArrayList<String> user_names = new ArrayList<>();

                fStore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String user_receiver_data = document.getString("name") + " " + document.getString("surname");
                                user_names.add(user_receiver_data);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, user_names.toArray(new String[0]));
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinner.setAdapter(adapter);

                            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    recipient = (String) parent.getItemAtPosition(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            };
                            mSpinner.setOnItemSelectedListener(itemSelectedListener);

                        } else {
                            Toast.makeText(getActivity(), "Something wrong with getting document names", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                builder.setPositiveButton("Отправить", (dialog, which) -> {
                    String message = mEditTextMessage.getText().toString();
                    Map<String, Object> m = new HashMap<>();
                    m.put("message_text", message);
                    m.put("message_recipient", recipient);
                    m.put("message_sender", user_name);
                    date = Long.toString(new Date().getTime());
                    m.put("message_date", date);

                    documentReference.set(m).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Maybe someday I will write something here.....
                        }
                    });
                });

                builder.setNegativeButton("Отмена", (dialog, which) -> {
                    //nothing
                });

                builder.show();
            }
        });

        return v;
    }
}