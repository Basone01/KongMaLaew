package com.advpro2.basone.kongmalaew;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    static String TAG = "LOGIN";
    LoginButton loginButton;
    CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

// ...
// Initialize Firebase Auth

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        PrintHashKeyInLog();
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.fb_login_btn);

        callbackManager = CallbackManager.Factory.create();




        // ..
        LoginManager.getInstance().logInWithReadPermissions(
                this,
                Arrays.asList("user_friends", "email", "public_profile"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {
                loginButton.setVisibility(View.GONE);
                handleFacebookAccessToken(loginResult.getAccessToken());
    //                textView.setText("Log In Successed \n"
    //                +loginResult.getAccessToken().getUserId()+"\n"+loginResult.getAccessToken().getToken()
    //                +"\n"+loginResult.getAccessToken().getPermissions());

                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                    Log.i("getUserId", loginResult.getAccessToken().getUserId());
    //                Intent intent = new Intent(MainActivity.this,HomeActivity.class);

    //                Singleton.getInstance().setFirstName("LOVE");


                setFacebookData(loginResult);


            }



            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

            }


        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override

            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged: ");
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:");
                }
                // ...
            }
        };

    }


    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential   = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            loginButton.setVisibility(View.VISIBLE);
        }

    }


    public void setFacebookData(final LoginResult loginResult)
    {

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

//                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        // Application code
                        try {
                            Log.i("Response",response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");

                            String profilePic= Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString();

                            Profile profile = Profile.getCurrentProfile();
//                            String id = profile.getId();
                            String link = profile.getLinkUri().toString();
                            Log.i("Link",link);
                            if (Profile.getCurrentProfile()!=null)
                            {
                                Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
//                                Picasso.with(getApplicationContext()).load(Profile.getCurrentProfile().getProfilePictureUri(200, 200)).into((Target) profilePictureView);
                            }


                            Log.i("Login" + "Email", email);

                            Log.i("Login"+ "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            Log.i("Login" + "Gender", gender);

                            Singleton.getInstance().setFirstName(firstName);
                            Singleton.getInstance().setLastName(lastName);
                            Singleton.getInstance().setProfilePic(profilePic);
                            Singleton.getInstance().setEmail(email);
//                            textView.setText(firstName +"\n" +lastName +"\n"+ Singleton.getInstance().getFirstName());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivityForResult(intent, 1);
//                            startActivity(intent);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();

    }


}
