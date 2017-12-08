package com.advpro2.basone.kongmalaew;


import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    final static String TAG = "Main";
    DrawerLayout drawer;
    NavigationView nav;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseUser user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.nav_toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        nav = findViewById(R.id.nav);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer,R.string.nav_toggle_open,R.string.nav_toggle_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(nav)){
                    drawer.closeDrawer(nav);
                }else {
                    drawer.openDrawer(nav);
                }
            }
        });



//        checkLoginState();
//        setupLogoutButton();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item)||super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(nav)){
            drawer.closeDrawer(nav);
        }else super.onBackPressed();
    }

    //    void setupLogoutButton(){
//        Button gotoLogin = findViewById(R.id.goto_login);
//        gotoLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                LoginManager.getInstance().logOut();
//                Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(loginIntent);
//            }
//        });
//    }
    void checkLoginState(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            Log.d(TAG, "onCreate: "+user.getDisplayName());
        }else{
            Intent login = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(login);
        }
    }


}
