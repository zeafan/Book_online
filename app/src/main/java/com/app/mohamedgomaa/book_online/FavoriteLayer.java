package com.app.mohamedgomaa.book_online;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteLayer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<ListItems_All_Favorite> myArryList_all_favorite;
    SqliteDB_favorite DB_myFav;
    User_Info user_info;
    ListView LV;
    ImageView imgSearch;
    boolean check_anim=true;
    Animation Anim,Anim2;
    EditText ET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_layer);
        ET=(EditText)findViewById(R.id.search_favorite);
        imgSearch=(ImageView)findViewById(R.id.search_img_fav);
        ET.setVisibility(View.INVISIBLE);
        DB_myFav=new SqliteDB_favorite(FavoriteLayer.this);
        LV = (ListView)findViewById(R.id.listView_favorite);
        user_info=(User_Info) getApplicationContext();
        myArryList_all_favorite=DB_myFav.getList_F_videos(user_info.getUser_name());
       AdapterBase_favorite adaptObj_fav=new AdapterBase_favorite(myArryList_all_favorite);
        LV.setAdapter(adaptObj_fav);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BackGTask_get_Like backGTask_get_like = new BackGTask_get_Like(position);
                backGTask_get_like.execute(String.valueOf(myArryList_all_favorite.get(position).id));
            }

        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_favorite);
        navigationView.setNavigationItemSelectedListener(this);
        View v=getLayoutInflater().inflate(R.layout.nav_header,null);
        CircleImageView image=(CircleImageView)v.findViewById(R.id.imageView876);
        TextView tv=(TextView) v.findViewById(R.id.textV);
        TextView tv_email=(TextView) v.findViewById(R.id.textVEmail);
        tv.setText(user_info.getName());
        tv_email.setText(user_info.getEmail());
        Picasso.with(FavoriteLayer.this).load(Uri.parse(user_info.getUrl_img())).fit().centerCrop().into(image);
        navigationView.addHeaderView(v);
        navigationView.setNavigationItemSelectedListener(this);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Anim= AnimationUtils.loadAnimation(FavoriteLayer.this,R.anim.scale);
                Anim2= AnimationUtils.loadAnimation(FavoriteLayer.this,R.anim.scale2);
                if (check_anim) {
                    ET.setVisibility(View.VISIBLE);
                    ET.setAnimation(Anim);
                    check_anim = false;
                }
                else if (!check_anim)
                {

                    check_anim=true;
                    ET.setAnimation(Anim2);
                    ET.setVisibility(View.INVISIBLE);
                }
            }
        });
        ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String wordSearch = ET.getText().toString();
                int y = 0;
                final int pos[]=new int[myArryList_all_favorite.size()];
                final ArrayList<ListItems_All_Favorite> search_list = new ArrayList<>();
                for (int i = 0; i < myArryList_all_favorite.size(); i++) {
                    String Text = myArryList_all_favorite.get(i).Title;
                    if (Text.contains(wordSearch)) {
                        search_list.add(myArryList_all_favorite.get(i));
                        pos[y]=i;
                        y++;
                    }
                }
               AdapterBase_favorite adaptObj_search=new AdapterBase_favorite(search_list);
                LV.setAdapter(adaptObj_search);
                LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BackGTask_get_Like backGTask_get_like=new BackGTask_get_Like(pos[position]);
                        backGTask_get_like.execute(String.valueOf(search_list.get(position).id));
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }


    public class AdapterBase_favorite extends BaseAdapter
    {
        private   ArrayList<ListItems_All_Favorite> arryL;
        public AdapterBase_favorite(ArrayList<ListItems_All_Favorite> arryL)
        {
            this.arryL = arryL;
        }
        @Override
        public int getCount() {
            return arryL.size();
        }

        @Override
        public Object getItem(int position) {
            return arryL.get(position);
        }

        @Override
        public long getItemId(int position) {
            return arryL.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View Row_View_ALL_Videos=getLayoutInflater().inflate(R.layout.row_favorite,null);
            TextView Title=(TextView)Row_View_ALL_Videos.findViewById(R.id.title321);
            ImageView img=(ImageView) Row_View_ALL_Videos.findViewById(R.id.imageView321);
            Title.setText(arryL.get(position).Title);
            Picasso.with(FavoriteLayer.this).load(arryL.get(position).P_link).into(img);
            return Row_View_ALL_Videos;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(FavoriteLayer.this,Profile_Page.class));

        }
        else if (id == R.id.nav_share) {
            String MessageTXt="تطبيق نورنى";
            String shareLink="";
            Intent in=new Intent(Intent.ACTION_SEND);
            in.setType("text/plain");
            in.putExtra(Intent.EXTRA_TEXT,MessageTXt+"\n"+shareLink);
            startActivity(in);
        } else if (id == R.id.nav_send)
        {
            String Txtmess="السلام عليكم ورحمة الله وبركاتة \nأقتراحى لتطوير البرنامج هو :";
            String[]email={"zeafanm@gmail.com"};
            Intent gmail = new Intent(Intent.ACTION_SEND);
            gmail.setData(Uri.parse("mailto:"));
            gmail.setType("plain/text");
            gmail.putExtra(Intent.EXTRA_EMAIL,email);
            gmail.putExtra(Intent.EXTRA_SUBJECT, "تطبيق نورنى");
            gmail.putExtra(Intent.EXTRA_TEXT,Txtmess);
            startActivity(gmail);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /////////////////////////////////////////////////
    public class BackGTask_get_Like extends AsyncTask<String, Void, String> {
        private int pos;

        public BackGTask_get_Like(int pos) {
            this.pos = pos;
        }

        String Url_GetAllLike;
        @Override
        protected void onPreExecute() {
            Url_GetAllLike ="https://zeafancom.000webhostapp.com/get_User_likes.php";
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String id_movie=params[0];
                URL url = new URL(Url_GetAllLike);
                HttpURLConnection httpC_Reg = (HttpURLConnection) url.openConnection();
                httpC_Reg.setRequestMethod("POST");
                httpC_Reg.setDoOutput(true);
                httpC_Reg.setDoInput(true);
                OutputStream os=httpC_Reg.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("id_mov", "UTF-8") + "=" + URLEncoder.encode(id_movie,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                InputStream IS = httpC_Reg.getInputStream();
                BufferedReader BR = new BufferedReader(new InputStreamReader(IS));
                String Line;
                String Response ="";
                while ((Line = BR.readLine()) != null) {
                    Response += Line;
                }
                BR.close();
                IS.close();
                httpC_Reg.disconnect();
                return Response;
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
                JSONArray JsonArry_like=JsonObj.getJSONArray("serverResponse_ALL_userLike");
                JSONArray JsonArry_dislike=JsonObj.getJSONArray("serverResponse_ALL_userDisLike");
                String []List_user_like=new String[JsonArry_like.length()];
                String []List_user_dislike=new String[JsonArry_dislike.length()];
                for(int i=0;i<JsonArry_like.length();i++)
                {
                    JSONObject J_Ob_l=JsonArry_like.getJSONObject(i);
                    List_user_like[i]= J_Ob_l.getString("user_likes");
                }
                for(int i=0;i<JsonArry_dislike.length();i++)
                {
                    JSONObject J_Ob_d=JsonArry_dislike.getJSONObject(i);
                    List_user_dislike[i]= J_Ob_d.getString("user_dislikes");
                }

                Bundle b=new Bundle();
                b.putInt("id",myArryList_all_favorite.get(pos).id);
                b.putString("videoUrl",myArryList_all_favorite.get(pos).V_link);
                b.putString("title",myArryList_all_favorite.get(pos).Title);
                b.putString("photo_Link",myArryList_all_favorite.get(pos).P_link);
                b.putStringArray("user_Likes",List_user_like);
                b.putStringArray("user_disLikes",List_user_dislike);
                Intent i=new Intent(FavoriteLayer.this,Show_Videos.class);
                i.putExtras(b);
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
