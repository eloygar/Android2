package com.example.pmm1a.data;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.UUID;

public class Pelicula {
    String id;
    String titulo;
    String director;
    String pais;
    String genero;
    String anho;

    public Pelicula(String id, String titulo, String director, String pais, String genero, String anho) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.pais = pais;
        this.genero = genero;
        this.anho = anho;
    }

    @SuppressLint("Range")
    public Pelicula(Cursor cursor){
        id = cursor.getString(cursor.getColumnIndex(PeliculasEsquema.PeliculaEntry._ID));
        titulo = cursor.getString(cursor.getColumnIndex(PeliculasEsquema.PeliculaEntry.TITULO));
        director = cursor.getString(cursor.getColumnIndex(PeliculasEsquema.PeliculaEntry.DIRECTOR));
        pais = cursor.getString(cursor.getColumnIndex(PeliculasEsquema.PeliculaEntry.PAIS));
        genero = cursor.getString(cursor.getColumnIndex(PeliculasEsquema.PeliculaEntry.GENERO));
        anho = cursor.getString(cursor.getColumnIndex(PeliculasEsquema.PeliculaEntry.ANHO));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(PeliculasEsquema.PeliculaEntry._ID, id);
        values.put(PeliculasEsquema.PeliculaEntry.TITULO, titulo);
        values.put(PeliculasEsquema.PeliculaEntry.DIRECTOR, director);
        values.put(PeliculasEsquema.PeliculaEntry.PAIS, pais);
        values.put(PeliculasEsquema.PeliculaEntry.GENERO, genero);
        values.put(PeliculasEsquema.PeliculaEntry.ANHO, anho);
        return values;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getAnho() {
        return anho;
    }
    public void setAnho(String anho) {
        this.anho = anho;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }


}