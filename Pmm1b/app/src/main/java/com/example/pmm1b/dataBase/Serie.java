package com.example.pmm1b.dataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

public class Serie {
    private String id;
    private String titulo;
    private String director;
    private String pais;
    private String anho;
    private String numeroTemporadas;

    public Serie(String id,String titulo, String director, String pais, String anho, String numeroTemporadas) {
        this.titulo = titulo;
        this.director = director;
        this.pais = pais;
        this.anho = anho;
        this.numeroTemporadas = numeroTemporadas;
        this.id = id;
    }

    @SuppressLint("Range")
    public Serie(Cursor cur) {
        id = cur.getString(cur.getColumnIndex(SerieEsquema._ID));
        titulo = cur.getString(cur.getColumnIndex(SerieEsquema.TITULO));
        director = cur.getString(cur.getColumnIndex(SerieEsquema.DIRECTOR));
        pais = cur.getString(cur.getColumnIndex(SerieEsquema.PAIS));
        anho = cur.getString(cur.getColumnIndex(SerieEsquema.ANHO));
        numeroTemporadas = cur.getString(cur.getColumnIndex(SerieEsquema.NUEMERO_TEMPORADAS));
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(SerieEsquema._ID, id);
        values.put(SerieEsquema.TITULO, titulo);
        values.put(SerieEsquema.DIRECTOR, director);
        values.put(SerieEsquema.PAIS, pais);
        values.put(SerieEsquema.ANHO, anho);
        values.put(SerieEsquema.NUEMERO_TEMPORADAS, numeroTemporadas);
        return values;
    }

    public String getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }

    public String getDirector() {
        return director;
    }

    public String getPais() {
        return pais;
    }

    public String getAnho() {
        return anho;
    }

    public String getNumeroTemporadas() {
        return numeroTemporadas;
    }


    public void setId(String id) {
        this.id = id;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    public void setNumeroTemporadas(String numeroTemporadas) {
        this.numeroTemporadas = numeroTemporadas;
    }
}
