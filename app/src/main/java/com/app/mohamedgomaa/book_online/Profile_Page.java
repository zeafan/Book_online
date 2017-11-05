package com.app.mohamedgomaa.book_online;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class Profile_Page extends AppCompatActivity {
    ImageView img;
    TextView TxtV;
    User_Info user_info;
    ProgressBar progress;
    BackgroundTask backTast_update;
    String downloadString;
    private StorageReference mStorageRef;
    StorageReference riversRef;
    AlertDialog.Builder change_name;
    EditText ETxt;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__page);
        img = (ImageView) findViewById(R.id.Img_profile);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        TxtV = (TextView) findViewById(R.id.name);
        user_info = (User_Info) getApplicationContext();

        Picasso.with(this).load(user_info.getUrl_img()).into(img);
        TxtV.setText(user_info.getName());
        progress = (ProgressBar) findViewById(R.id.Progress12345);
        progress.setVisibility(View.INVISIBLE);
        v = getLayoutInflater().inflate(R.layout.change_name, null);
        ETxt = (EditText) v.findViewById(R.id.editText);
    }

    public void Change_Img(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data12345) {
        try {
            super.onActivityResult(requestCode, resultCode, data12345);
            Uri uri123 = data12345.getData();
            Random R = new Random();
            int x = R.nextInt(10000000);
            int y = R.nextInt(10000000);
            riversRef = mStorageRef.child("images/" + x * y);
            riversRef.putFile(uri123)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            downloadString = downloadUri.toString();
                            Picasso.with(Profile_Page.this).load(downloadUri).centerCrop().fit().into(img);
                            progress.setVisibility(View.INVISIBLE);
                            String method = "change_image";
                            user_info.setUrl_img(downloadString);
                            backTast_update = new BackgroundTask(Profile_Page.this);
                            backTast_update.execute(method, user_info.getUser_name(), downloadString);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Profile_Page.this, "failure in upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void change_profile_name(View view) {
        change_name = new AlertDialog.Builder(Profile_Page.this);
        backTast_update = new BackgroundTask(Profile_Page.this);
        change_name.setMessage("change your name");
        change_name.setView(v);
        change_name.setNegativeButton("change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!ETxt.getText().toString().equals("")) {
                    String method = "change_name";
                    String name = ETxt.getText().toString();
                    user_info.setName(name);
                    TxtV.setText(name);
                    backTast_update.execute(method, user_info.getUser_name(), name);
                    change_name.setCancelable(true);
                } else
                    Toast.makeText(Profile_Page.this, "please inter your name", Toast.LENGTH_SHORT).show();
            }
        });
        change_name.show();
    }


    public void change_pass(View view)
    {
        Intent i = new Intent(Profile_Page.this, Updata_Page.class);
        startActivity(i);
    }
}
