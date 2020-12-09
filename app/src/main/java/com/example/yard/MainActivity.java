package com.example.yard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.yard.fragments_bottom_menu.MessagesFragment;
import com.example.yard.fragments_bottom_menu.NotificationsFragment;
import com.example.yard.fragments_bottom_menu.ProfileFragment;
import com.example.yard.fragments_bottom_menu.ServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    BottomNavigationView mNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DEAL WITH FRAGMENTS AND BOTTOM NAVIGATION MENU
        mNav = findViewById(R.id.menu);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, new ServicesFragment());
        fragmentTransaction.commit();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.messages:
                        fragment = new MessagesFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.servises:
                        fragment = new ServicesFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.notifications:
                        fragment = new NotificationsFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });

    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}