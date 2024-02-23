package com.example.pmm1a.data;

import android.provider.BaseColumns;

/**
 * Esquema de la base de datos para abogados
 */
public class PeliculasEsquema {

    public abstract  class PeliculaEntry implements BaseColumns {
        public static final String TABLE_NAME = "pelicula";

        public static final String TITULO = "titulo";
        public static final String DIRECTOR = "director";
        public static final String PAIS = "pais";
        public static final String GENERO = "genero";
        public static final String ANHO = "anho";
    }
}
