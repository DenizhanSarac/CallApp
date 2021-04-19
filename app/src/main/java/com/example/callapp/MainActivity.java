package com.example.callapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottombar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottombar=(BottomNavigationView)findViewById(R.id.activity_main_bottombar);
        bottombar.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragmentContain,
                new ContactFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;

                    switch (item.getItemId()){
                        case R.id.bottom_navigation_contact:
                            selectedFragment = new ContactFragment();
                            break;
                        case R.id.bottom_navigation_message:
                            selectedFragment = new MessageFragment();
                            break;
                        case R.id.bottom_navigation_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragmentContain,
                            selectedFragment).commit();

                    return true;
                }
            };
}