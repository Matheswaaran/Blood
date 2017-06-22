package com.example.mat.blood.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mat.blood.Fragments.CreateReqFragment;
import com.example.mat.blood.Fragments.DeleteReqFragment;
import com.example.mat.blood.Fragments.HomeFragment;
import com.example.mat.blood.Fragments.UserProfileFragment;
import com.example.mat.blood.Fragments.ViewReqFragment;
import com.example.mat.blood.Fragments.ViewRespFragment;
import com.example.mat.blood.R;
import com.example.mat.blood.utils.Utils;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String TAG = HomeActivity.class.getSimpleName();
    Utils utils;

    int containerId = R.id.fragment_holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");

        utils = new Utils(this);
        utils.loadUserDetails();

        //Set HomeFragment as Default
        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.add(containerId,homeFragment,HomeFragment.class.getSimpleName());
        ft.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateReqFragment createReqFragment = new CreateReqFragment();
                ft.replace(containerId,createReqFragment,CreateReqFragment.class.getSimpleName());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home){
            HomeFragment homeFragment = new HomeFragment();
            ft.replace(containerId,homeFragment,HomeFragment.class.getSimpleName());
        } else if (id == R.id.nav_create_req) {
            CreateReqFragment createReqFragment = new CreateReqFragment();
            ft.replace(containerId,createReqFragment,CreateReqFragment.class.getSimpleName());
        } else if (id == R.id.nav_view_req) {
            ViewReqFragment viewReqFragment = new ViewReqFragment();
            ft.replace(containerId,viewReqFragment, ViewReqFragment.class.getSimpleName());
        } else if (id == R.id.nav_view_resp) {
            ViewRespFragment viewRespFragment = new ViewRespFragment();
            ft.replace(containerId,viewRespFragment, ViewReqFragment.class.getSimpleName());
        } else if (id == R.id.nav_del_req) {
            DeleteReqFragment deleteReqFragment = new DeleteReqFragment();
            ft.replace(containerId,deleteReqFragment, DeleteReqFragment.class.getSimpleName());
        } else if (id == R.id.nav_profile) {
            UserProfileFragment userProfileFragment = new UserProfileFragment();
            ft.replace(containerId,userProfileFragment, UserProfileFragment.class.getSimpleName());
        } else if (id == R.id.nav_logout) {
            utils.signOut();
        }
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
