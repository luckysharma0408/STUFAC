package com.example.aman.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentRegistration extends AppCompatActivity {
    EditText email,password;
    Button register;
    FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference loginref = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);
        email=findViewById(R.id.editText);
        password=findViewById(R.id.editText4);
        register=findViewById(R.id.button);

        mAuth= FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pwd=password.getText().toString();
                if(mail.isEmpty()||pwd.isEmpty()){
                    Toast.makeText(StudentRegistration.this, "", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(mail,pwd).addOnCompleteListener(StudentRegistration.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String uid = mAuth.getUid();
                                loginref.child("Login").child(uid).child("Status").setValue("3");
                                loginref.child("Login").child(uid).child("Approve").setValue("0");
                                Toast.makeText(StudentRegistration.this,"You have been registered", Toast.LENGTH_SHORT).show();
                                loginref.child("Login").child(uid).child("Approve").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String value = dataSnapshot.getValue().toString();
                                        if (Integer.parseInt(value) == 3) {
                                            Intent intent=new Intent(StudentRegistration.this,StudentMain.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(StudentRegistration.this,"Wait for the Approval", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(StudentRegistration.this,FrontPage.class);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        }
                    });
                }
            }
        });


    }
}
