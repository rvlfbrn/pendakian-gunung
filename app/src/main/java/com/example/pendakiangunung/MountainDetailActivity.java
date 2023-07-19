package com.example.pendakiangunung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class MountainDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    double latitude;
    double longitude;
    MapView mapView;
    IMapController mapController;
    ImageView mountainImage;
    TextView mountainName;
    TextView mountainLocation;
    TextView mountainDescription;
    TextView mountainTrack;
    TextView mountainInfo;
    int mountainLocationIndex;
    int mountainLocationPosition;
    JSONObject mountain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain_detail);

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        toolbar = findViewById(R.id.toolbar);
        mountainImage = findViewById(R.id.mountainImage);
        mountainName = findViewById(R.id.mountainName);
        mountainLocation = findViewById(R.id.mountainLocationName);
        mountainDescription = findViewById(R.id.mountainDescription);
        mountainTrack = findViewById(R.id.mountainTrack);
        mountainInfo = findViewById(R.id.mountainInfo);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent mainIntent = getIntent();
        mountainLocationIndex = mainIntent.getIntExtra("mountainLocationIndex", 0);
        mountainLocationPosition = mainIntent.getIntExtra("mountainLocationPosition", 0);

        try {
            InputStream inputStream = getAssets().open("nama_gunung.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            mountain = new JSONArray(json).getJSONObject(mountainLocationIndex).getJSONArray("nama_gunung").getJSONObject(mountainLocationPosition);

            getSupportActionBar().setTitle(mountain.getString("nama"));
            mountainImage.setImageResource(getResources().getIdentifier(mountain.getString("image_gunung"), "drawable", getPackageName()));
            mountainName.setText(mountain.getString("nama"));
            mountainLocation.setText(mountain.getString("lokasi"));
            mountainDescription.setText(mountain.getString("deskripsi"));
            mountainTrack.setText(mountain.getString("jalur_pendakian"));
            mountainInfo.setText(mountain.getString("info_gunung"));

            latitude = mountain.getDouble("lat");
            longitude = mountain.getDouble("lon");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        mapController = mapView.getController();
        mapController.setCenter(new GeoPoint(latitude, longitude));
        mapController.setZoom(10);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }
}