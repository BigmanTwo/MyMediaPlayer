package com.example.asus.mymediaplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class VedioActivity extends AppCompatActivity implements View.OnClickListener,MediaPlayer.OnPreparedListener{
      private SurfaceView surfaceView;
     private Button button;
    private MediaPlayer myPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        button=(Button)findViewById(R.id.play);
        surfaceView=(SurfaceView)findViewById(R.id.vedio_view);
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
        myPlay.setDisplay(surfaceView.getHolder());
        myPlay.prepareAsync();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
