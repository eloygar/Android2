package com.example.a56almacenarpreferencias;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String NAME = "NAME";
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.tv_ver);
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME, null);
        if(name == null){
            textView.setText("No hay nada guardado");
        } else {
            textView.setText("Benvido de volta " + name);
        }
        mEditText = findViewById(R.id.et_escribir);

        Button button = findViewById(R.id.btn_guardar);
        button.setOnClickListener(view -> saveName(view));

    }

    private  void saveName(View view){
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString(NAME, mEditText.getText().toString());
        editor.commit();
    }


}