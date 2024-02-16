package com.example.a76videoview;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.VideoView;


public class MainActivity extends Activity {

    VideoView videoView;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtenemos la referencia al widget VideoView
        videoView = (VideoView) findViewById(R.id.videoView);
        // Creamos el objeto MediaController
        mediaController = new MediaController(this);
        // Establecemos el ancho del MediaController
        mediaController.setAnchorView(videoView);
        // Al contenedor VideoView le añadimos los controles
        videoView.setMediaController(mediaController);
        // Cargamos el contenido multimedia (el vídeo) en el VideoView
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.magia));

        // Registramos el callback que será invocado cuando el vídeo esté cargado y
        // preparado para la reproducción
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaController.show(10000);
                videoView.start();
            }
        });
    }

    // Programamos el método onTouchEvent(), para que se muestre el MediaControl
    // cuando el usuario pulse en la pantalla
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mediaController.show();
        return false;
    }


}
