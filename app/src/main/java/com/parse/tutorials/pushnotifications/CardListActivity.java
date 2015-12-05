package com.parse.tutorials.pushnotifications;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CardListActivity extends Activity {

    private static final String TAG = "CardListActivity";
    public static CardArrayAdapter cardArrayAdapter;
    private ListView listView;
    private String[] title_list,message_list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        listView = (ListView) findViewById(R.id.card_listView);

        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
        try {
            FileInputStream fis=openFileInput("title.data");
            String title_raw="";
            int c;
            while( (c = fis.read()) != -1){
                title_raw = title_raw + Character.toString((char)c);
            }
            fis.close();
            fis=openFileInput("message.data");
            String message_raw="";
            int s;
            while( (s = fis.read()) != -1){
                message_raw = message_raw + Character.toString((char)s);
            }
            message_list=message_raw.split("\n");
            title_list=title_raw.split("\n");
            Log.i("message",message_raw);
            Log.i("title",title_raw);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> a= Arrays.asList(message_list);
        Collections.reverse(a);
        message_list=(String[])a.toArray();
        a= Arrays.asList(title_list);
        Collections.reverse(a);
        title_list=(String[])a.toArray();

        for (int i = 0; i < message_list.length; i++) {
            Card card = new Card(title_list[i], message_list[i]);
            cardArrayAdapter.add(card);
        }
        listView.setAdapter(cardArrayAdapter);
    }
}