package com.example.pmm2a;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    ListView l;
    private TextView salida;
    private final String tag = "SMS:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.lstContactos);
        listView.setOnItemLongClickListener(this);
        salida = (TextView) findViewById(R.id.txtBuscar);
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
        String[] args_filtro = {txtNombre.getText().toString() + "%"};
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

    /*
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
                BuscarGoogle(identificador);
            }
        }
        cur.close();
        return true;
    }*/
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
                new BuscarGoogle().execute(identificador);
            }
        }
        cur.close();
        return true;
    }



    /*
    class BuscarGoogle extends AsyncTask<String, Void, String> {
        private ProgressDialog progreso;

        @Override
        protected void onPreExecute() {
            progreso = new ProgressDialog(MainActivity.this);
            progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progreso.setMessage("Accediendo a Google...");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected String doInBackground(String... palabras) {
            try {
                return resultadoGoogle(palabras[0]);
            } catch (Exception e) {
                cancel(true);
                Log.e("HTTP", e.getMessage(), e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String res) {
            progreso.dismiss();
            salida.append(res + "\n");
        }

        @Override
        protected void onCancelled() {
            progreso.dismiss();
            salida.append("Error al conectarlo.\n");
        }
    }
*/
    //envia un SMS a los teléfonos de un contacto
    class BuscarGoogle extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... ids) {
            String id = ids[0];
            String devuelve = "";
            HttpURLConnection conexion = null;
            try {
                URL url = new URL("https://www.google.es/search?q=" + URLEncoder.encode(id, "UTF-8"));
                conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestProperty("User-Agent", "Mozilla/5.0" +
                        " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conexion.getInputStream()));
                    String linea = reader.readLine();
                    String pagina = "";
                    while (linea != null) {
                        pagina += linea;
                        linea = reader.readLine();
                    }
                    reader.close();
                    int count = pagina.split("<a href=").length - 1;
                    devuelve = "resultados de busqueda " + count;
                } else {
                    devuelve = "Error: " + conexion.getResponseMessage();
                }
                conexion.disconnect();
            } catch (Exception e) {
                devuelve = "Error: " + e.getMessage();
            }
            return devuelve;
        }
        @Override
        protected void onPostExecute(String result) {
            salida.append(result + "\n");
        }
    }

}