package com.example.aman.firstapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class home extends AppCompatActivity {
    private Button fileupload;
    private Button fileretrive;
    private StorageReference mStorage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference pdfRef = database.getReference();
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;
    private EditText filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // button refrence for pdf upload
        fileupload = (Button) findViewById(R.id.fileuplaod);
        // button reffrence for pdf retrive
        fileretrive = (Button) findViewById(R.id.fileretrive);
        filename = (EditText) findViewById(R.id.filename);

        mStorage = FirebaseStorage.getInstance().getReference();

        mProgressDialog = new ProgressDialog(this);

        fileupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,GALLERY_INTENT);

            }
        });

        fileretrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(home.this,retriveactivity.class);
                startActivity(inte);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            mProgressDialog.setMessage("Pdf Uploading");
            mProgressDialog.show();
            final Uri uri = data.getData();
            StorageReference filepath =  mStorage.child("Pdfs").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(home.this, "Upload Done", Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    pdfRef.child("ALLPDF").child(filename.getText().toString()).setValue(downloadUri.toString());
                }
            });
        }

    }
}
