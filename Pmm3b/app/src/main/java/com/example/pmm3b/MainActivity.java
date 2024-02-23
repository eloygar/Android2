package com.example.pmm3b;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private final String FILENAME = "Documents/macarrones.txt";
    private final String[] PERMISSIONS_STORAGE = {
            //lool
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    TextView txtView;
    Button btnCargar, btnGuardar;
    String texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = findViewById(R.id.textView);
        btnCargar = findViewById(R.id.btnCargar);
        btnGuardar = findViewById(R.id.btnGuardar);

        btnCargar.setOnClickListener(v -> {
            try {
                txtView.setText(getText(this.getAssets().open("Estoyloco.txt")));
                //  image.setImageBitmap(BitmapFactory.decodeStream(this.getAssets().open("ditto.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btnGuardar.setOnClickListener(v -> writeFile(v));
    }


    private String getText(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            ;
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String newLine = null;
                while ((newLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(newLine + "\n");
                }
                inputStream.close();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    /* public boolean isExternalStorageReadable() {
         if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                 Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
             return true;
         }
         return false;
     }
 */
    public void checkStoragePermission() {
        int permission = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 101);
        }
    }

    public void writeFile(View view) {
        if (isExternalStorageWritable()) {
            checkStoragePermission();
            try {
                File textFile = new File(Environment.getExternalStorageDirectory(), FILENAME);
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                fileOutputStream.write(txtView.getText().toString().getBytes());
                fileOutputStream.close();
                Toast.makeText(this, "Guardado", Toast.LENGTH_LONG).show();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error writing file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Cannot write to External Storage", Toast.LENGTH_LONG).show();
        }
    }
}