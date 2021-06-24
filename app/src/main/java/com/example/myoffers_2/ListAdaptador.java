package com.example.myoffers_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListAdaptador extends RecyclerView.Adapter<ListAdaptador.Holder> {

    private List<Modelo> Lista;

    private Context miContext;

    public ListAdaptador(List<Modelo> lista,Context miContext) {
        Lista = lista;

        this.miContext = miContext;
    }

    @NonNull
    @Override
    public ListAdaptador.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,null,false);
        //view.setOnClickListener(this);
        return new Holder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull ListAdaptador.Holder holder, int position) {
        Modelo modelo=Lista.get(position);
        Glide.with(this.miContext).load(modelo.getImagen()).into(holder.imagen);
        holder.Nombre.setText(modelo.getNombre());
        holder.Marca.setText(modelo.getMarca());
        holder.Descri.setText(modelo.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView Nombre;
        TextView Marca;
        TextView Descri;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imagen= (ImageView) itemView.findViewById(R.id.idImagen);
            Nombre=(TextView) itemView.findViewById(R.id.txtNombre);
            Marca=(TextView) itemView.findViewById(R.id.idMarca);
            Descri=(TextView) itemView.findViewById(R.id.txtDescrip);
        }
    }
}
