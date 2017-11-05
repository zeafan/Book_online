package com.app.mohamedgomaa.book_online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Show_ALL_Categories extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GridView gridView;
    User_Info userInf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<ListItems_image> list=new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__all__categories);
        userInf=(User_Info)getApplicationContext();
         gridView = (GridView) findViewById(R.id.gridView);
        list.add(new ListItems_image(R.drawable.cat1));
        list.add(new ListItems_image(R.drawable.cat2));
        list.add(new ListItems_image(R.drawable.cat3));
        list.add(new ListItems_image(R.drawable.cat4));
        Connection con=new Connection(list);
        gridView.setAdapter(con);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View v=getLayoutInflater().inflate(R.layout.nav_header,null);
        //ImageView image=(ImageView)v.findViewById(R.id.imageView876);
       CircleImageView image=(CircleImageView)v.findViewById(R.id.imageView876);
        TextView tv=(TextView) v.findViewById(R.id.textV);
        TextView tv_email=(TextView) v.findViewById(R.id.textVEmail);
        tv.setText(userInf.getName());
        tv_email.setText(userInf.getEmail());
        Picasso.with(Show_ALL_Categories.this).load(Uri.parse(userInf.getUrl_img())).fit().centerCrop().into(image);
        navigationView.addHeaderView(v);
        navigationView.setNavigationItemSelectedListener(this);
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
            SharedPreferences Login_out=getSharedPreferences("user_login",MODE_PRIVATE);
            SharedPreferences.Editor edit=Login_out.edit();
            edit.clear();
            edit.apply();
            finish();
            System.exit(0);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            startActivity(new Intent(Show_ALL_Categories.this,Profile_Page.class));

        } else if (id == R.id.nav_favorite) {
            startActivity(new Intent(Show_ALL_Categories.this, FavoriteLayer.class));

        }else if (id == R.id.nav_share) {
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
    class Connection extends BaseAdapter
    {
        private ArrayList<ListItems_image>myList;

        public Connection(ArrayList<ListItems_image> myList) {
            this.myList = myList;
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public Object getItem(int position) {
            return myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return myList.get(position).imageId;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view=getLayoutInflater().inflate(R.layout.row_all_catigory,null);
            ImageView img=(ImageView)view.findViewById(R.id.imageView12554);
            img.setImageResource(myList.get(position).imageId);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(Show_ALL_Categories.this,Show_one_category.class);
                    i.putExtra("key",position+1);
                    startActivity(i);
                }
            });
            return view;
        }
    }

}
