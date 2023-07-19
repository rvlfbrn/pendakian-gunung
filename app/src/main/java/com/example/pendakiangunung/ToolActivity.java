package com.example.pendakiangunung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

public class ToolActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    JSONArray tools;
    String titles[];
    String subtitles[];
    int images[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.getMenu().getItem(1).setChecked(true);

        try {
            InputStream inputStream = getAssets().open("peralatan.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, "UTF-8");
            tools = new JSONArray(json);

            titles = new String[tools.length()];
            subtitles = new String[tools.length()];
            images = new int[tools.length()];

            for (int i = 0; i < tools.length(); i++) {
                titles[i] = tools.getJSONObject(i).getString("nama");
                subtitles[i] = tools.getJSONObject(i).getString("tipe");
                String imageTool = tools.getJSONObject(i).getString("image_url");
                images[i] = getResources().getIdentifier(imageTool, "drawable", getPackageName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        ListView tool = findViewById(R.id.toolListView);
        ToolListAdapter toolListAdapter = new ToolListAdapter(getApplicationContext(), titles, subtitles, images);
        tool.setAdapter(toolListAdapter);

        tool.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), ToolDetailActivity.class).putExtra("toolLocationIndex", position);
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
                    return true;
                } else if (itemId == R.id.navigation_tips) {
                    Intent intent = new Intent(getApplicationContext(), TipsActivity.class);
                    startActivity(intent);

                    return true;
                }

                return false;
            }
        });
    }
}