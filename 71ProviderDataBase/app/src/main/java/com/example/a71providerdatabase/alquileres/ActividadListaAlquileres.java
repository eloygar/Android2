package com.example.a71providerdatabase.alquileres;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a71providerdatabase.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import com.example.a71providerdatabase.alquileres.provider.Contrato.Alquileres;
import com.google.android.material.snackbar.Snackbar;

public class ActividadListaAlquileres extends AppCompatActivity implements AdaptadorAlquileres.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView listaUI;
    private LinearLayoutManager linearLayoutManager;
    private AdaptadorAlquileres adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_lista_alquileres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Filtro...", Snackbar.LENGTH_LONG)
                        .setAction("Acción", null).show();
            }
        });

        // Preparar lista
        listaUI = (RecyclerView) findViewById(R.id.lista);
        listaUI.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(this);
        listaUI.setLayoutManager(linearLayoutManager);

        adaptador = new AdaptadorAlquileres(this, this);
        listaUI.setAdapter(adaptador);

        // Iniciar loader
        getSupportLoaderManager().restartLoader(1, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actividad_lista_alquileres, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(AdaptadorAlquileres.ViewHolder holder, String idAlquiler) {
        Snackbar.make(findViewById(android.R.id.content), ":id = " + idAlquiler,
                Snackbar.LENGTH_LONG).show();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Alquileres.URI_CONTENIDO, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (adaptador != null) {
            adaptador.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
