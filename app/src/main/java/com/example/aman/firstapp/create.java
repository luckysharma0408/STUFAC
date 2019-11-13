package com.example.aman.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class create extends AppCompatActivity {

     EditText branch,year,week,sec,l1,l2,l3,l4,l5,l6,l7;
     Button createbtn;

     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference timeref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        branch = (EditText) findViewById(R.id.branch);
        year = (EditText) findViewById(R.id.year);
        week = (EditText) findViewById(R.id.week);
        sec = (EditText) findViewById(R.id.sec);
        l1 = (EditText) findViewById(R.id.lecture1);
        l2 = (EditText) findViewById(R.id.lecture2);
        l3 = (EditText) findViewById(R.id.lecture3);
        l4 = (EditText) findViewById(R.id.lecture4);
        l5 = (EditText) findViewById(R.id.lecture5);
        l6 = (EditText) findViewById(R.id.lecture6);
        l7 = (EditText) findViewById(R.id.lecture7);

        createbtn = (Button) findViewById(R.id.createbtn);

        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String branch_str = branch.getText().toString().toUpperCase();
                String year_str = year.getText().toString();
                String week_str = week.getText().toString().toUpperCase();
                String sec_str = sec.getText().toString().toUpperCase();
                String l1_str = l1.getText().toString();
                String l2_str = l2.getText().toString();
                String l3_str = l3.getText().toString();
                String l4_str = l4.getText().toString();
                String l5_str = l5.getText().toString();
                String l6_str = l6.getText().toString();
                String l7_str = l7.getText().toString();

                String final_value = l1_str+" "+l2_str+" "+l3_str+" "+l4_str+" "+l5_str+" "+l6_str+" "+l7_str;
                Toast.makeText(create.this, final_value, Toast.LENGTH_SHORT).show();

                timeref.child("TimeTable").child("BTech").child(branch_str).child(year_str).child(sec_str).child(week_str).setValue(final_value);

            }
        });


    }
}
