package com.example.a68enviarsms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class ReceptorSMS extends BroadcastReceiver {

    private final String
    SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    //Interfaz (listener) para comunicarnos
    //con la actividad que instancia un BroadcastReceiver
    public interface onRecibeSMS {
        void onRecibeSMS(String origen, String msg);
    }
    private onRecibeSMS respuesta;
    public void setOnRecibeSMSListener(Activity x){
        respuesta = (onRecibeSMS) x;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            //Esto aborta notificaciones otros
            this.abortBroadcast();
            // get the SMS message passed in
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String origen = null;
            String msg = null;
            if (bundle != null) {
                // obtenemos
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    origen = msgs[i].getOriginatingAddress();
                    msg = msgs[i].getMessageBody().toString();
                    respuesta.onRecibeSMS(origen, msg);
                    Toast.makeText(context, "SMS Recibido!", Toast.LENGTH_LONG).show();
                }
            }
            this.clearAbortBroadcast();
        }
    }
}
