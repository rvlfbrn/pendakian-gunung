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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ToolDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView toolImage;
    TextView toolName;
    TextView toolDescription;
    TextView toolType;
    TextView toolTips;
    int toolLocationIndex;
    JSONObject tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_detail);

        toolbar = findViewById(R.id.toolbar);
        toolImage = findViewById(R.id.toolImage);
        toolName = findViewById(R.id.toolName);
        toolDescription = findViewById(R.id.toolDescription);
        toolType = findViewById(R.id.toolType);
        toolTips = findViewById(R.id.toolTips);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent mainIntent = getIntent();
        toolLocationIndex = mainIntent.getIntExtra("toolLocationIndex", 0);

        try {
            InputStream inputStream = getAssets().open("peralatan.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            tool = new JSONArray(json).getJSONObject(toolLocationIndex);

            getSupportActionBar().setTitle(tool.getString("nama"));

            toolName.setText(tool.getString("nama"));
            toolImage.setImageResource(getResources().getIdentifier(tool.getString("image_url"), "drawable", getPackageName()));
            toolDescription.setText(tool.getString("deskripsi"));
            toolType.setText(tool.getString("tipe"));
            toolTips.setText(tool.getString("tips"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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