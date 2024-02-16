package com.example.a69contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    ListView l;
    private final String tag = "SMS:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l=(ListView)findViewById(R.id.lstContactos);
        l.setOnItemLongClickListener(this);
    }

    @SuppressLint("Range")
    public void Buscar(View v) {
        EditText txtNombre = findViewById(R.id.txtContacto);

        String[] proyeccion = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.PHOTO_ID
        };
        String filtro = ContactsContract.Contacts.DISPLAY_NAME + " like ?";
        String[] args_filtro = {"%" + txtNombre.getText().toString() + "%"};
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                proyeccion, filtro, args_filtro, null);

        List<String> lista_contactos = new ArrayList<>();
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                @SuppressLint("Range") String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    lista_contactos.add(name);
                }
            }
            ListView l = findViewById(R.id.lstContactos);
            l.setAdapter(new ArrayAdapter<>(this, R.layout.fila_lista, lista_contactos));
            cur.close();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView t = (TextView) view;
        String nombreContacto = t.getText().toString();
        String[] proyeccion = {ContactsContract.Contacts._ID};
        String filtro = ContactsContract.Contacts.DISPLAY_NAME + " = ?";
        String[] args_filtro = {nombreContacto};
        List<String> lista_contactos = new ArrayList<String>();
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, proyeccion, filtro, args_filtro, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                @SuppressLint("Range") String identificador = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                EnviarSMS(identificador);
            }
        }
        cur.close();
        return true;
    }
    //envia un SMS a los teléfonos de un contacto
    private void EnviarSMS(String identificador){
        ContentResolver cr = getContentResolver();
        SmsManager smsManager = SmsManager.getDefault();
        String mensaje=((EditText)findViewById(R.id.txtSMS)).getText().toString();
        Cursor cursorTelefono = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                new String[]{identificador}, null);
        while (cursorTelefono.moveToNext()) {
            @SuppressLint("Range")
            String telefono = cursorTelefono.getString(
                    cursorTelefono.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA));
            try {
                smsManager.sendTextMessage(telefono, null, mensaje, null, null);
                Log.d(tag, "SMS enviado.");
            } catch (Exception e) {
                Log.d(tag, "No se pudo enviar el SMS.");
                e.printStackTrace();
            }

        }
        cursorTelefono.close();
    }
}