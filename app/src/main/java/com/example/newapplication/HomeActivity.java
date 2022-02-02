package com.example.newapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.newapplication.Fragments.HomeFragment;
import com.example.newapplication.Fragments.HomeNotesFragment;
import com.example.newapplication.Fragments.HomeTaskFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * To Display either Tasks or Notes Fragment
 * It's a Main Activity which has Fragment Container and Navigation Drawer
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageView editProfile;
    private ImageView profilePhoto;
    private ImageView navigationLogo;
    private TextView navigationName;
    private TextView navigationEmail;
    private ImageView notification;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.homeDrawerLayout);
        navigationView = (NavigationView) findViewById(R.id.homeNavView);
        View headerView = navigationView.getHeaderView(0);
        editProfile = (ImageView) headerView.findViewById(R.id.buttonEditProfile);
        profilePhoto = (ImageView) headerView.findViewById(R.id.headerImage);
        navigationLogo = (ImageView) headerView.findViewById(R.id.headerLogo);
        navigationName = (TextView) headerView.findViewById(R.id.headerName);
        navigationEmail = (TextView) headerView.findViewById(R.id.headerEmail);
        notification = (ImageView) findViewById(R.id.notification);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Bundle extra = getIntent().getExtras();

        navigationName.setText(mUser.getDisplayName());
        navigationEmail.setText(mUser.getEmail());
        profilePhoto.setImageURI(mUser.getPhotoUrl());

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "OnProcess", Toast.LENGTH_SHORT).show();
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.homeDrawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.homefFrameLayout,new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.menuHome);

        int authNum = extra.getInt("authType");
        navigationLogo.setImageResource(authTypeLogo(authNum));

    }

    private int authTypeLogo(int authNum) {
        switch (authNum){
            case 0:
                return R.drawable.logo;
            case 1:
                return R.drawable.google_logo;
            case 2:
                return R.drawable.facebook_logo;
            case 3:
                return R.drawable.github_logo;
            default:
                return R.drawable.logo;
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.notification){
            PopupMenu popupMenu = new PopupMenu(this,);
            popupMenu.inflate(R.menu.menu_temp);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    return false;
                }
            });
        }
        return true;
    }*/

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.homefFrameLayout,new HomeFragment()).addToBackStack(null).commit();
                break;

            case R.id.menuTask:
                getSupportFragmentManager().beginTransaction().replace(R.id.homefFrameLayout,new HomeTaskFragment()).addToBackStack(null).commit();
                break;

            case R.id.menuNotes:
                getSupportFragmentManager().beginTransaction().replace(R.id.homefFrameLayout,new HomeNotesFragment()).addToBackStack(null).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}