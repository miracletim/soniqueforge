package com.soniqueforge.soniqueforge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;
import  android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerActivity extends AppCompatActivity{
    private TextView radioName, radioFreq, radioLocation, tvRadioStatus;
    private Button playStopBtn;
    private String stationName, stationFreq, stationLink, stationLocation;
    private void startService() {
        Intent serviceIntent = new Intent(this,
                RadioPlayerService.class);
        serviceIntent.putExtra("stationLink", stationLink);
        serviceIntent.putExtra("stationName", stationName);
        serviceIntent.putExtra("stationFreq", stationFreq);
        serviceIntent.putExtra("stationLocation", stationLocation);
        startService(serviceIntent);
        playStopBtn.setText("STOP");
    }
    private void stopService() {
        stopService(new Intent(this, RadioPlayerService.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_player);

        radioName = findViewById(R.id.player_tvRadioName);
        radioFreq = findViewById(R.id.player_tvRadioFrequency);
        radioLocation = findViewById(R.id.player_tvRadioLocation);
        playStopBtn = findViewById(R.id.player_btnPlayStop);
        tvRadioStatus = findViewById(R.id.player_tvStatus);

        stationName = getIntent().getStringExtra("stationName");
        stationFreq = getIntent().getStringExtra("stationFreq");
        stationLink = getIntent().getStringExtra("stationLink");
        stationLocation = getIntent().getStringExtra("stationLocation");

        radioName.setText(stationName);
        radioFreq.setText(stationFreq);
        radioLocation.setText(stationLocation);

        startService();

        playStopBtn.setOnClickListener(v -> {
            if (RadioPlayerService.isPlaying) {
                stopService();
                playStopBtn.setText("PLAY");
            } else {
                startService();
            }
        });

    }

    private BroadcastReceiver statusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra("status");
            tvRadioStatus.setText(status);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(statusReceiver, new IntentFilter("RADIO_STATUS"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(statusReceiver);
    }
}
