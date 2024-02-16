package com.example.a75mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.adrenalina);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                TextView t = (TextView) findViewById(R.id.textView);
                mediaPlayer.release();
                t.setText("La reproducción ha terminado, acabo de hacer un release()");

            }
        });
    }

    public void play(View view) {
        TextView t = (TextView) findViewById(R.id.textView);
        if (mediaPlayer.isPlaying()) {
            t.setText("Ya estás escuchando música, ¿qué más quieres chaval?");
        } else {
            mediaPlayer.start();
            t.setText("Tu MP está parado, tranqui que le hago un start()");
        }
    }

    public void stop(View view) throws IOException {
        TextView t = (TextView) findViewById(R.id.textView);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();

            try {
                mediaPlayer.prepare();
                t.setText("La música estaba sonando pero acabas de hacer un stop() y un prepare() a tu MP");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            t.setText("La música no suena, el MP está parado, ¿por qué haces un stop()?");
        }
    }

    public void pause(View view) {
        TextView t = (TextView) findViewById(R.id.textView);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            t.setText("Acabas de pausar tu MP");
        } else {
            t.setText("Tu MP no está en ejecución, luego no lo puedes pausar");
        }
    }
}