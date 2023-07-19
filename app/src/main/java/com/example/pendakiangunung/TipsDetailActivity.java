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

public class TipsDetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    int tipsLocationIndex;
    ImageView tipsImage;
    TextView tipsTitle;
    TextView tipsDescription;
    JSONObject tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_detail);

        toolbar = findViewById(R.id.toolbar);
        tipsImage = findViewById(R.id.tipsImage);
        tipsTitle = findViewById(R.id.tipsTitle);
        tipsDescription = findViewById(R.id.tipsDescription);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent mainIntent = getIntent();
        tipsLocationIndex = mainIntent.getIntExtra("tipsLocationIndex", 0);

        try {
            InputStream inputStream = getAssets().open("tips.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            tips = new JSONArray(json).getJSONObject(tipsLocationIndex);

            getSupportActionBar().setTitle(tips.getString("judul"));

            tipsTitle.setText(tips.getString("judul"));
            tipsImage.setImageResource(getResources().getIdentifier(tips.getString("gambar"), "drawable", getPackageName()));
            tipsDescription.setText(tips.getString("deskripsi"));

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