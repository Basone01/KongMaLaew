package com.advpro2.basone.kongmalaew;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.Autopilot;
import com.urbanairship.UAirship;

/**
 * Created by bason on 04-Nov-17.
 */

public class AppAutopilot extends Autopilot {
    private static final String NO_BACKUP_PREFERENCES = "com.urbanairship.sample.no_backup";
    public static final String CHANNEL_ID="com.advpro2.basone.kongmalaew";
    private static final String FIRST_RUN_KEY = "first_run";
    static String chId;
    @Override
    public void onAirshipReady(UAirship airship) {


        SharedPreferences preferences = UAirship.getApplicationContext().getSharedPreferences(NO_BACKUP_PREFERENCES, Context.MODE_PRIVATE);

        boolean isFirstRun = preferences.getBoolean(FIRST_RUN_KEY, true);
        if (isFirstRun) {
            preferences.edit().putBoolean(FIRST_RUN_KEY, false).apply();

            // Enable user notifications on first run
            airship.getPushManager().setUserNotificationsEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            Context context = UAirship.getApplicationContext();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Log.d("APPPPPPPPPPPPPPPPPP", "onAirshipReady: "+AppAutopilot.chId);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID+"",
                    AppAutopilot.chId+"",
                    NotificationManager.IMPORTANCE_DEFAULT+0);

            notificationManager.createNotificationChannel(channel);
        }
//         Create a customized default notification factory
        CustomNotificationFactory notificationFactory;
        notificationFactory = new CustomNotificationFactory(UAirship.getApplicationContext());

//         Set the factory on the PushManager
        airship.getPushManager().setNotificationFactory(notificationFactory);
    }

    @Nullable
    @Override
    public AirshipConfigOptions createAirshipConfigOptions(@NonNull Context context) {

         /*
          Optionally, customize your config at runtime:
             AirshipConfigOptions options = new AirshipConfigOptions.Builder()
                    .setInProduction(!BuildConfig.DEBUG)
                    .setDevelopmentAppKey("Your Development App Key")
                    .setDevelopmentAppSecret("Your Development App Secret")
                    .setProductionAppKey("Your Production App Key")
                    .setProductionAppSecret("Your Production App Secret")
                    .setGcmSender("Your GCM/Firebase Sender ID")
                    .setNotificationAccentColor(ContextCompat.getColor(context, R.color.color_accent))
                    .setNotificationIcon(R.drawable.ic_notification)
                    .build();
            return options;
         */

        // defaults to loading config from airshipconfig.properties file
        return super.createAirshipConfigOptions(context);
    }
}