package com.advpro2.basone.kongmalaew;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.urbanairship.UAirship;
import com.urbanairship.push.notifications.NotificationFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button b;
    EditText et,et2;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d("LOGIN", "onAuthStateChanged: ");
                }else{
                    Log.d("LOGIN", "onAuthStateChanged: OK");

                }
            }
        };

        NotificationFactory factory = UAirship.shared().getPushManager().getNotificationFactory();

// Customize the factory
        factory.setTitleId(R.string.app_title);
        factory.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        factory.setLargeIcon(R.drawable.anonymous_mask1600);
        factory.setSmallIconId(R.mipmap.ic_launcher_round);
        UAirship.shared().getPushManager().editTags()
                .addTag("OnlyMe")
                .apply();

        et = (EditText)findViewById(R.id.em);
        et2 = (EditText)findViewById(R.id.pw);
        Log.d("CHID", "onCreate: "+        AppAutopilot.chId
        );
        //        RequestQueue q = Volley.newRequestQueue(this);
//        fetch(q);
    }

//    private void fetch(RequestQueue requestQueue) {
//        JsonArrayRequest request = new JsonArrayRequest("https://us-central1-kongmalaew-e33a2.cloudfunctions.net/getProducts",
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray jsonArray) {
//                        for(int i = 0; i < jsonArray.length(); i++) {
//                            try {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
////                                mEntries.add(jsonObject.toString());
//                                Log.d("JSONNN", "onResponse: "+jsonObject.toString());
////                                jsonArray.getJSONObject(0).get
//                            }
//                            catch(JSONException e) {
////                                mEntries.add("Error: " + e.getLocalizedMessage());
//                            }
//                        }
//
////                        allDone();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(MainActivity.this, "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

//        mEntries = new ArrayList<>();
//        requestQueue.add(request);
//    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (listener != null) {
            mAuth.removeAuthStateListener(listener);
        }
    }

    public void logIn(View view){
        String m,p;
        m=et.getText().toString();
        p=et2.getText().toString();
        Log.d("SASAS", "logIn: "+m+p);
        mAuth.signInWithEmailAndPassword(m, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
//                            Log.w("DDD", "signInWithEmail", task.getException().getMessage());
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }


}
