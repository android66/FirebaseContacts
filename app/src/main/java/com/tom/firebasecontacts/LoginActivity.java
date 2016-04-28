package com.tom.firebasecontacts;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        final String email = ((EditText)findViewById(R.id.email)).getText().toString();
        final String password = ((EditText)findViewById(R.id.password)).getText().toString();
        Log.d("AUTH", email + "/" + password);
        firebaseRef.authWithPassword(email, password,
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Log.d("AUTH", "User ID:" + authData.getUid() +
                                ", Provider: " + authData.getProvider());
                        Toast.makeText(getApplicationContext(), "登入成功",
                                Toast.LENGTH_LONG).show();
                        getSharedPreferences("contacts", MODE_PRIVATE)
                                .edit()
                                .putString("USERID", authData.getUid())
                                .commit();
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError error) {
                        switch (error.getCode()){
                            case FirebaseError.USER_DOES_NOT_EXIST:
                                Log.d("ERROR", "NOT EXISTS");
                                register(email, password);
                                break;
                            case FirebaseError.INVALID_PASSWORD:
                                //密碼錯誤
                                break;
                            default:
                                //其他錯誤
                                break;
                        }
                    }
                });
    }

    private void register(final String email, final String password) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("登入問題")
                .setMessage(" 無此帳號，是否要以此帳號與密碼註冊 ?")
                .setPositiveButton("註冊", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseRef.createUser(email, password,
                                new Firebase.ResultHandler() {
                                    @Override
                                    public void onSuccess() {
                                        new AlertDialog.Builder(LoginActivity.this)
                                                .setMessage("註冊成功")
                                                .setPositiveButton("OK", null)
                                                .show();
                                    }
                                    @Override
                                    public void onError(FirebaseError firebaseError) {

                                    }
                                });
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }
}
