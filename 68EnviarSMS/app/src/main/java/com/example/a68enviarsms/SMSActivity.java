package com.example.a68enviarsms;

import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SMSActivity extends AppCompatActivity implements ReceptorSMS.onRecibeSMS {

    public final  String tag ="DemoSMS";

    ReceptorSMS receptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creamos y registramos el receptor de sms de manera dinamica
        //en vez de declararlo como receptor en el MANIFEST
        receptor = new ReceptorSMS();
        registerReceiver(receptor, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        receptor.setOnRecibeSMSListener(this);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> EnviarSMS(view));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receptor); // para evitar memory leaks
        receptor = null;
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

    @Override
    public void onRecibeSMS(String origen, String mensaje) {
        EditText editText = findViewById(R.id.editTextNumero);
        editText.setText("mensaje de: "+origen);
        EditText editTextMen = findViewById(R.id.mensaje);
        editTextMen.setText(mensaje);
    }
}
