package com.example.pmm1b.dataBase;


import android.provider.BaseColumns;
public abstract class SerieEsquema implements BaseColumns{


    public static final String TABLE_NAME ="series";

    public static final String TITULO = "titulo";
    public static final String DIRECTOR = "director";
    public static final String PAIS = "pais";
    public static final String ANHO = "anho";
    public static final String NUEMERO_TEMPORADAS = "numeroTemporadas";
}
