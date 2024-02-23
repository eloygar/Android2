package com.example.pmm3a;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private final String FILENAME = "Documents/testfile.txt";
    private final String[] PERMISSIONS_STORAGE = {

            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    TextView textViewRaw;
    Button button;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewRaw = findViewById(R.id.texto);

        button = findViewById(R.id.cargar);
        button.setOnClickListener(v -> {
            textViewRaw.setText(getText(this.getResources().openRawResource(R.raw.texto)));
        });

        button2 = findViewById(R.id.guardar);
        button2.setOnClickListener(v -> {
            writeFile(textViewRaw.getText().toString());

        });

        checkStoragePermission();
    }

    private String getText(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String newLine = null;
                while ((newLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(newLine + "\n");
                }
                inputStream.close();
            }
        } catch (IOException e) {
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

    public boolean isExternalStorageReadable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public void checkStoragePermission() {
        int permission = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 101);
        }
    }

    public void writeFile(String text) {
        if (isExternalStorageWritable()) {
            checkStoragePermission();
            try {
                File textFile = new File(Environment.getExternalStorageDirectory(), FILENAME);
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                fileOutputStream.write(text.getBytes());
                fileOutputStream.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error writing file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Cannot write to External Storage", Toast.LENGTH_LONG).show();
        }
    }

    public void readFile(View view) {
        if (isExternalStorageReadable()) {
            checkStoragePermission();
            StringBuilder stringBuilder = new StringBuilder();
            try {
                File textFile = new File(Environment.getExternalStorageDirectory(), FILENAME);
                FileInputStream fileInputStream = new FileInputStream(textFile);
                if (fileInputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String newLine = null;
                    while ((newLine = bufferedReader.readLine()) != null) {
                        stringBuilder.append(newLine + "\n");
                    }
                    fileInputStream.close();
                }
                textViewRaw.setText(stringBuilder);
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Cannot read External Storage",
                    Toast.LENGTH_LONG).show();
        }


    }
}/* public void writeFile(View view) {
        if (isExternalStorageWritable()) {
            checkStoragePermission();
            try {
                File textFile = new File(Environment.getExternalStorageDirectory(), FILENAME);
                FileOutputStream fileOutputStream = new FileOutputStream(textFile);
                fileOutputStream.write(mEditText.getText().toString().getBytes());
                fileOutputStream.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error writing file", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Cannot write to External Storage", Toast.LENGTH_LONG).show();
        }
    }*/

