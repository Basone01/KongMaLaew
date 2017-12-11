package com.advpro2.basone.kongmalaew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.advpro2.basone.kongmalaew.Model.Singleton;
import com.advpro2.basone.kongmalaew.util.CircleTransform;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.urbanairship.UAirship;

/**
 * Created by kanazang on 11/2/2017.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static String TAG = "KMMain";
    public static final int LOGIN_REQUEST_CODE= 224;
    TextView textView;
    TextView emailTextView;
    String firstName;
    String lastName;
    String email;
    ImageView imageView;
    private boolean checkExit=false;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        syncSubscribedList();
        setContentView(R.layout.activity_main);

        ShopFragment fragment = new ShopFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fireabaseAuthenticate();



    }

    @Override
    public void onBackPressed() {
        // Write your code here
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!checkExit){
                Toast.makeText(this,"Press Back Again To Exit",Toast.LENGTH_LONG).show();
                checkExit=true;
            }else {
//    super.onBackPressed();
                ActivityCompat.finishAffinity(MainActivity.this);
            }
            new CountDownTimer(3000,1000){

                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {
                    checkExit=false;
                }
            }.start();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_merchant) {
            MerchantFragment fragment = new MerchantFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_subscribe) {
            SubscribeFragment fragment = new SubscribeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_visitshop) {
            ShopFragment fragment = new ShopFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_signout) {

            LoginManager.getInstance().logOut();
            Toast.makeText(MainActivity.this, "Sign Out", Toast.LENGTH_LONG).show();
            Singleton.clear();
            FirebaseAuth.getInstance().signOut();
            updateUserData();
            fireabaseAuthenticate();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void fireabaseAuthenticate(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user==null){
            Intent loginIntent = new Intent(this,LoginActivity.class);
            startActivityForResult(loginIntent,LOGIN_REQUEST_CODE);
            finish();
        }else {
            setFacebookData();
        }

    }

    public void setFacebookData()
    {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Singleton.getInstance().setEmail(user.getEmail());
        Singleton.getInstance().setFirstName(user.getDisplayName().split(" ")[0]);
        Singleton.getInstance().setLastName(user.getDisplayName().split(" ")[1]);
        Singleton.getInstance().setProfilePic(user.getPhotoUrl().toString());
        updateUserData();
    }

    void updateUserData(){
        firstName = Singleton.getInstance().getFirstName();
        lastName = Singleton.getInstance().getLastName();
        email = Singleton.getInstance().getEmail();
        View view = navigationView.getHeaderView(0);
        textView = view.findViewById(R.id.nameja);
        emailTextView = view.findViewById(R.id.email);
        textView.setText(firstName + " " + lastName);
        emailTextView.setText(email);
        imageView = view.findViewById(R.id.facebookpic);
        Context context = imageView.getContext();
        if (Singleton.getInstance().getProfilePic()!=""){
            Picasso.with(context)
                    .load(Singleton.getInstance().getProfilePic())
                    .transform(new CircleTransform())
                    .centerCrop()
                    .resize(200,200)
                    .error(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .into(imageView);
        }else {
            Picasso.with(context)
                    .load(R.drawable.com_facebook_profile_picture_blank_portrait)
                    .transform(new CircleTransform())
                    .centerCrop()
                    .resize(200,200)
                    .into(imageView);
        }

    }

    void syncSubscribedList(){

        SharedPreferences pref = getSharedPreferences("brand_Subscribe_list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        for (String tag : UAirship.shared().getPushManager().getTags()){
            editor.putBoolean(tag,true);
            Log.d(TAG, "syncSubscribedList: "+tag);
        }
        editor.commit();

        Log.d(TAG, "syncSubscribedList: ");
    }
    
}


