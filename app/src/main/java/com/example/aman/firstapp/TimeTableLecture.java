package com.example.aman.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimeTableLecture extends AppCompatActivity {
    TextView textView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference viewref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_lecture);
        textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        final String sec = intent.getExtras().getString("SEC");
        final String branch = intent.getExtras().getString("BRANCH");
        final String year = intent.getExtras().getString("YEAR");
        final String week = intent.getExtras().getString("WEEK");
        viewref = database.getReference("TimeTable").child("BTech").child(branch).child(year).child(sec).child(week);


       viewref.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               String timetable = dataSnapshot.getValue().toString();
               Toast.makeText(TimeTableLecture.this,timetable, Toast.LENGTH_SHORT).show();
               textView.setText(timetable);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });

    }
}
