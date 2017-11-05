package com.app.mohamedgomaa.book_online;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnnection {
    Context context;
    ConnectivityManager ConMng;

    public CheckConnnection(Context context) {
        this.context = context;
    }

    public boolean IsConnection()
    {
        ConMng = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (ConMng != null){
            NetworkInfo info=ConMng.getActiveNetworkInfo();
            if(info!=null && info.isConnected())
            {
                return true;
            }
        }
        return false;
    }
}
/////////////