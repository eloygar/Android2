package com.example.pmm1a.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PeliculasDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Peliculas.db";

    public PeliculasDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PeliculasEsquema.PeliculaEntry.TABLE_NAME + " ("
                + PeliculasEsquema.PeliculaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PeliculasEsquema.PeliculaEntry.TITULO + " TEXT NOT NULL,"
                + PeliculasEsquema.PeliculaEntry.DIRECTOR + " TEXT NOT NULL,"
                + PeliculasEsquema.PeliculaEntry.PAIS + " TEXT NOT NULL,"
                + PeliculasEsquema.PeliculaEntry.GENERO + " TEXT NOT NULL,"
                +PeliculasEsquema.PeliculaEntry.ANHO + " TEXT NOT NULL )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        return;
    }

    public long guardaPelicula(Pelicula pelicula){
        SQLiteDatabase db = getWritableDatabase();

        return db.insert(
                PeliculasEsquema.PeliculaEntry.TABLE_NAME,
                null,
                pelicula.toContentValues()
        );
    }

    public Cursor getAllPeliculas(){
        return getReadableDatabase()
                .query(
                        PeliculasEsquema.PeliculaEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
    }

    public Cursor getPeliculaById(String serieId) {
        Cursor c = getReadableDatabase().query(
                PeliculasEsquema.PeliculaEntry.TABLE_NAME,
                null,
                PeliculasEsquema.PeliculaEntry._ID + " LIKE ?",
                new String[]{serieId},
                null,
                null,
                null);
        return c;
    }

    public int deletePelicula(String peliculaId){
        return getWritableDatabase().delete(
                PeliculasEsquema.PeliculaEntry.TABLE_NAME,
                PeliculasEsquema.PeliculaEntry._ID + " LIKE ?",
                new String[]{peliculaId}
        );
    }

    public int updatePelicula(Pelicula pelicula, String peliculaId){
        return getWritableDatabase().update(
                PeliculasEsquema.PeliculaEntry.TABLE_NAME,
                pelicula.toContentValues(),
                PeliculasEsquema.PeliculaEntry._ID + " LIKE ?",
                new String[]{peliculaId}
        );
    }


}
