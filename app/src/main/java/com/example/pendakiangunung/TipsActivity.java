package com.example.pendakiangunung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class TipsActivity extends AppCompatActivity {
    JSONArray tools;
    String titles[];
    int images[];
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        try {
            InputStream inputStream = getAssets().open("tips.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");
            tools = new JSONArray(json);

            titles = new String[tools.length()];
            images = new int[tools.length()];

            for (int i = 0; i < tools.length(); i++) {
                titles[i] = tools.getJSONObject(i).getString("judul");
                String imageTips = tools.getJSONObject(i).getString("gambar");
                images[i] = getResources().getIdentifier(imageTips, "drawable", getPackageName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        ListView tips = findViewById(R.id.tipsListView);
        TipsListAdapter tipsListAdapter = new TipsListAdapter(getApplicationContext(), titles, images);
        tips.setAdapter(tipsListAdapter);

        tips.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), TipsDetailActivity.class).putExtra("tipsLocationIndex", position);
            startActivity(intent);
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    return true;
                } else if (itemId == R.id.navigation_tool) {
                    Intent intent = new Intent(getApplicationContext(), ToolActivity.class);
                    startActivity(intent);

                    return true;
                } else if (itemId == R.id.navigation_tips) {
                    return true;
                }

                return false;
            }
        });
    }
}