package com.example.user.myapplication;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonForward;
    private Button buttonBackward;

    private MediaPlayer mediaPlayer;
    private TextView tvElapsedTime;
    private TextView tvDuration;
    private SeekBar seekBar;
    private long duration;
    private long elaspedTime;
    private Handler handler;

    private final int time = 5000;

    private Runnable updateTime = new Runnable() {
        @Override
        public void run() {
            elaspedTime = mediaPlayer.getCurrentPosition();

            seekBar.setProgress((int) elaspedTime);

            long minute = TimeUnit.MILLISECONDS.toMinutes(elaspedTime); // 2
            long second = TimeUnit.MILLISECONDS.toSeconds(elaspedTime) -
                    TimeUnit.MINUTES.toSeconds(minute);

            tvElapsedTime.setText(String.format("%d Min %d Sec", minute, second));
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();

        buttonPlay = findViewById(R.id.btn_play);
        buttonPause = findViewById(R.id.btn_pause);
        buttonForward = findViewById(R.id.btn_forward);
        buttonBackward = findViewById(R.id.btn_backward);

        buttonPause.setClickable(false);
        buttonForward.setClickable(false);
        buttonBackward.setClickable(false);

        tvElapsedTime = findViewById(R.id.tv_elapsedTime);
        tvDuration = findViewById(R.id.tv_duration);
        seekBar = findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.airtel);
        duration = mediaPlayer.getDuration();

        seekBar.setMax((int) duration);

        long minute = TimeUnit.MILLISECONDS.toMinutes(duration); // 2
        long second = TimeUnit.MILLISECONDS.toSeconds(duration) -
                TimeUnit.MINUTES.toSeconds(minute);

        tvDuration.setText(String.format("%d Min %d Sec", minute, second));

        elaspedTime = 0;
    }

    public void play(View view) {
        mediaPlayer.start();

        handler.postDelayed(updateTime, 1000);

        buttonPlay.setClickable(false);

        buttonPause.setClickable(true);
        buttonBackward.setClickable(true);
        buttonForward.setClickable(true);
    }

    public void backward(View view) {
        int position = mediaPlayer.getCurrentPosition();

        if ((position - time) > 0) {
            mediaPlayer.seekTo(position - time);
        } else {
            Toast.makeText(this, "You can't go backward", Toast.LENGTH_SHORT).show();
        }
    }

    public void pause(View view) {
        mediaPlayer.pause();

        buttonPlay.setClickable(true);
        buttonPause.setClickable(false);
        buttonBackward.setClickable(false);
        buttonForward.setClickable(false);
    }

    public void forward(View view) {

        int position = mediaPlayer.getCurrentPosition();

        if ((position + time) < duration) {
            mediaPlayer.seekTo(position + time);
        } else {
            Toast.makeText(this, "You can't go forward", Toast.LENGTH_SHORT).show();
        }

    }
}
