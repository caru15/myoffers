package com.example.myoffers_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int posicion = position;
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(miContext);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Desea Eliminar este producto?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Lista.remove(posicion);
                        notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Lista.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imagen;
        TextView Nombre;
        TextView Marca;
        TextView Descri;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.idcard);
            imagen= (ImageView) itemView.findViewById(R.id.idImagen);
            Nombre=(TextView) itemView.findViewById(R.id.txtNombre);
            Marca=(TextView) itemView.findViewById(R.id.idMarca);
            Descri=(TextView) itemView.findViewById(R.id.txtDescrip);
        }
    }
}
