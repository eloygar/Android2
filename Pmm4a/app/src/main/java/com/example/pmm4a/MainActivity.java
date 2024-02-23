package com.example.pmm4a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    static final String TELEFONO = " 34986419899";
    static final String MENSAJE = "Baka Mitae";
    Button foto ,audio,sms;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foto = findViewById(R.id.foto);
        audio = findViewById(R.id.audio);
        sms = findViewById(R.id.sms);
        imageView = findViewById(R.id.imageView);
        foto.setOnClickListener(v -> sacarFoto());
        audio.setOnClickListener(v -> {
            MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.adrenalina);
            mediaPlayer.start();
        });
        sms.setOnClickListener(v -> enviarSMS());

    }

    private void enviarSMS() {

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(TELEFONO, null, MENSAJE, null, null);
            Toast.makeText(getApplicationContext(), "SMS enviado.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "error eviando SMS", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    private void sacarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }

    }
   /*  // onClick botón obtener thumbnail
    public void hacerFotoThumbnail (View view){
        Intent hacerFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (hacerFotoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(hacerFotoIntent, CAPTURA_IMAGEN_THUMBNAIL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURA_IMAGEN_THUMBNAIL && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
        else if (requestCode == CAPTURA_IMAGEN_TAMAÑO_REAL && resultCode == RESULT_OK) {
            Toast toast = Toast.makeText(this, "La imagen se ha guardado en: " + fotoPath , Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            Toast toast = Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
*/
}