package com.example.pmm1a;

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

import com.example.pmm1a.data.Pelicula;
import com.example.pmm1a.data.PeliculasDBHelper;


public class MainActivity extends AppCompatActivity {

    List<Pelicula> DatosPeliculas = new ArrayList<Pelicula>();
    EditText editTextTitulo, editTextDirector, editTextPais, editTextGenero, editTextAnho;
    Button buttonModificar, buttonBorrar, buttonInsertar, buttonListar;
    ListView listViewPeliculas;
    PeliculasDBHelper db;

    Pelicula _pelicula = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new PeliculasDBHelper(this);

        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextPais = findViewById(R.id.editTextPais);
        editTextGenero = findViewById(R.id.editTextGenero);
        editTextAnho = findViewById(R.id.editTextAnho);

        listViewPeliculas = findViewById(R.id.listaPeliculas);

        buttonInsertar = findViewById(R.id.buttonInsertar);
        buttonModificar = findViewById(R.id.buttonModificar);
        buttonBorrar = findViewById(R.id.buttonBorrar);
        buttonListar = findViewById(R.id.buttonListar);

        buttonInsertar.setOnClickListener(v -> insertar());
        buttonModificar.setOnClickListener(v -> modificarPelicula());
        buttonBorrar.setOnClickListener(v -> eliminarPelicula());
        buttonListar.setOnClickListener(v -> listar());
        listViewPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    _pelicula = DatosPeliculas.get(position);
                } catch (IndexOutOfBoundsException e) {
                    _pelicula = null;
                    Toast.makeText(MainActivity.this, "Error al seleccionar disco", Toast.LENGTH_SHORT).show();
                }
                if (_pelicula == null)
                    return;
                editTextTitulo.setText(_pelicula.getTitulo());
                editTextDirector.setText(_pelicula.getDirector());
                editTextPais.setText(_pelicula.getPais());
                editTextGenero.setText(_pelicula.getGenero());
                editTextAnho.setText(_pelicula.getAnho());
            }
        });


    }

    private void insertar() {
        String titulo = editTextTitulo.getText().toString();
        String director = editTextDirector.getText().toString();
        String pais = editTextPais.getText().toString();
        String genero = editTextGenero.getText().toString();
        String anho = editTextAnho.getText().toString();

        Pelicula pelicula = new Pelicula(null, titulo, director, pais, genero, anho);
        db.guardaPelicula(pelicula);
        listar();
    }

    public void modificarPelicula() {

        if (db == null) {
            Toast.makeText(this, "Selecciona una pelicula en la lista", Toast.LENGTH_SHORT).show();
            return;
        }
        Pelicula pelicula = _pelicula;
        String id = pelicula.getId();

        Cursor c = db.getPeliculaById(id);
        if (c.getCount() == 0) {
            Toast.makeText(this, "No se encontro la pelicula " + pelicula.getTitulo(), Toast.LENGTH_SHORT).show();
            return;
        }

        String titulo = editTextTitulo.getText().toString();
        String director = editTextDirector.getText().toString();
        String pais = editTextPais.getText().toString();
        String genero = editTextGenero.getText().toString();
        String anho = editTextAnho.getText().toString();

        Pelicula nuevosDatos = new Pelicula(id, titulo, director, pais, genero, anho);

        db.updatePelicula(nuevosDatos, id);
        _pelicula = null;
        Toast.makeText(this, "Se actualizo la pelicula " + pelicula.getTitulo(), Toast.LENGTH_SHORT).show();
        listar();

    }


    private void cargarLista() {
        DatosPeliculas.clear();
        List<Pelicula> lista = new ArrayList<Pelicula>();
        Cursor c = db.getAllPeliculas();

        Pelicula pelicula = null;
        if (c.getCount() == 0)
            return;
        else {
            while (c.moveToNext()) {
                /*Toast.makeText(this, "id "+c.getString(0)+" series", Toast.LENGTH_SHORT).show();*/

                pelicula = new Pelicula(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
                if (pelicula != null) {
                    lista.add(pelicula);
                }

            }
        }
        c.close();
        DatosPeliculas = lista;
    }

    public void listar() {
        cargarLista();
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<>();

        for (Pelicula pelicula : DatosPeliculas) {
            lista.add(pelicula.getTitulo() + " - " + pelicula.getDirector() + " - " + pelicula.getPais() + " - " + pelicula.getGenero() + " - " + pelicula.getAnho());


        }

        adaptador = new ArrayAdapter<>(
                getApplicationContext(), R.layout.lista_fila, lista);
        listViewPeliculas.setAdapter(adaptador);
    }


    public void eliminarPelicula() {
        if (_pelicula == null) {
            Toast.makeText(this, "Selecciona una pelicula en la lista", Toast.LENGTH_SHORT).show();
            return;
        }
        Pelicula pelicula = _pelicula;
        String id = pelicula.getId();

        Cursor c = db.getPeliculaById(id);
        if (c.getCount() == 0) {
            Toast.makeText(this, "No se encontro la pelicula " + pelicula.getTitulo(), Toast.LENGTH_SHORT).show();
            return;
        }
        db.deletePelicula(id);
        _pelicula = null;
        Toast.makeText(this, "Se borro la pelicula " + pelicula.getTitulo(), Toast.LENGTH_SHORT).show();
        listar();

    }
}