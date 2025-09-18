package com.soniqueforge.soniqueforge;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SecondActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private RadioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_second);

        recyclerView = findViewById(R.id.radioRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Radio> stations = RadioRepository.getStations();

        adapter = new RadioAdapter(this, stations, (radio, position) -> {

            adapter.setCurrentlyPlaying(position);
            Intent intent = new Intent(SecondActivity.this, PlayerActivity.class);
            intent.putExtra("stationName", radio.getName());
            intent.putExtra("stationFreq", radio.getFrequency());
            intent.putExtra("stationLink", radio.getStreamLink());
            intent.putExtra("stationLocation", radio.getLocation());

            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}
