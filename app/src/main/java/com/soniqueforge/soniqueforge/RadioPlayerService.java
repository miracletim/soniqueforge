package com.soniqueforge.soniqueforge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.media.MediaPlayer;
import android.media.AudioAttributes;

import androidx.annotation.Nullable;
import androidx.media.app.NotificationCompat.MediaStyle;
import androidx.core.app.NotificationCompat;
import java.io.IOException;
public class RadioPlayerService extends Service {

    public static boolean isPlaying = false;

    public static enum RadioStatus {
        CONNECTING,
        PLAYING,
        STOPPED,
        UNAVAILABLE
    }
    public static RadioStatus currentStatus = RadioStatus.STOPPED;
    private static final String CHANNEL_ID = "RadioChannel";
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String streamUrl = intent.getStringExtra("stationLink");
        String stationName = intent.getStringExtra("stationName");
        String stationFreq = intent.getStringExtra("stationFreq");
        String stationLocation = intent.getStringExtra("stationLocation");

        if(streamUrl != null) {
            if(mediaPlayer != null) {
                mediaPlayer.reset();
            }
            else {
                mediaPlayer = new MediaPlayer();
            }
            try {
                currentStatus = RadioStatus.CONNECTING;
                broadcastStatus();
                mediaPlayer.setAudioAttributes(
                        new AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .build()
                );
                mediaPlayer.setDataSource(streamUrl);
                mediaPlayer.setOnPreparedListener(mp -> {
                    currentStatus = RadioStatus.PLAYING;
                    broadcastStatus();
                    mp.start();
                });

                mediaPlayer.setOnErrorListener((mp, what, extra) -> {

                    currentStatus = RadioStatus.UNAVAILABLE;
                    broadcastStatus();
                    return true;
                });
                mediaPlayer.prepareAsync();

                Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("Playing: " + stationName)
                        .setSmallIcon(R.drawable.radio)
                        .setStyle(new MediaStyle())
                        .setOngoing(true)
                        .build();
                startForeground(1, notification);

                isPlaying = true;

            } catch(IOException e){
                currentStatus = RadioStatus.UNAVAILABLE;
                broadcastStatus();
                e.printStackTrace();
            }
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isPlaying = false;
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        currentStatus = RadioStatus.STOPPED;
        broadcastStatus();
    }

    private void broadcastStatus() {
        Intent statusIntent = new Intent("RADIO_STATUS");
        statusIntent.putExtra("status", currentStatus.name());
        sendBroadcast(statusIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Radio Playback",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if(manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}
