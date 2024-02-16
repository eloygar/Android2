package com.example.a63misdiscos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtGrupo, txtDisco;
    ListView listaDiscos;
    SQLiteDatabase db;
    Button add, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGrupo = findViewById(R.id.txtGrupo);
        txtDisco = findViewById(R.id.txtDisco);
        listaDiscos = findViewById(R.id.listaDiscos);
        db = openOrCreateDatabase("MisDiscos", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS MisDiscos (Grupo VARCHAR, Disco VARCHAR)");
        add = findViewById(R.id.buttonAddUpdate);
        delete = findViewById(R.id.buttonRemoveUpdate);
        add.setOnClickListener(v -> Add(v));
        delete.setOnClickListener(v -> Delete(v));
        Listar();
    }

    public void Add(View v) {
        String grupo = txtGrupo.getText().toString();
        String disco = txtDisco.getText().toString();

        if (!grupo.isEmpty() && !disco.isEmpty()) {
            db.execSQL("INSERT INTO MisDiscos (Grupo, Disco) VALUES ('" + grupo + "', '" + disco + "')");
            Toast.makeText(this, "Se añadió el disco " + disco, Toast.LENGTH_LONG).show();
            Listar();
        } else {
            Toast.makeText(this, "Por favor, ingrese valores válidos", Toast.LENGTH_LONG).show();
        }
    }

    public void Delete(View v) {
        try {
            String grupo = txtGrupo.getText().toString();
            String disco = txtDisco.getText().toString();

            db.execSQL("DELETE FROM MisDiscos WHERE Grupo = '" + grupo + "' AND Disco = '" + disco + "'");
            Toast.makeText(this, "Se borró el disco " + disco, Toast.LENGTH_LONG).show();
        } catch (SQLException s) {
            Toast.makeText(this, "Error al borrar!", Toast.LENGTH_LONG).show();
        } finally {
            Listar();
        }
    }

    public void Listar() {
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM MisDiscos", null);

        if (c.getCount() == 0)
            lista.add("No hay registros");
        else {
            while (c.moveToNext())
                lista.add(c.getString(0) + "-" + c.getString(1));

            adaptador = new ArrayAdapter<>(

                    getApplicationContext(), R.layout.lista_fila, lista);
            listaDiscos.setAdapter(adaptador);
        }

        c.close();
    }
}
