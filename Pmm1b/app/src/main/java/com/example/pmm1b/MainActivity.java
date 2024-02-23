package com.example.pmm1b;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import com.example.pmm1b.dataBase.Serie;
import com.example.pmm1b.dataBase.SeriesDBHelper;

public class MainActivity extends AppCompatActivity {
    List<Serie> DatosSeries = new ArrayList<Serie>();
    EditText editTextTitulo, editTextDirector, editTextPais, editTextAnho, editTextNumeroTemporadas;
    Button buttonModificar, buttonBorrar, buttonInsertar, buttonListar;
    ListView listViewSeries;

    Serie _serie = null;

    SeriesDBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new SeriesDBHelper(this);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextPais = findViewById(R.id.editTextPais);
        editTextAnho = findViewById(R.id.editTextAnho);
        editTextNumeroTemporadas = findViewById(R.id.editTextNumeroTemporadas);

        listViewSeries = findViewById(R.id.listViewSeries);

        buttonInsertar = findViewById(R.id.buttonInsertar);
        buttonModificar = findViewById(R.id.buttonModificar);
        buttonBorrar = findViewById(R.id.buttonBorrar);
        buttonListar = findViewById(R.id.buttonListar);

        buttonInsertar.setOnClickListener(v -> insertarSerie());
        buttonModificar.setOnClickListener(v -> modificarSerie());
        buttonBorrar.setOnClickListener(v -> eliminarSerie());
        buttonListar.setOnClickListener(v -> listarSeries());

        listViewSeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    _serie = DatosSeries.get(position);
                } catch (IndexOutOfBoundsException e) {
                    _serie = null;
                    Toast.makeText(MainActivity.this, "Error al seleccionar serie", Toast.LENGTH_SHORT).show();
                }
                if (_serie == null)
                    return;
                editTextTitulo.setText(_serie.getTitulo());
                editTextDirector.setText(_serie.getDirector());
                editTextPais.setText(_serie.getPais());
                editTextAnho.setText(_serie.getAnho());
                editTextNumeroTemporadas.setText(_serie.getNumeroTemporadas());
            }
        });

    }

    public void insertarSerie() {
        String titulo = editTextTitulo.getText().toString();
        String director = editTextDirector.getText().toString();
        String pais = editTextPais.getText().toString();
        String anho = editTextAnho.getText().toString();
        String numeroTemporadas = editTextNumeroTemporadas.getText().toString();

        Serie serie = new Serie(null, titulo, director, pais, anho, numeroTemporadas);
        db.saveSerie(serie);
        listarSeries();
    }

    private void cargarLista() {
        DatosSeries.clear();
        List<Serie> lista = new ArrayList<Serie>();
        Cursor c = db.getAllSeries();

        Serie serie = null;
        if (c.getCount() == 0)
            return;
        else {
            while (c.moveToNext()) {
                /*Toast.makeText(this, "id "+c.getString(0)+" series", Toast.LENGTH_SHORT).show();*/

                serie = new Serie(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
                if (serie != null) {
                    lista.add(serie);
                }

            }
        }
        c.close();
        DatosSeries = lista;
    }

    public void listarSeries() {
        cargarLista();
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<>();

        for (Serie serie : DatosSeries) {
            lista.add(serie.getTitulo() + " - " + serie.getDirector() + " - " + serie.getPais() + " - " + serie.getAnho() + " - " + serie.getNumeroTemporadas());

        }
        adaptador = new ArrayAdapter<>(
                getApplicationContext(), R.layout.lista_fila, lista);
        listViewSeries.setAdapter(adaptador);
    }

    public void eliminarSerie() {
        if (_serie == null) {
            Toast.makeText(this, "escoge una serie", Toast.LENGTH_SHORT).show();
            return;
        }
        Serie serie = _serie;
        String id = serie.getId();
        Cursor c = db.getSerieById(id);
        if (c.getCount() == 0) {
            Toast.makeText(this, "no existe la serie" + serie.getTitulo(), Toast.LENGTH_SHORT).show();
            return;
        }

        db.deleteSerie(id);
        _serie = null;

        Toast.makeText(this, "Serie Borrada " + serie.getTitulo(), Toast.LENGTH_SHORT).show();
        listarSeries();
    }

    public void modificarSerie() {
        if (_serie == null) {
            Toast.makeText(this, "escoge una serie", Toast.LENGTH_SHORT).show();
            return;
        }
        Serie serie = _serie;
        String id = serie.getId();
        String titulo = editTextTitulo.getText().toString();
        String director = editTextDirector.getText().toString();
        String pais = editTextPais.getText().toString();
        String anho = editTextAnho.getText().toString();
        String numeroTemporadas = editTextNumeroTemporadas.getText().toString();

        Serie nuevosDatos = new Serie(id, titulo, director, pais, anho, numeroTemporadas);
        db.updateSerie(nuevosDatos, id);
        _serie = null;

        Toast.makeText(this, "Serie Modifcada " + serie.getTitulo(), Toast.LENGTH_SHORT).show();
        listarSeries();

    }
}