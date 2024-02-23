package com.example.a65busquedaasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {

    private EditText entrada;
    private TextView salida;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entrada = (EditText) findViewById(R.id.EditText01);

        salida = (TextView) findViewById(R.id.TextView01);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy
                .Builder().permitNetwork().build());
        button = findViewById(R.id.Button01);
        button.setOnClickListener(v -> {
           Buscar(v);
        });
    }

    public void Buscar(View view) {
        try {
            String palabras = entrada.getText().toString();
            String resultado = resultadoGoogle(palabras);
            salida.append(palabras + " -- " + resultado + "\n");
        } catch (Exception e) {
            salida.append("Error al conectar!!!\n");
            Toast.makeText(this, "Error al conectar!!!\n", Toast.LENGTH_LONG).show();
        }
    }

    String resultadoGoogle(String palabras) throws Exception {

        String pagina = "", devuelve = "";

        URL url = new URL("https://www.google.es/search?hl=en&q=\""
                + URLEncoder.encode(palabras, "UTF-8") + "\"");

        HttpURLConnection conexion = (HttpURLConnection)
                url.openConnection();

        //ESTAMOS INDICANDO EL BROWSER QUE VAMOS A UTILIZAR
        conexion.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0;"
                + "Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) "
                + "Chrome/88.0.4324.104 Safari/537.36");

        if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conexion.getInputStream()));

            String linea = reader.readLine();
            while (linea != null) {
                pagina += linea;
                linea = reader.readLine();
            }
            reader.close();

            int ini = pagina.indexOf("About");
            if (ini != -1) {
                int fin = pagina.indexOf(" ", ini + 16);
                devuelve = pagina.substring(ini + 6, fin);
            } else {
                devuelve = "no encontrado";
            }
        } else {
            salida.append("Error: " + conexion.getResponseMessage() + "\n");
        }
        conexion.disconnect();
        return devuelve;
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
}
