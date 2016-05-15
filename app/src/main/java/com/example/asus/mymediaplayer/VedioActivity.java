package com.example.asus.mymediaplayer;

import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VedioActivity extends AppCompatActivity implements View.OnClickListener,MediaPlayer.OnPreparedListener{
      private SurfaceView surfaceView;
     private Button button;
    private MediaPlayer myPlay;
    private SeekBar seekBar;
    private boolean b=false;
    private int count=1;
    private int progrec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        button=(Button)findViewById(R.id.play);
        surfaceView=(SurfaceView)findViewById(R.id.vedio_view);
        seekBar=(SeekBar)findViewById(R.id.vedio_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                b=true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myPlay.seekTo(seekBar.getProgress());
                b=false;
            }
        });
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.setKeepScreenOn(true);//设置屏幕高亮
        button.setOnClickListener(this);
        myPlay=new MediaPlayer();
        myPlay.setOnPreparedListener(this);
        try {
            myPlay.setDataSource("http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (count==1) {
                    myPlay.setDisplay(surfaceView.getHolder());
                    myPlay.prepareAsync();
                    count++;
                }
            if (myPlay != null) {
                seekBar.setMax(myPlay.getDuration());
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (b == true) {
                            return;
                        }
                        progrec=myPlay.getCurrentPosition();
                        seekBar.setProgress(myPlay.getCurrentPosition());
                    }
                };
                timer.schedule(timerTask, 0, 10);
                if (myPlay.isPlaying()) {
                    myPlay.pause();
                    button.setText("播放");
                    seekBar.setVisibility(View.VISIBLE);
                } else {
                    myPlay.start();

                    seekBar.setVisibility(View.GONE);
                    button.setText("暂停");
                }
            }
                break;
        }

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    protected void onDestroy() {
        b=true;
        myPlay.release();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            b=true;
            seekBar.setProgress(progrec);
            Log.d("横屛","success");
        }else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            b=true;
            seekBar.setProgress(progrec);
            Log.d("竖屛","success");
        }
    }
}
