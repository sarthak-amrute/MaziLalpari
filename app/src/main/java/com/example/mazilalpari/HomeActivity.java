package com.example.mazilalpari;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    UserHomeFragment userhomeFragment=new  UserHomeFragment();
    UserNearbyFragment usernearbyfragment=new UserNearbyFragment();
    UserHelpFragment userhelpfragment = new UserHelpFragment();
    UserMusicFragment usermusicfragment = new UserMusicFragment();
    UserMyProfileFragment usermyprofileFragment = new UserMyProfileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.menuBottomNavHome)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_Frame_Layout,userhomeFragment).commit();
        }
        else if (item.getItemId()==R.id.menuBottomNavNearby) {

            getSupportFragmentManager().beginTransaction().replace(R.id.home_Frame_Layout,usernearbyfragment).commit();
        }
        else if (item.getItemId()==R.id.menuBottomNavMusic) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_Frame_Layout,usermusicfragment).commit();
        }
        else if(item.getItemId()==R.id.menuBottomNavMyProfile){
            getSupportFragmentManager().beginTransaction().replace(R.id.home_Frame_Layout,usermyprofileFragment).commit();
        }

        return true;
    }
}