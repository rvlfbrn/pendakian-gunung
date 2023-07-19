package com.example.pendakiangunung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MountainActivity extends AppCompatActivity {

    String titles[];
    int images[];
    JSONArray mountains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain);

        Intent mainIntent = getIntent();
        String mountainLocation = mainIntent.getStringExtra("location");

        Map<String, Integer> mountainLocationIndex = new HashMap<>();
        mountainLocationIndex.put("Jawa Timur", 0);
        mountainLocationIndex.put("Jawa Tengah", 1);
        mountainLocationIndex.put("Jawa Barat", 2);
        mountainLocationIndex.put("Luar Pulau Jawa", 3);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mountainLocation);

        try {
            InputStream inputStream = getAssets().open("nama_gunung.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");
            mountains = new JSONArray(json).getJSONObject(mountainLocationIndex.get(mountainLocation)).getJSONArray("nama_gunung");

            titles = new String[mountains.length()];
            images = new int[mountains.length()];

            for (int i = 0; i < mountains.length(); i++) {
                titles[i] = mountains.getJSONObject(i).getString("nama");
                String imageGunung = mountains.getJSONObject(i).getString("image_gunung");
                images[i] = getResources().getIdentifier(imageGunung, "drawable", getPackageName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        GridView mountain = findViewById(R.id.mountainGrid);
        MountainListAdapter mountainListAdapter = new MountainListAdapter(getApplicationContext(), titles, images);
        mountain.setAdapter(mountainListAdapter);

        mountain.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), MountainDetailActivity.class).putExtra("mountainLocationIndex", mountainLocationIndex.get(mountainLocation)).putExtra("mountainLocationPosition", position);
            startActivity(intent);
        });
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