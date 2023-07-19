package com.example.pendakiangunung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MountainSearchActivity extends AppCompatActivity {
    Map<String, Integer> mountainLocationIndex;
    List<JSONObject> mountainObjects;
    Toolbar toolbar;
    SearchView searchMountain;
    GridView mountain;
    MountainListAdapter mountainListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mountain_search);

        mountainLocationIndex = new HashMap<>();
        mountainLocationIndex.put("Jawa Timur", 0);
        mountainLocationIndex.put("Jawa Tengah", 1);
        mountainLocationIndex.put("Jawa Barat", 2);
        mountainLocationIndex.put("Luar Pulau Jawa", 3);

        mountainObjects = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        searchMountain = findViewById(R.id.searchMountain);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        try {
            InputStream inputStream = getAssets().open("nama_gunung.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");
            JSONArray mountains = new JSONArray(json);

            for (int i = 0; i < 4; i++) {
                JSONArray mountainArrays = mountains.getJSONObject(i).getJSONArray("nama_gunung");
                for (int j = 0; j < mountainArrays.length(); j++) {
                    mountainObjects.add(mountainArrays.getJSONObject(j).put("location", i).put("index", j));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        searchMountain.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<JSONObject> filteredMountainObjects = new ArrayList<>();

                for (int i = 0; i < mountainObjects.size(); i++) {
                    try {
                        String name = mountainObjects.get(i).getString("nama");

                        if (name.toLowerCase().contains(newText.toLowerCase())) {
                            filteredMountainObjects.add(mountainObjects.get(i));
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                String[] titles = new String[filteredMountainObjects.size()];
                int[] images = new int[filteredMountainObjects.size()];

                for (int i = 0; i < filteredMountainObjects.size(); i++) {
                    try {
                        titles[i] = filteredMountainObjects.get(i).getString("nama");
                        String imageGunung = filteredMountainObjects.get(i).getString("image_gunung");
                        images[i] = getResources().getIdentifier(imageGunung, "drawable", getPackageName());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

                mountain = findViewById(R.id.mountainGrid);
                mountainListAdapter = new MountainListAdapter(getApplicationContext(), titles, images);
                mountain.setAdapter(mountainListAdapter);

                mountain.setOnItemClickListener((parent, view, position, id) -> {
                    Intent intent = null;
                    try {
                        intent = new Intent(getApplicationContext(), MountainDetailActivity.class).putExtra("mountainLocationIndex", filteredMountainObjects.get(position).getInt("location")).putExtra("mountainLocationPosition", filteredMountainObjects.get(position).getInt("index"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    startActivity(intent);
                });

                return false;
            }
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