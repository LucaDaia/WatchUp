package com.example.watchup;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class DataFetchService extends Service implements FetchDataCallback {
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "DataFetchChannel";

    private BackendFetcher fetcher;
    private Handler handler;
    private Runnable fetchRunnable;
    private List<Image> currentImageList = new ArrayList<>();
    private int counterOfRefreshes = 0;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        fetcher = new BackendFetcher();
        handler = new Handler();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel(); // Create the notification channel
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForegroundService();
        System.out.println("a inceput foreground service ul fmmm");
        handler.post(fetchRunnable = this::fetchData); // Fetch data immediately
        return START_STICKY;
    }

    private void startForegroundService() {
        Notification notification = createInitialNotification();
        startForeground(NOTIFICATION_ID, notification);
    }

    private Notification createNotification(Image image) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("New Image")
                .setContentText("A new image is available")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Create an intent to launch an activity when the notification is clicked
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        return builder.build();
    }

    private Notification createInitialNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentTitle("Data Fetch Service")
                .setContentText("Fetching data...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);

        return builder.build();
    }

    private void createNotificationChannel() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    CHANNEL_ID,
//                    "Data Fetch Channel",
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if(notificationChannel == null) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(CHANNEL_ID, "un nume", importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
    }

    private void fetchData() {
        fetcher.fetchData(this);
        handler.postDelayed(fetchRunnable, /*10 * 60 **/10000); // Fetch data every 10 minutes
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(fetchRunnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSuccess(List<Image> imageList) {
        if (currentImageList.size() != imageList.size() && counterOfRefreshes > 0) {
            currentImageList = imageList;
            Utils.sortObjectsByDate(currentImageList);
            Image latestImage = currentImageList.get(0);
            Notification newNotification = createNotification(latestImage);
//            NotificationManager manager = getSystemService(NotificationManager.class);
            notificationManager.notify(NOTIFICATION_ID, newNotification);
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        } else {
            currentImageList = imageList;
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
        counterOfRefreshes++;
    }

    @Override
    public void onFailure(String errorMessage) {
        Log.e("DataFetchService", "Failed to fetch images: " + errorMessage);
    }
}
