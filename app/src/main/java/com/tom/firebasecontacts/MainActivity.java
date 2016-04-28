package com.tom.firebasecontacts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1);
        list.setAdapter(adapter);
        Firebase.setAndroidContext(this);
        String url = "https://contacts-example.firebaseio.com/contacts";
        new Firebase(url).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.add(
                        (String) dataSnapshot.child("name").getValue());
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.remove(
                        (String) dataSnapshot.child("name").getValue());
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
