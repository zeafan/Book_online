package com.app.mohamedgomaa.book_online;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register_Page extends AppCompatActivity {
EditText ET_name,ET_userName,ET_password,ET_email;
    String name,user_name,Password,Email123;
    CircleImageView ImgBtn;
    String downloadString;
    Button BtnReg;
    ProgressBar progress;
    boolean check=false;
    boolean CheckEye=true;
    ImageView eye;
    private StorageReference mStorageRef;
     StorageReference riversRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        ET_name=(EditText)findViewById(R.id.new_name);
        ET_userName=(EditText)findViewById(R.id.new_user_name);
        ET_password=(EditText)findViewById(R.id.new_user_pass);
        ET_email=(EditText)findViewById(R.id.Email);
        eye=(ImageView)findViewById(R.id.eye);
        ImgBtn=(CircleImageView) findViewById(R.id.image_register);
        BtnReg=(Button)findViewById(R.id.btn_reg);
        progress=(ProgressBar)findViewById(R.id.Progress12);
        progress.setVisibility(View.INVISIBLE);
    }
    public void userReg(View view) {
        name=ET_name.getText().toString().trim();
        user_name=ET_userName.getText().toString().trim();
        Password=ET_password.getText().toString().trim();
        Email123=ET_email.getText().toString().trim();
        if(check&&CheckEmail()&&Checkpassword()&&CheckEmpty())
        {
             BackgroundTask backT = new BackgroundTask(Register_Page.this);
             String method = "register";
             backT.execute(method,name,user_name,Password,Email123,downloadString);
            final CountDownTimer count = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    progress.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFinish() {

                    if (BackgroundTask.checkReg == 2) {
                        progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(Register_Page.this, "successful register", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (BackgroundTask.checkReg == 0) {
                        secondCount();
                    } else if (BackgroundTask.checkReg == 1) {
                        progress.setVisibility(View.INVISIBLE);
                        Toast.makeText(Register_Page.this, "failure register Enter another user name", Toast.LENGTH_LONG).show();
                    }
                }
            }.start();

    //
        }
        else
            Toast.makeText(this, "there error", Toast.LENGTH_SHORT).show();


    }
    void secondCount()
    {
        final CountDownTimer count2=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                if(BackgroundTask.checkReg==2) {
                    progress.setVisibility(View.INVISIBLE);
                    Toast.makeText(Register_Page.this, "successful register", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(BackgroundTask.checkReg==0) {
                    secondCount();
                }
            }
        }.start();
    }
    boolean CheckEmpty() {
        if (!name.equals("") && !user_name.equals("") && !Password.equals("") && !Email123.equals("")) {
           return true;
        }
        else
            Toast.makeText(this, "please insert all data", Toast.LENGTH_SHORT).show();
            return false;
    }


    boolean Checkpassword()
    {
        if(Password.length()>4)
        {
            return true;
        }
        ET_password.setError("short password please insert more 4 character");
        Toast.makeText(this, "insert more 4 character", Toast.LENGTH_SHORT).show();
        return false;
    }
    boolean CheckEmail()
    {
        if(Email123.contains("@")&&Email123.contains(".com"))
        {
            return true;
        }
        ET_email.setError("incorrect email");
        Toast.makeText(this, "incorrect email", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void BtnAccount(View view) {
        Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i,1);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data12345)
    {
        try{
        super.onActivityResult(requestCode, resultCode, data12345);
            Uri uri123 = data12345.getData();
            Random R = new Random();
            int x = R.nextInt(10000000);
            int y = R.nextInt(10000000);
            riversRef = mStorageRef.child("images/"+x*y);
            riversRef.putFile(uri123)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            downloadString=downloadUri.toString();
                            Picasso.with(Register_Page.this).load(downloadUri).centerCrop().fit().into(ImgBtn);
                            check=true;
                            progress.setVisibility(View.INVISIBLE);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Register_Page.this, "failure in upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void eye(View view) {
        if(CheckEye) {
            eye.setImageResource(R.drawable.f_eye);
            CheckEye = false;
            ET_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        }
    }
}
