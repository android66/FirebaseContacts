package com.tom.firebasecontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {
    private String url = "https://contacts-example.firebaseio.com";
    private Firebase firebaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase(url);
    }

    public void login(View v){
        String email = ((EditText)findViewById(R.id.email)).getText().toString();
        String password = ((EditText)findViewById(R.id.password)).getText().toString();
        Log.d("AUTH", email + "/" + password);
        firebaseRef.authWithPassword(email, password,
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Log.d("AUTH", "User ID:" + authData.getUid() +
                                ", Provider: " + authData.getProvider());
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {

                    }
                });
    }
}
