package com.app.mohamedgomaa.book_online;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
public class SqliteDB_favorite extends SQLiteOpenHelper {
    final static String namedb="favorite_data.db";
    ArrayList<ListItems_All_Favorite> myList_AllVideos_Favorite;
    public SqliteDB_favorite(Context contex)
    {super(contex,namedb, null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE favorite(user_name TEXT ,id_movies INTEGER,title TEXT,photo_link TEXT,video_link TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS favorite");
        onCreate(db);
    }
    boolean addFavorite(String user_name,int id_movies,String title,String photo_link,String video_link)
    {
        SQLiteDatabase sqldb=this.getWritableDatabase();
        ContentValues conV=new ContentValues();
        conV.put("user_name",user_name);
        conV.put("id_movies",id_movies);
        conV.put("title",title);
        conV.put("photo_link",photo_link);
        conV.put("video_link",video_link);
        Long i=sqldb.insert("favorite",null,conV);
        if (i == -1) {
            return false;
        } else {
            return true;
        }
    }
    void RemoveFavorite(String user_name,int id_movies)
    {
        SQLiteDatabase sqldb=getWritableDatabase();
        sqldb.execSQL("DELETE FROM favorite where user_name like '"+user_name+"' and id_movies like '"+id_movies+"'");
    }

    boolean checkFavorite(String user_name,int id_movies)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crs=db.rawQuery("select * from favorite where user_name like '"+user_name+"' and id_movies like '"+id_movies+"'",null);
        if(crs.getCount()>0)
        {
            return true;
        }
        else
            return false;

    }
    ArrayList<ListItems_All_Favorite> getList_F_videos(String user_name)
    {
        myList_AllVideos_Favorite=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor crs=db.rawQuery("select * from favorite where user_name like '"+user_name+"'",null);
        crs.moveToFirst();
        while (!crs.isAfterLast())
        {
            myList_AllVideos_Favorite.add(new ListItems_All_Favorite(crs.getInt(1),crs.getString(2),crs.getString(3),crs.getString(4)));
            crs.moveToNext();
        }
        return myList_AllVideos_Favorite;
    }

}
