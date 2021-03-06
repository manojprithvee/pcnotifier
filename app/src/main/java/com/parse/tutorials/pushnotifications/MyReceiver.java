package com.parse.tutorials.pushnotifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.OutputStream;

public class MyReceiver extends ParsePushBroadcastReceiver {
    static int id=1;
    private String message;
    private String title;
    private OutputStream outputStream;
    private String app;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);
        Log.d("Push", "Push received");

        if (intent == null)
            return ;

        String jsonData = intent.getExtras().getString("com.parse.Data");

        Log.d("Push", "JSON Data ["+jsonData+"]");


        FileInputStream fis= null;
        try {
            fis=context.openFileInput("message.data");
            String message_raw="";
            int s;
            while( (s = fis.read()) != -1){
                message_raw = message_raw + Character.toString((char)s);
            }
            String[] message_list = message_raw.split("\n");
            if (message_list.length>100){
                outputStream = context.openFileOutput("title.data", Context.MODE_PRIVATE);
                outputStream.write(("").getBytes());
                outputStream.close();
                outputStream = context.openFileOutput("message.data", Context.MODE_PRIVATE);
                outputStream.write(("").getBytes());
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        JSONObject obj = null;
        try {
            obj = new JSONObject(jsonData);
            message=obj.getString("alert");
            title=obj.getString("title");
            app=obj.getString("flag");
            outputStream = context.openFileOutput("title.data", Context.MODE_APPEND);
            outputStream.write((title+"\n").getBytes());
            outputStream.close();
            outputStream = context.openFileOutput("message.data", Context.MODE_APPEND);
            outputStream.write((message+"\n").getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Add custom intent
        PackageManager manager = context.getPackageManager();
        Intent cIntent = manager.getLaunchIntentForPackage("com.estrongs.android.pop");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, cIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Create custom notification
        Notification.Builder  builder = new Notification.Builder(context)
                .setContentText(message)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setGroup("pcnotifer")
                .setVibrate(new long[]{100, 200, 300, 400, 500})
                .setLights(Color.RED, 3000, 3000)
                .setSound(Uri.parse("android.resource://com.parse.tutorials.pushnotifications/" + R.raw.sound));
        if (app.equals("watchseries")){
            builder.setSound(Uri.parse("android.resource://com.parse.tutorials.pushnotifications/" + R.raw.watch));
        }
        Notification notification = builder.build();

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags = (Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR);
        if (Build.VERSION.SDK_INT >= 21)
            notification.visibility = Notification.VISIBILITY_PUBLIC;
        nm.notify(1, notification);
        Log.i("notif", String.valueOf(id));
        try {
            CardListActivity.cardArrayAdapter.repopulate();
        } catch (Exception e) {
        }
        id++;


    }



    private String getData(String jsonData) {
        // Parse JSON Data
        try {
            System.out.println("JSON Data ["+jsonData+"]");
            JSONObject obj = new JSONObject(jsonData);

            return obj.getString("message");
        }
        catch(JSONException jse) {
            jse.printStackTrace();
        }

        return "";
    }

}
