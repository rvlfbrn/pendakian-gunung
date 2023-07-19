package com.example.pendakiangunung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    LinearLayout searchMountain;
    LinearLayout eastJava;
    LinearLayout centralJava;
    LinearLayout westJava;
    LinearLayout outsideJava;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchMountain = findViewById(R.id.searchMountain);
        eastJava = findViewById(R.id.eastJava);
        centralJava = findViewById(R.id.centralJava);
        westJava = findViewById(R.id.westJava);
        outsideJava = findViewById(R.id.outsideJava);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.getMenu().getItem(0).setChecked(true);

        searchMountain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MountainSearchActivity.class);
                startActivity(intent);
            }
        });

        eastJava.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MountainActivity.class).putExtra("location", "Jawa Timur");
            startActivity(intent);
        });

        centralJava.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MountainActivity.class).putExtra("location", "Jawa Tengah");
            startActivity(intent);
        });

        westJava.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MountainActivity.class).putExtra("location", "Jawa Barat");
            startActivity(intent);
        });

        outsideJava.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MountainActivity.class).putExtra("location", "Luar Pulau Jawa");
            startActivity(intent);
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    return true;
                } else if (itemId == R.id.navigation_tool) {
                    Intent intent = new Intent(getApplicationContext(), ToolActivity.class);
                    startActivity(intent);

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