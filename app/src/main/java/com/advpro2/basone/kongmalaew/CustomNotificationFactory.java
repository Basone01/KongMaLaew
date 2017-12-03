package com.advpro2.basone.kongmalaew;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.urbanairship.push.PushMessage;
import com.urbanairship.push.notifications.ActionsNotificationExtender;
import com.urbanairship.push.notifications.NotificationFactory;
import com.urbanairship.util.UAStringUtil;

/**
 * Created by bason on 06-Nov-17.
 */

public class CustomNotificationFactory extends NotificationFactory {

    public CustomNotificationFactory(Context context) {
        super(context);
    }

    @Override
    public Notification createNotification(PushMessage message, int notificationId) {
        // do not display a notification if there is not an alert
        if (UAStringUtil.isEmpty(message.getAlert())) {
            return null;
        }

        // Build the notification
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(getContext(),AppAutopilot.chId)
                .setContentTitle("KongMaLaew")
                .setContentText(message.getAlert())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(),R.drawable.anonymous_mask1600))
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        Log.d("lll", "createNotification: "+9999);

        // Notification action buttons
        builder.extend(new ActionsNotificationExtender(getContext(), message, notificationId));

        return builder.build();
    }

//    @Override
//    public int getNextId(PushMessage pushMessage) {
////        return NotificationIDGenerator.nextID();
//        return 1;
//    }

    /**
     * Checks if the push message requires a long running task. If {@code true}, the push message
     * will be scheduled to process at a later time when the app has more background time. If {@code false},
     * the app has approximately 10 seconds total for {@link #createNotification(PushMessage, int)}
     * and {@link #getNextId(PushMessage)}.
     * <p>
     * Apps that return {@code false} are highly encouraged to add {@code RECEIVE_BOOT_COMPLETED} so
     * the push message will persist between device reboots.
     *
     * @param message The push message.
     * @return {@code true} to require long running task, otherwise {@code false}.
     */
    @Override
    public boolean requiresLongRunningTask(PushMessage message) {
        return false;
    }

}