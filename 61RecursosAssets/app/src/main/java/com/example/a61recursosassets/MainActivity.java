package com.example.a61recursosassets;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            openAsset(v);
            openImageAsset(v);
        });

    }

    private void openAsset(View v) {
        editText = findViewById(R.id.editText);
        try {
            editText.setText(getText(this.getAssets().open("mitexto.txt")));
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    private void openImageAsset(View v) {
        image = findViewById(R.id.imageView);
        try {
            image.setImageBitmap(BitmapFactory.decodeStream(this.getAssets().open("ditto.png")));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}