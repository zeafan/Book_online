package com.app.mohamedgomaa.book_online;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class Updata_Page extends AppCompatActivity {
EditText username,C_pass,N_pass;
    String updata_username,updata_pass1,updata_pass2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        username=(EditText)findViewById(R.id.new_name_updata);
        C_pass=(EditText)findViewById(R.id.current_password);
        N_pass=(EditText)findViewById(R.id.new_password);
    }

    public void userUpdata(View view) {
     updata_username=username.getText().toString().trim();
        updata_pass1=C_pass.getText().toString().trim();
        updata_pass2=N_pass.getText().toString().trim();
        BackgroundTask backT=new BackgroundTask(Updata_Page.this);
        String method="Update";
        backT.execute(method,updata_username,updata_pass1,updata_pass2);
        finish();
    }

    public void showPass(View view) {
        C_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        N_pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    }
}
