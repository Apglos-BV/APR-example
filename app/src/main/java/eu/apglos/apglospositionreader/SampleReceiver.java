package eu.apglos.apglospositionreader;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class SampleReceiver extends Service {
    private static final String TAG = "PositionUpdateService";
    private static final String CHANNEL_ID = "PositionUpdateChannel";

    private BroadcastReceiver positionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("NESTLE on2go.POSITION_UPDATE".equals(intent.getAction())) {
                Location location = intent.getParcelableExtra("location");
                if (location != null) {
                    Log.d("aaaj", "Foreground Service received location: " +
                            location.getLatitude() + ", " + location.getLongitude());
                    MainActivity.LBL_location.setText(location.getLatitude() + ", " + location.getLongitude() + "," + location.getAltitude());
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(1, getNotification());

        IntentFilter filter = new IntentFilter("NESTLE on2go.POSITION_UPDATE");
        registerReceiver(positionReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(positionReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Position Update Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }

    private Notification getNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Receiving Location Updates")
                .setContentText("Listening for POSITION_UPDATE broadcasts")
                .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                .build();
    }
}