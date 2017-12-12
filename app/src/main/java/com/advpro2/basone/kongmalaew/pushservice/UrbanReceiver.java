package com.advpro2.basone.kongmalaew.pushservice;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.advpro2.basone.kongmalaew.DetailActivity;
import com.advpro2.basone.kongmalaew.MainActivity;
import com.urbanairship.AirshipReceiver;
import com.urbanairship.push.PushMessage;

import org.json.JSONException;
import org.json.JSONObject;


public class UrbanReceiver extends AirshipReceiver {

    private static final String TAG = "UrbanReceiver";

    @Override
    protected void onChannelCreated(@NonNull Context context, @NonNull String channelId) {
        Log.i(TAG, "Channel created. Channel Id:" + channelId + ".");
        AppAutopilot.chId=channelId;
    }

    @Override
    protected void onChannelUpdated(@NonNull Context context, @NonNull String channelId) {
        Log.i(TAG, "Channel updated. Channel Id:" + channelId + ".");
        AppAutopilot.chId=channelId;


    }

    @Override
    protected void onChannelRegistrationFailed(@NonNull Context context) {
        Log.i(TAG, "Channel registration failed.");
    }

    @Override
    protected void onPushReceived(@NonNull Context context, @NonNull PushMessage message, boolean notificationPosted) {
        super.onPushReceived(context, message, notificationPosted);
        Log.i(TAG, "Received push message. Alert: " + message.getAlert() + ". posted notification: " + notificationPosted);
    }

    @Override
    protected void onNotificationPosted(@NonNull Context context, @NonNull NotificationInfo notificationInfo) {
        super.onNotificationPosted(context, notificationInfo);
        Log.i(TAG, "Notification posted. Alert: " + notificationInfo.getMessage().getAlert() + ". NotificationId: " + notificationInfo.getNotificationId());
//        Log.d(TAG, "onNotificationPosted: "+        notificationInfo.getMessage().toJsonValue().toString());
    }
//
    @Override
    protected boolean onNotificationOpened(@NonNull Context context, @NonNull NotificationInfo notificationInfo) {
        Log.i(TAG, "Notification opened. Alert: " + notificationInfo.getMessage().getAlert() + ". NotificationId: " + notificationInfo.getNotificationId());
        Intent notiOpened = new Intent(context, DetailActivity.class);
        JSONObject obj = new JSONObject();

        PushMessage message = notificationInfo.getMessage();
        String type = message.getExtra("type","none");
        if(type.equals("New Product")){
            try {
                obj.put("product_img",message.getExtra("product_img","none"));
                obj.put("product_uploadby",message.getExtra("product_uploadby","none"));
                obj.put("product_detail",message.getExtra("product_detail","none"));
                obj.put("product_brand",message.getExtra("product_brand","none"));
                obj.put("product_name",message.getExtra("product_name","none"));
                obj.put("product_id",message.getExtra("product_id","none"));
                obj.put("product_price",Double.parseDouble(message.getExtra("product_price","none")));

                notiOpened.putExtra("product_img",message.getExtra("product_img","none"));
                notiOpened.putExtra("product_uploadby",message.getExtra("product_uploadby","none"));
                notiOpened.putExtra("product_detail",message.getExtra("product_detail","none"));
                notiOpened.putExtra("product_brand",message.getExtra("product_brand","none"));
                notiOpened.putExtra("product_name",message.getExtra("product_name","none"));
                notiOpened.putExtra("product_id",message.getExtra("product_id","none"));
                notiOpened.putExtra("product_price",Double.parseDouble(message.getExtra("product_price","none")));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            Log.d(TAG, "onNotificationOpened: "+obj.toString(5));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        context.startActivity(notiOpened);

        Log.e(TAG, "onNotificationOpened: "+notificationInfo.getMessage().toJsonValue() );

        // Return false here to allow Urban Airship to auto launch the launcher activity
        return true;
    }

    @Override
    protected boolean onNotificationOpened(@NonNull Context context, @NonNull NotificationInfo notificationInfo, @NonNull ActionButtonInfo actionButtonInfo) {
        Log.i(TAG, "Notification action button opened. Button ID: " + actionButtonInfo.getButtonId() + ". NotificationId: " + notificationInfo.getNotificationId());

        // Return false here to allow Urban Airship to auto launch the launcher
        // activity for foreground notification action buttons

        return false;
    }

    @Override
    protected void onNotificationDismissed(@NonNull Context context, @NonNull NotificationInfo notificationInfo) {
        Log.i(TAG, "Notification dismissed. Alert: " + notificationInfo.getMessage().getAlert() + ". Notification ID: " + notificationInfo.getNotificationId());
    }
}
