package com.example.asus.mymediaplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnBufferingUpdateListener{
    private Button playButton,stopButton,playVedio;
    private SeekBar mSeekBar;
    private MediaPlayer mediaPlayer;
    private boolean b=false;
    private String uris="http://abv.cn/music/光辉岁月.mp3";
    private int conut=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton=(Button)findViewById(R.id.start_but);
        stopButton=(Button)findViewById(R.id.stop_but);
        playVedio=(Button)findViewById(R.id.play_vido);
        playVedio.setOnClickListener(this);
        mSeekBar=(SeekBar)findViewById(R.id.seek_bar);
        playButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        //第一种启动方式
        mediaPlayer=new MediaPlayer();
        //设置流媒体
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        //第二种实例化
//        mediaPlayer=MediaPlayer.create(this,R.raw.moon);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                  b=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                b=false;

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);
                mp.setLooping(true);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.start_but:
                if(conut==1) {
                    getMedia();
                    conut++;
                }
                if ( mediaPlayer!=null) {
                mSeekBar.setMax(mediaPlayer.getDuration());
                //定时器
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (b == true) {
                            return;
                        }
                        mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                };
                timer.schedule(timerTask, 0, 10);
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();

                    playButton.setText("播放");
                } else {
                    mediaPlayer.start();
                    playButton.setText("暂停");
                }
            }
                break;
            case R.id.stop_but:
                finish();
                break;
            case R.id.play_vido:
                Intent intent=new Intent(MainActivity.this,VedioActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getMedia() {
        try {
            mediaPlayer.setDataSource(this, Uri.parse(uris));
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }
}
