package com.example.a67demobroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Objects;

public class Receptor extends BroadcastReceiver {

    private final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), SMS_RECEIVED)) {
            Toast.makeText(context, "RECIBIDO SMS", Toast.LENGTH_SHORT).show();
        }

    }
}

