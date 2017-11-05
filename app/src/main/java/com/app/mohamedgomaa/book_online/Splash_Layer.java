package com.app.mohamedgomaa.book_online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_Layer extends AppCompatActivity {
    User_Info user_info;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__layer);
        ImageView img=(ImageView)findViewById(R.id.imageView6);
        anim= AnimationUtils.loadAnimation(this,R.anim.tranform);
        img.setAnimation(anim);

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    SharedPreferences Read_user_login=getSharedPreferences("user_login",MODE_PRIVATE);
                    boolean check=Read_user_login.getBoolean("check",false);
                    if(check)
                    {
                        user_info=(User_Info)getApplicationContext();
                        user_info.setName(Read_user_login.getString("name",""));
                        user_info.setUser_name(Read_user_login.getString("user_name",""));
                        user_info.setUser_pass(Read_user_login.getString("user_password",""));
                        user_info.setEmail(Read_user_login.getString("user_email",""));
                        user_info.setUrl_img(Read_user_login.getString("url_img",""));
                        startActivity(new Intent(Splash_Layer.this, Show_ALL_Categories.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(Splash_Layer.this, Login_page.class));
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
