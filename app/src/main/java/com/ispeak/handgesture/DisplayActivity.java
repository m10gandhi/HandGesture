package com.ispeak.handgesture;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class DisplayActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final int choice;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        int position = getIntent().getExtras().getInt("Image Int");
        choice = getIntent().getExtras().getInt("Choice");
        ImageView imagev = (ImageView) findViewById(R.id.imagedisplay);

        /* Compute index based on choice(Alphabets/Numbers/Frequently Used words) */
        if (choice == 2)
            position = position + 26;
        else if (choice == 3)
            position = position + 46;

        /* Array which contains id for Alphabets, Numbers and Frequently Used words stored in drawable */
        int[] bLogos = {
                R.drawable.frame_001,
                R.drawable.frame_002,
                R.drawable.frame_003,
                R.drawable.frame_004,
                R.drawable.frame_005,
                R.drawable.frame_006,
                R.drawable.frame_007,
                R.drawable.frame_008,
                R.drawable.frame_009,
                R.drawable.frame_010,
                R.drawable.frame_011,
                R.drawable.frame_012,
                R.drawable.frame_013,
                R.drawable.frame_014,
                R.drawable.frame_015,
                R.drawable.frame_016,
                R.drawable.frame_017,
                R.drawable.frame_018,
                R.drawable.frame_019,
                R.drawable.frame_020,
                R.drawable.frame_021,
                R.drawable.frame_022,
                R.drawable.frame_023,
                R.drawable.frame_024,
                R.drawable.frame_025,
                R.drawable.frame_026,
                R.drawable.frame_027,
                R.drawable.frame_028,
                R.drawable.frame_029,
                R.drawable.frame_030,
                R.drawable.frame_031,
                R.drawable.frame_032,
                R.drawable.frame_033,
                R.drawable.frame_034,
                R.drawable.frame_035,
                R.drawable.frame_036,
                R.drawable.frame_037,
                R.drawable.frame_038,
                R.drawable.frame_039,
                R.drawable.frame_040,
                R.drawable.frame_041,
                R.drawable.frame_042,
                R.drawable.frame_043,
                R.drawable.frame_044,
                R.drawable.frame_045,
                R.drawable.frame_046,
                R.drawable.frame_047,
                R.drawable.frame_048,
                R.drawable.frame_049,
                R.drawable.frame_050,
                R.drawable.frame_051,
                R.drawable.frame_052
        };
        int cImage = bLogos[position];
        imagev.setImageResource(cImage);
    }
}

