package com.example.user.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private TextView tvElapsedTime;
    private TextView tvDuration;
    private SeekBar seekBar;
    private long duration;
    private long elaspedTime;
    private Handler handler;

    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            elaspedTime = mediaPlayer.getCurrentPosition();

            seekBar.setProgress((int) elaspedTime);

            long minute = TimeUnit.MILLISECONDS.toMinutes(elaspedTime); // 2
            long second = TimeUnit.MILLISECONDS.toSeconds(elaspedTime) -
                    TimeUnit.MILLISECONDS.toSeconds(minute);

            tvElapsedTime.setText(String.format("%d Min %d Sec", minute, second));
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();

        tvElapsedTime = findViewById(R.id.tv_elapsedTime);
        tvDuration = findViewById(R.id.tv_duration);
        seekBar = findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.airtel);
        duration = mediaPlayer.getDuration();

        seekBar.setMax((int) duration);

        long minute = TimeUnit.MILLISECONDS.toMinutes(duration); // 2
        long second = TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MILLISECONDS.toSeconds(minute);

        tvDuration.setText(String.format("%d Min %d Sec", minute, second));

        elaspedTime = 0;
    }

    public void play(View view) {
        mediaPlayer.start();

        handler.postDelayed(updateTime, 1000);
    }

    public void backward(View view) {

    }

    public void pause(View view) {
        mediaPlayer.pause();
    }

    public void forward(View view) {

    }
}
