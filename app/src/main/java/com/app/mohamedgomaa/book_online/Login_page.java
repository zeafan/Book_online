package com.app.mohamedgomaa.book_online;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Login_page extends Activity{
    EditText ET_NAME,ET_PASS;
    String login_name,login_pass;
    ProgressBar progB;
    LinearLayout LinearEmail;
    EditText et_mail;
    CheckConnnection check;
    @Override
    protected void onStart() {
        super.onStart();
        progB.setVisibility(View.INVISIBLE);}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page_activity);
        et_mail=(EditText)findViewById(R.id.et_email);
        LinearEmail=(LinearLayout)findViewById(R.id.Linear123);
        check=new CheckConnnection(getApplicationContext());
        ET_NAME = (EditText)findViewById(R.id.user_name);
        ET_PASS = (EditText)findViewById(R.id.user_pass);
        progB=(ProgressBar)findViewById(R.id.progressBar);
        progB.setVisibility(View.INVISIBLE);
    }
    public void userReg(View view)
    {
        if(check.IsConnection()) {
            startActivity(new Intent(this, Register_Page.class));
        }
        else {            Toast.makeText(this, "no connection with internet", Toast.LENGTH_SHORT).show();
        }
    }
    public void userLogin(View view) {
        if(check.IsConnection())
        {
            progB.setVisibility(View.VISIBLE);
            login_name = ET_NAME.getText().toString();
            login_pass = ET_PASS.getText().toString();
            String method = "login";
            BackgroundTask BackT = new BackgroundTask(this);
            BackT.execute(method, login_name, login_pass);
            final CountDownTimer count = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {

                    if (BackgroundTask.check == 2) {
                        BackGTask_GET_ALL_INFO_USER getUserInfo=new BackGTask_GET_ALL_INFO_USER(Login_page.this);
                        getUserInfo.execute(login_name, login_pass);
                    } else if (BackgroundTask.check == 0) {
                        secondCount();
                    } else {
                        progB.setVisibility(View.INVISIBLE);
                    }
                }
            }.start();
        }
        else {
            Toast.makeText(this, "no connection with internet", Toast.LENGTH_SHORT).show();
        }
    }
    void secondCount()
    {
        final CountDownTimer count2=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                    if (BackgroundTask.check == 2) {

                        BackGTask_GET_ALL_INFO_USER getUserInfo = new BackGTask_GET_ALL_INFO_USER(Login_page.this);
                        getUserInfo.execute(login_name, login_pass);
                    } else if (BackgroundTask.check == 0) {
                        secondCount();
                    }
                    else {
                        progB.setVisibility(View.INVISIBLE);
                    }
            }
        }.start();
    }
    void secondCount2()
    {
        final CountDownTimer count2=new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(BackgroundTask.checkEmail==2) {
                    progB.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login_page.this,"check you Email box", Toast.LENGTH_LONG).show();

                }
                else if(BackgroundTask.checkEmail==0) {
                    secondCount2();
                }
                else {
                    progB.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login_page.this,"this Email not Exist  ", Toast.LENGTH_LONG).show();
                }
            }
        }.start();
    }
    long time;
    @Override
    public void onBackPressed() {
        if(time+1500>System.currentTimeMillis())
        {
            super.onBackPressed();
        }else
        {
            Toast.makeText(this, "أضغط مرة أخرى للخروج", Toast.LENGTH_SHORT).show();
        }

        time=System.currentTimeMillis();
    }

    User_Info user_info;

    public void Email(View view) {
        if(check.IsConnection()) {
            LinearEmail.setVisibility(View.VISIBLE);
        }
        else {
            Toast.makeText(Login_page.this, "no connection internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void send_email(View view) {
        String Email=et_mail.getText().toString();
        if(!Email.equals(""))
        {
            String method = "Send_Email";
            BackgroundTask BackT = new BackgroundTask(this);
            BackT.execute(method,Email);
            final CountDownTimer count = new CountDownTimer(2000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    progB.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFinish() {

                    if (BackgroundTask.checkEmail == 2) {
                        progB.setVisibility(View.INVISIBLE);
                        Toast.makeText(Login_page.this,"check you Email box", Toast.LENGTH_LONG).show();
                    } else if (BackgroundTask.checkEmail == 0) {
                        secondCount2();
                    } else {
                        progB.setVisibility(View.INVISIBLE);
                        Toast.makeText(Login_page.this,"this Email not Exist  ", Toast.LENGTH_LONG).show();
                    }
                }
            }.start();

        }
        else
        {
            Toast.makeText(Login_page.this,"please insert your Email", Toast.LENGTH_SHORT).show();
        }

    }

    //////////////////////////////////////////////////////////////////////////////
    public class BackGTask_GET_ALL_INFO_USER extends AsyncTask<String, Void,String> {
    private Context con;
        String Url_User_Info;
    public BackGTask_GET_ALL_INFO_USER(Context con) {
        this.con = con;
    }

    @Override
        protected void onPreExecute() {

        Url_User_Info = "https://zeafancom.000webhostapp.com/Info_user.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String login_name=params[0];
            String login_pass=params[1];
            try {
                URL url = new URL(Url_User_Info);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("login_name", "UTF-8") + "=" + URLEncoder.encode(login_name, "UTF-8") + "&" +
                        URLEncoder.encode("login_pass", "UTF-8") + "=" + URLEncoder.encode(login_pass, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream is=httpURLConnection.getInputStream();
                BufferedReader BR=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String resopnse="";
                String Line;
                while ((Line=BR.readLine())!=null)
                {
                    resopnse=resopnse+Line;
                }
                BR.close();
                is.close();
                httpURLConnection.disconnect();
                return resopnse;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject JsonObj=new JSONObject(result);
                JSONArray JsonArry=JsonObj.getJSONArray("serverResponse_user_info");
                    JSONObject J_Ob=JsonArry.getJSONObject(0);
                user_info=(User_Info)getApplicationContext();
                user_info.setName(J_Ob.getString("name"));
                user_info.setUser_name(J_Ob.getString("user_name"));
                user_info.setUser_pass(J_Ob.getString("user_pass"));
                user_info.setEmail(J_Ob.getString("email"));
                user_info.setUrl_img(J_Ob.getString("url_img"));

                    SharedPreferences file_user_Login=getSharedPreferences("user_login",MODE_PRIVATE);
                    SharedPreferences.Editor edit=file_user_Login.edit();
                    edit.putBoolean("check",true);
                    edit.putString("name",J_Ob.getString("name"));
                    edit.putString("user_name",J_Ob.getString("user_name"));
                    edit.putString("user_password",J_Ob.getString("user_pass"));
                    edit.putString("user_email",J_Ob.getString("email"));
                    edit.putString("url_img",J_Ob.getString("url_img"));
                    edit.apply();

              Intent i = new Intent(con,Show_ALL_Categories.class);
              startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
