package com.app.mohamedgomaa.book_online;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
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
public class Show_Videos extends YouTubeBaseActivity {
    YouTubePlayer.OnInitializedListener onInitializedListener;
    public static final String API_KEY="AIzaSyDPwMEPlp20_pY4_vz7oE_xQxOXensn_Co";
    YouTubePlayerView youtubeV;
    AlertDialog.Builder built;
    User_Info user_ifo;
    EditText ET_comment;
    int id_movie;
    BackgroundTask backT;
    View v_add_comment;
    ListView myList_comment;
    String[]like_user;
    String[]dislike_user;
    TextView No_like,No_dislike,NO_comments;
    Boolean like=true,dislike=true;
    ImageView im_like,im_disLike,im_favorite;
    String title_movie,url_video_Link,url_photo_Link;
    SqliteDB_favorite mySqlite;
    TextView title_T;
    ArrayList<List_array_comments> All_Comment;
    boolean check_fav;
    @Override
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__videos);
        Initialization();
        backT=new BackgroundTask(this);
        backT.execute("add_view",String.valueOf(id_movie));
   ////////////////////////////////////////////////////////////////////////////
        No_like.setText(String.valueOf(like_user.length));
        No_dislike.setText(String.valueOf(dislike_user.length));
        title_T.setText(title_movie);
     //   Intent i=new Intent(Intent.ACTION_SCREEN_OFF);
       // sendBroadcast(i);
        for(String name:like_user)
        {
            if (name.equals(user_ifo.getUser_name())) {
                like = false;
                im_like.setImageResource(R.drawable.f_like);
                break;
            }
        }
        for(String name:dislike_user)
        {
            if (name.equals(user_ifo.getUser_name())) {
                dislike = false;
                im_disLike.setImageResource(R.drawable.f_dislike);
                break;
            }
        }
        new BackGTask_Get_All_Comments().execute(String.valueOf(id_movie));
        check_fav=mySqlite.checkFavorite(user_ifo.getUser_name(),id_movie);
            if(check_fav)
            {
                im_favorite.setImageResource(R.drawable.f_favorite);
            }
        else if(!check_fav)
        {
            im_favorite.setImageResource(R.drawable.t_favorite);
        }

        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(url_video_Link);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        ////////////////////
        v_add_comment=getLayoutInflater().inflate(R.layout.layer_comment_add,null);
        CircleImageView img_user=(CircleImageView)v_add_comment.findViewById(R.id.user_img_add_comment);
        TextView user_name=(TextView)v_add_comment.findViewById(R.id.name_user_add_comment);
        ET_comment=(EditText)v_add_comment.findViewById(R.id.user_comment);
        ///////////////////////////////
        user_name.setText(user_ifo.getName());
        youtubeV.initialize(API_KEY,onInitializedListener);
        Picasso.with(Show_Videos.this).load(Uri.parse(user_ifo.getUrl_img())).fit().centerCrop().into(img_user);
    }
    //////////////////////////////////////////////
    public void add_comment(View view) {
        built=new AlertDialog.Builder(Show_Videos.this);
        built.setTitle("add comment..!");
        built.setView(v_add_comment);
        built.setNegativeButton("post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String content = ET_comment.getText().toString();
                if (!content.equals("")) {
                    backT = new BackgroundTask(Show_Videos.this);
                    String method = "add_comment";
                    backT.execute(method, user_ifo.getUser_name(), String.valueOf(id_movie), content);
                    built.setCancelable(true);
                    final CountDownTimer count = new CountDownTimer(2000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                        }

                        @Override
                        public void onFinish() {
                            new BackGTask_Get_All_Comments().execute(String.valueOf(id_movie));
                        }
                    }.start();

                }
            }
        });

    built.show();
    }
