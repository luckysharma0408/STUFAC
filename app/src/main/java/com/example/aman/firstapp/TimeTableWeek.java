package com.example.aman.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TimeTableWeek extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference viewref;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_week);

        Intent intent = getIntent();
        final String branch = intent.getExtras().getString("BRANCH");
        final String year = intent.getExtras().getString("YEAR");
        final String sec = intent.getExtras().getString("SEC");
        viewref = database.getReference("TimeTable").child("BTech").child(branch).child(year).child(sec);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        viewref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String string = dataSnapshot.getKey();
                arrayList.add(string);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str_week = arrayList.get(position);
                Toast.makeText(TimeTableWeek.this, str_week, Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(TimeTableWeek.this,TimeTableLecture.class);
                inte.putExtra("WEEK",str_week);
                inte.putExtra("BRANCH",branch);
                inte.putExtra("YEAR",year);
                inte.putExtra("SEC",sec);
                startActivity(inte);
            }
        });


    }
}
