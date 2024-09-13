package com.example.mazilalpari;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VideoView videoView = findViewById(R.id.vv_Login_logo);

        // Load the video from the raw folder
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.logologin);
        videoView.setVideoURI(videoUri);

        // Start playing the video
        videoView.start();

        // Set a listener to know when the video ends
        videoView.setOnCompletionListener(mediaPlayer -> {
            // Go to the next activity (MainActivity) after the video ends
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, SelectLoginOptionActivity.class);
                    startActivity(i);
                }
            }, 3000);
        });

    }
}