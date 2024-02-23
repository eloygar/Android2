package com.example.pmm1b.dataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SeriesDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Series.db";

    public SeriesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SerieEsquema.TABLE_NAME + " ("
                + SerieEsquema._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SerieEsquema.TITULO + " TEXT,"
                + SerieEsquema.DIRECTOR + " TEXT,"
                + SerieEsquema.PAIS + " TEXT,"
                + SerieEsquema.ANHO + " TEXT,"
                + SerieEsquema.NUEMERO_TEMPORADAS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long saveSerie(Serie serie) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(
                SerieEsquema.TABLE_NAME,
                null,
                serie.toContentValues());
    }

    public int updateSerie(Serie serie, String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update(
                SerieEsquema.TABLE_NAME,
                serie.toContentValues(),
                SerieEsquema._ID + " LIKE ?",
                new String[]{id});
    }
  public Cursor getAllSeries() {
        return getReadableDatabase()
                .query(
                        SerieEsquema.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getSerieById(String serieId) {
        Cursor c = getReadableDatabase().query(
                SerieEsquema.TABLE_NAME,
                null,
                SerieEsquema._ID + " LIKE ?",
                new String[]{serieId},
                null,
                null,
                null);
        return c;
    }

    public int deleteSerie(String serieId) {
        return getWritableDatabase().delete(
                SerieEsquema.TABLE_NAME,
                SerieEsquema._ID + " LIKE ?",
                new String[]{serieId});
    }

}
