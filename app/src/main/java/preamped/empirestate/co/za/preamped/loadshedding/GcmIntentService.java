package preamped.empirestate.co.za.preamped.loadshedding;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import preamped.empirestate.co.za.preamped.R;

/**
 * Created by George Kapoya on 2015-05-06.
 */
public class GcmIntentService extends IntentService{
    private static final String SENDER_ID = "970358825737" ;
    public int notification_id = 10;
    String location,name;
    String message = "jfjfj";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;


    public GcmIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        Toast.makeText( getApplicationContext(),"push came in",Toast.LENGTH_LONG).show();
        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {

            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {

                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                 message =   extras.getString("type");
                if(message != null ) {
                    if (message.equalsIgnoreCase("outage")) {
                        location = extras.getString("markerName");
                        name = extras.getString("userName");
                        sendNotification("Hi " + name + " outages have been reported at " + location);
                    } else if (message.equalsIgnoreCase("stageUpdate")) {
                        message = extras.getString("message");
                        sendNotification(message);
                    }
                }
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }


    // Put the message into a notification and post it.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(getApplicationContext(), PreampedPager.class);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.app_acc);


        mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.pre_white)
                        .setLargeIcon(largeIcon)
                        .setContentTitle("Preamped")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setLights(Color.GREEN, 3000, 3000);


        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(notification_id, mBuilder.build());
    }
}
