package com.app.mohamedgomaa.book_online;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Switch;
import android.widget.Toast;

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

public class BackgroundTask extends AsyncTask<String,Void,String> {
private Context con;
    public BackgroundTask(Context mycon) {
       this.con=mycon;
    }

    @Override
    protected void onPreExecute() {
    }
    @Override
    protected String doInBackground(String... params) {
        String Key=params[0];
        String reg_url="https://zeafancom.000webhostapp.com/register.php";
        String login_url="https://zeafancom.000webhostapp.com/Login.php";
        String Update_url="https://zeafancom.000webhostapp.com/Updata.php";
        String add_comment_url="https://zeafancom.000webhostapp.com/add_comment.php";
        String add_like_url="https://zeafancom.000webhostapp.com/add_like.php";
        String add_dislike_url="https://zeafancom.000webhostapp.com/add_dislike.php";
        String add_view_url="https://zeafancom.000webhostapp.com/number_views.php";
        String change_image_url="https://zeafancom.000webhostapp.com/updata_img.php";
        String change_name_url="https://zeafancom.000webhostapp.com/updata_name.php";
        String forgen_pass_url="https://zeafancom.000webhostapp.com/Forgin_password.php";

        if(Key.equals("Update"))
        {
            String user_name=params[1];
            String user_pass1=params[2];
            String user_pass2=params[3];
            try {
                URL url = new URL(Update_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass1", "UTF-8") + "=" + URLEncoder.encode(user_pass1, "UTF-8")+ "&"
                        + URLEncoder.encode("user_pass2", "UTF-8") + "=" + URLEncoder.encode(user_pass2, "UTF-8");
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

        }
       else if(Key.equals("login"))
        {
            String login_name=params[1];
            String login_pass=params[2];
            try {
                URL url = new URL(login_url);
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
        }
      else if(Key.equals("register"))
        {
            String name=params[1];
            String user_name=params[2];
            String user_pass=params[3];
            String Email=params[4];
            String Image_Uri=params[5];
            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                        URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(user_pass, "UTF-8") + "&" +
                        URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(Email, "UTF-8") + "&" +
                        URLEncoder.encode("user_image", "UTF-8") + "=" + URLEncoder.encode(Image_Uri, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
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
        }

         else if(Key.equals("add_comment"))
         {
             String user_name=params[1];
             String id_movies=params[2];
             String content=params[3];
             try {
                 URL url = new URL(add_comment_url);
                 HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                 httpURLConnection.setRequestMethod("POST");
                 httpURLConnection.setDoOutput(true);
                 OutputStream OS = httpURLConnection.getOutputStream();
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                 String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                         URLEncoder.encode("id_movies", "UTF-8") + "=" + URLEncoder.encode(id_movies, "UTF-8") + "&" +
                         URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8") ;
                 bufferedWriter.write(data);
                 bufferedWriter.flush();
                 bufferedWriter.close();
                 OS.close();
                 InputStream IS = httpURLConnection.getInputStream();
                 IS.close();
                 return "add Success...";
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
        else if(Key.equals("add_view"))
        {
            String id_movie=params[1];
            try {
                URL url = new URL(add_view_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("id_movie", "UTF-8") + "=" + URLEncoder.encode(id_movie, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "add view...";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(Key.equals("Send_Email"))
        {
            String email=params[1];
            try {
                URL url = new URL(forgen_pass_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
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
        }
         else if(Key.equals("add_like"))
         {
             String id_movies=params[1];
             String user_name=params[2];
             try {
                 URL url = new URL(add_like_url);
                 HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                 httpURLConnection.setRequestMethod("POST");
                 httpURLConnection.setDoOutput(true);
                 OutputStream OS = httpURLConnection.getOutputStream();
                 BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                 String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                         URLEncoder.encode("id_movies", "UTF-8") + "=" + URLEncoder.encode(id_movies, "UTF-8");
                 bufferedWriter.write(data);
                 bufferedWriter.flush();
                 bufferedWriter.close();
                 OS.close();
                 InputStream IS = httpURLConnection.getInputStream();
                 IS.close();
                 return "add like";
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
        else if(Key.equals("add_dislike"))
        {
            String user_name=params[2];
            String id_movies=params[1];
            try {
                URL url = new URL(add_dislike_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("id_movies", "UTF-8") + "=" + URLEncoder.encode(id_movies, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "add dislike";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(Key.equals("change_image"))
        {
            String user_name=params[1];
            String new_url_img=params[2];
            try {
                URL url = new URL(change_image_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("url_img", "UTF-8") + "=" + URLEncoder.encode(new_url_img, "UTF-8");
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
        }
        else if(Key.equals("change_name"))
        {
            String user_name=params[1];
            String new_name=params[2];
            try {
                URL url = new URL(change_name_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" +
                        URLEncoder.encode("new_name", "UTF-8") + "=" + URLEncoder.encode(new_name, "UTF-8");
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
        }
        return null;
    }
   static int check=0;
    static int checkReg=0;
    static int checkEmail=0;
    @Override
    protected void onPostExecute(String Result) {
        String result=Result;
         if  (Result.equals("Login Failed.....Try Again..")) {
             Toast.makeText(con, Result, Toast.LENGTH_LONG).show();
              check=1;
            }
         else if  (Result.equals("Login Success..Welcome"))
            {
                Toast.makeText(con, Result, Toast.LENGTH_LONG).show();
                check = 2;
            }
       if (Result.equals("failure where registered"))
       {
    checkReg=1;
       }
       else if(Result.equals("Register Success"))
       {
     checkReg=2;
       }
        if (Result.equals("failure"))
        {
            checkEmail=1;
        }
        else if(Result.equals("successful"))
        {
            checkEmail=2;
        }
    }
}
