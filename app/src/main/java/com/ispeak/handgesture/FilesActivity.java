package com.ispeak.handgesture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FilesActivity extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        TextView btnalpha = (TextView) findViewById(R.id.alphabets);
        TextView btnnum = (TextView) findViewById(R.id.numbers);
        btnalpha.setOnClickListener(this);
        btnnum.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(FilesActivity.this, ItemsActivity.class);
        switch (v.getId()) {
            case R.id.alphabets:
                intent.putExtra("Userchoice", 1);
                startActivity(intent);
                break;

            case R.id.numbers:
                intent.putExtra("Userchoice", 2);
                startActivity(intent);
                break;
        }
    }
}