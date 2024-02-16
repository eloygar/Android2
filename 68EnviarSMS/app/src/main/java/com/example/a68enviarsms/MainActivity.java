package com.example.a68enviarsms;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.telephony.SmsManager;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> EnviarSMS(view));
    }

    public void EnviarSMS (View v) {
        EditText txtTelefono = (EditText) findViewById(R.id.editTextNumero);
        EditText txtMensaje = findViewById(R.id.mensaje);
        Log.i("OJO", "Enviando SMS ....");
        String telefono = txtTelefono.getText().toString();
        String message = txtMensaje.getText().toString();
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefono, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS enviado.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS no enviado, por favor, inténtalo otra vez.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}