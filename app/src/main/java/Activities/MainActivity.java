package Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import Fragment.ContactFragment;
import Fragment.MessageFragment;
import Fragment.ProfileFragment;
import com.example.callapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottombar;
    private String gelenMail;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=getIntent();
        gelenMail=intent.getStringExtra("kullaniciMail");
        System.out.println("Burası Merkez "+gelenMail);

        bundle = new Bundle();
        bundle.putString("params", gelenMail);

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
                            selectedFragment.setArguments(bundle);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_fragmentContain,
                            selectedFragment,gelenMail).commit();

                    return true;
                }
            };
}