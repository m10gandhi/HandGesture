package com.ispeak.handgesture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class StarterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        ImageButton gestures = (ImageButton) findViewById(R.id.gestures);
        gestures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gestureIntent = new Intent(StarterActivity.this, MainActivity.class);
                startActivity(gestureIntent);
            }
        });

        ImageButton cliparts = (ImageButton) findViewById(R.id.cliparts);
        cliparts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clipartsIntent = new Intent(StarterActivity.this, GestureSearch.class);
                startActivity(clipartsIntent);
            }
        });

        ImageButton about = (ImageButton) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(StarterActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
            }
        });
    }
}