package com.example.a71providerdatabase.alquileres;

import android.content.Context;
import android.database.Cursor;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a71providerdatabase.R;


/**
 * Adaptador con un cursor para poblar la lista de alquileres desde SQLite
 */
public class AdaptadorAlquileres extends RecyclerView.Adapter<AdaptadorAlquileres.ViewHolder> {
    private final Context contexto;
    private Cursor items;

    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, String idAlquiler);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Referencias UI
        public TextView viewNombre;
        public TextView viewUbicacion;
        public TextView viewDescripcion;
        public TextView viewPrecio;
        public ImageView viewFoto;

        public ViewHolder(View v) {
            super(v);
            viewNombre = (TextView) v.findViewById(R.id.nombre);
            viewUbicacion = (TextView) v.findViewById(R.id.ubicacion);
            viewDescripcion = (TextView) v.findViewById(R.id.descripcion);
            viewPrecio = (TextView) v.findViewById(R.id.precio);
            viewFoto = (ImageView) v.findViewById(R.id.foto);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            escucha.onClick(this, obtenerIdAlquiler(getAdapterPosition()));
        }
    }

    private String obtenerIdAlquiler(int posicion) {
        if (items != null) {
            if (items.moveToPosition(posicion)) {
                return items.getString(ConsultaAlquileres.ID_ALQUILER);
            }
        }

        return null;
    }

    public AdaptadorAlquileres(Context contexto, OnItemClickListener escucha) {
        this.contexto = contexto;
        this.escucha = escucha;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_alquiler, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);

        String s;

        // Asignación UI
        s = items.getString(ConsultaAlquileres.NOMBRE);
        holder.viewNombre.setText(s);

        s = items.getString(ConsultaAlquileres.UBICACION);
        holder.viewUbicacion.setText(s);

        s = items.getString(ConsultaAlquileres.DESCRIPCION);
        holder.viewDescripcion.setText(s);

        s = items.getString(ConsultaAlquileres.PRECIO);
        holder.viewPrecio.setText(String.format("$ %s", s));

        s = items.getString(ConsultaAlquileres.URL);
        Glide.with(contexto).load(s).centerCrop().into(holder.viewFoto);


    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.getCount();
        return 0;
    }

    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            items = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return items;
    }

    interface ConsultaAlquileres {
        int ID_ALQUILER = 1;
        int NOMBRE = 2;
        int UBICACION = 3;
        int DESCRIPCION = 4;
        int PRECIO = 5;
        int URL = 6;
    }
}
