package com.parse.tutorials.pushnotifications;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
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
        final ActionBar ab = getActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff2094bd")));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.parseColor("#ff2094bd"));
        listView = (ListView) findViewById(R.id.card_listView);
        listView.setOnScrollListener(new OnScrollListener() {

            int mLastFirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (view.getId() == listView.getId()) {
                    final int currentFirstVisibleItem = listView.getFirstVisiblePosition();

                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                        // getSherlockActivity().getSupportActionBar().hide();
                        findViewById(R.id.llayout).setPadding(0, 0, 0, 0);
                        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#112094bd")));
                        ab.setDisplayShowTitleEnabled(false);
                        ab.setDisplayUseLogoEnabled(false);
                        ab.setDisplayHomeAsUpEnabled(false);
                        ab.setDisplayShowHomeEnabled(false);
                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                        // getSherlockActivity().getSupportActionBar().show();
                        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff2094bd")));
                        findViewById(R.id.llayout).setPadding(0, 75, 0, 0);
                        ab.setDisplayShowTitleEnabled(true);
                        ab.setDisplayUseLogoEnabled(true);
                        ab.setDisplayHomeAsUpEnabled(true);
                        ab.setDisplayShowHomeEnabled(true);
                    }

                    mLastFirstVisibleItem = currentFirstVisibleItem;
                }
            }
        });

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
