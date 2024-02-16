package com.example.a76mediacontroler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.os.Bundle;

import java.io.IOException;

public class MainActivity extends AppCompatActivity  implements MediaController.MediaPlayerControl {

    private MediaController mediaController;
    private MediaPlayer mediaPlayer;
    private Handler handler;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtener instancias
        mediaPlayer = new MediaPlayer();
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.constraintLayout));
        handler = new Handler();

        //carga musicA
        try {
            mediaPlayer.setDataSource(this,
                    Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.metodo_para_escapar));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //muestra los controles y arranca
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                handler.post(new Runnable() {
                    public void run() {
                        // Se muestra el control en la pantalla.
                        // Tras 20 segundos de inactividad, el control se ocultará
                        mediaController.show(20000);
                        mediaPlayer.start();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    // El método onTouchEvent nos permite controlar qué hacer cuando el usuario toca la pantalla
    public boolean onTouchEvent(MotionEvent event) {
        // En este caso, cuando el usuario toque la pantalla,
        // mostramos los controles de reproducción
        mediaController.show();
        return false;
    }


    //CICLO DE VIDA DE MEDIA PLAYER
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }
}