////////////////////////////////////////////////////
    public void Click(View view) {
        if(like&&dislike)
        {
            backT = new BackgroundTask(Show_Videos.this);
            if (view.getId() == R.id.imageView) {
                String method = "add_like";
                im_like.setBackground(getDrawable(R.drawable.f_like));
                 like=false;
                No_like.setText(String.valueOf(like_user.length+1));
                backT.execute(method, String.valueOf(id_movie), user_ifo.getUser_name());
            }
                else if(view.getId()==R.id.imageView3)
            {
                String method = "add_dislike";
                dislike=false;
                No_dislike.setText(String.valueOf(dislike_user.length+1));
                im_disLike.setBackground(getDrawable(R.drawable.f_dislike));
                backT.execute(method, String.valueOf(id_movie),user_ifo.getUser_name());
            }

        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void add_favorite(View view)
    {
        if(!check_fav) {
            im_favorite.setImageResource(R.drawable.f_favorite);
           boolean check=mySqlite.addFavorite(user_ifo.getUser_name(), id_movie, title_movie, url_photo_Link, url_video_Link);
        if(check)
        {
            Toast.makeText(Show_Videos.this,"add to favorite list" , Toast.LENGTH_SHORT).show();
        }
        }
        else if (check_fav)
        {
            im_favorite.setImageResource(R.drawable.t_favorite);
            mySqlite.RemoveFavorite(user_ifo.getUser_name(),id_movie);

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
     public class BackGTask_Get_All_Comments extends AsyncTask<String, Void, String> {
    String Url_Get_All_Comments;
    @Override
    protected void onPreExecute() {
        Url_Get_All_Comments = "https://zeafancom.000webhostapp.com/select_movies_comments.php";
    }

    @Override
    protected String doInBackground(String... params) {
        String id_movies=params[0];
        try {
            URL url = new URL(Url_Get_All_Comments);
            HttpURLConnection httpC_Reg = (HttpURLConnection) url.openConnection();
            httpC_Reg.setRequestMethod("POST");
            httpC_Reg.setDoOutput(true);
            httpC_Reg.setDoInput(true);
            OutputStream os=httpC_Reg.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            String data = URLEncoder.encode("id_movies", "UTF-8") + "=" + URLEncoder.encode(id_movies, "UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();
            InputStream IS = httpC_Reg.getInputStream();
            BufferedReader BR = new BufferedReader(new InputStreamReader(IS));
            String Line;
            String Response = "";
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
        All_Comment=new ArrayList<>();
        try {
            JSONObject JsonObj=new JSONObject(result);
            JSONArray JsonArry=JsonObj.getJSONArray("serverResponse_all_comment");
            for(int i=0;i<JsonArry.length();i++)
            {
                JSONObject J_Ob=JsonArry.getJSONObject(i);
                All_Comment.add(new List_array_comments(J_Ob.getString("name"),J_Ob.getString("img_url"),J_Ob.getString("content")));
            }
            int size=All_Comment.size();
            NO_comments.setText(String.valueOf(size));
            AdapterBase_all_movies adaptObj=new AdapterBase_all_movies(All_Comment);
            myList_comment.setAdapter(adaptObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
    public class AdapterBase_all_movies extends BaseAdapter
    {
        private   ArrayList<List_array_comments> arryL;

        public AdapterBase_all_movies(ArrayList<List_array_comments> arryL) {
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
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=getLayoutInflater().inflate(R.layout.show_all_comments,null);
            TextView user_name=(TextView)v.findViewById(R.id.name_user_comment);
            TextView content=(TextView)v.findViewById(R.id.content_user);
            ImageView img_user=(ImageView) v.findViewById(R.id.user_image_comment);
            user_name.setText(arryL.get(position).name);
            content.setText(arryL.get(position).content);
            Picasso.with(Show_Videos.this).load(arryL.get(position).url_img).into(img_user);
            return v;
        }
    }
void Initialization()
{
    mySqlite= new SqliteDB_favorite(this);
    im_like=(ImageView)findViewById(R.id.imageView);
    NO_comments=(TextView)findViewById(R.id.TxtVno_comment);
    im_disLike=(ImageView)findViewById(R.id.imageView3);
    im_favorite=(ImageView)findViewById(R.id.imageView5);
    title_T=(TextView)findViewById(R.id.title_movie);
    user_ifo=(User_Info)getApplicationContext();
    youtubeV=(YouTubePlayerView)findViewById(R.id.videoView123);
    myList_comment=(ListView)findViewById(R.id.listViewComments);
    title_movie=getIntent().getExtras().getString("title");
    like_user=getIntent().getExtras().getStringArray("user_Likes");
    dislike_user=getIntent().getExtras().getStringArray("user_disLikes");
    url_video_Link=getIntent().getExtras().getString("videoUrl");
    url_photo_Link=getIntent().getExtras().getString("photo_Link");
    id_movie=getIntent().getExtras().getInt("id");
    No_like=(TextView)findViewById(R.id.textView);
    No_dislike=(TextView)findViewById(R.id.textView2);
}



















}