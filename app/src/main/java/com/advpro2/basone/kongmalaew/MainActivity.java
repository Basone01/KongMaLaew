package com.advpro2.basone.kongmalaew;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    final static String TAG = "Main";

    FirebaseUser user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkLoginState();

        setContentView(R.layout.activity_main);
        Button gotoLogin = findViewById(R.id.goto_login);

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });





    }

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
