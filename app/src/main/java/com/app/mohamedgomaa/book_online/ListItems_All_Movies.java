package com.app.mohamedgomaa.book_online;

/**
 * Created by Mohamed Gooma on 4/4/2017.
 */

public class ListItems_All_Movies {
    String P_link,Title,V_link;
    int number_views,id;
    public ListItems_All_Movies(int id, String title, String p_link, String v_link,int number_views) {
        this.id = id;
        P_link = p_link;
        this.number_views=number_views;
        Title = title;
        V_link = v_link;
    }
}
