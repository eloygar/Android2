package com.example.a71providerdatabase.alquileres.provider;


import android.net.Uri;

import java.util.UUID;

/**
 * Contrato con la estructura de la base de datos y forma de las URIs
 */
public class Contrato {

    interface ColumnasAlquiler {
        String ID_ALQUILER = "idAlquiler"; // Pk
        String NOMBRE  = "nombre";
        String UBICACION = "ubicacion";
        String DESCRIPCION = "descripcion";
        String PRECIO = "precio";
        String URL_IMAGEN ="urlImagen";
    }


    // Autoridad del Content Provider
    public final static String AUTORIDAD = "com.herprogramacion.alquileres";

    // Uri base
    public final static Uri URI_CONTENIDO_BASE = Uri.parse("content://" + AUTORIDAD);


    /**
     * Controlador de la tabla "alquiler"
     */
    public static class Alquileres implements ColumnasAlquiler {

        public static final Uri URI_CONTENIDO =
                URI_CONTENIDO_BASE.buildUpon().appendPath(RECURSO_ALQUILERES).build();

        public final static String MIME_RECURSO =
                "vnd.android.cursor.item/vnd." + AUTORIDAD + "/" + RECURSO_ALQUILERES;

        public final static String MIME_COLECCION =
                "vnd.android.cursor.dir/vnd." + AUTORIDAD + "/" + RECURSO_ALQUILERES;


        /**
         * Construye una {@link Uri} para el {@link #ID_ALQUILER} solicitado.
         */
        public static Uri construirUriAlquiler(String idApartamento) {
            return URI_CONTENIDO.buildUpon().appendPath(idApartamento).build();
        }

        public static String generarIdAlquiler() {
            return "A-" + UUID.randomUUID();
        }

        public static String obtenerIdAlquiler(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    // Recursos
    public final static String RECURSO_ALQUILERES = "alquileres";

}