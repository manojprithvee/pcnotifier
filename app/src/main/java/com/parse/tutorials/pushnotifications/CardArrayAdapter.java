package com.parse.tutorials.pushnotifications;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardArrayAdapter  extends ArrayAdapter<Card> {

    private List<Card> cardList = new ArrayList<Card>();

    static class CardViewHolder {
        TextView line1;
        TextView line2;
    }

    public CardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(Card object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Card getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.line1);
            viewHolder.line2 = (TextView) row.findViewById(R.id.line2);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Card card = getItem(position);
        viewHolder.line1.setText(card.getLine1());
        viewHolder.line2.setText(card.getLine2());
        return row;
    }
public void repopulate(){
    ArrayList<Card> abc = new ArrayList<>();
    String[] message_list = new String[0],title_list = new String[0];
    try {
        FileInputStream fis=getContext().openFileInput("title.data");
        String title_raw="";
        int c;
        while( (c = fis.read()) != -1){
            title_raw = title_raw + Character.toString((char)c);
        }
        fis.close();
        fis=getContext().openFileInput("message.data");
        String message_raw="";
        int s;
        while( (s = fis.read()) != -1){
            message_raw = message_raw + Character.toString((char)s);
        }
        message_list=message_raw.split("\n");
        title_list=title_raw.split("\n");
        Log.i("message", message_raw);
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
        abc.add(card);
    }
    this.cardList=null;
    this.cardList=abc;
    this.notifyDataSetChanged();
}

}