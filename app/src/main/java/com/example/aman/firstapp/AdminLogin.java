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

public class AdminLogin extends AppCompatActivity {
    EditText email,password;
    Button login;

    FirebaseAuth mAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference loginref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email=findViewById(R.id.editText5);
        password=findViewById(R.id.editText6);
        login=findViewById(R.id.button12);

        mAuth= FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pwd=password.getText().toString();

                if(mail.isEmpty()||pwd.isEmpty()){
                    Toast.makeText(AdminLogin.this, "", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(mail,pwd).addOnCompleteListener(AdminLogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String uid = mAuth.getUid();
                                loginref.child("Login").child(uid).child("Approve").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String value = dataSnapshot.getValue().toString();
                                        if (Integer.parseInt(value) == 1) {
                                            Toast.makeText(AdminLogin.this,"Login into your Account",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(AdminLogin.this,AdminMain.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(AdminLogin.this,"You are not admin",Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(AdminLogin.this,FrontPage.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            else {
                                Toast.makeText(AdminLogin.this,task.getException().toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });


    }

}
