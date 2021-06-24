package com.example.myoffers_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class listAdapter extends RecyclerView.Adapter<listAdapter.modeloViewHolder> {

    private List<Modelo> items;
    private List<Modelo> originalItems;
    private RecycleItemClick itemClick;
    private Context miContext;


    public listAdapter(@NonNull Context context, List<Modelo> modelo,RecycleItemClick click) {
        this.items = modelo;
        this.miContext= context;
        this.itemClick=click;
        this.originalItems= new ArrayList<>();
        originalItems.addAll(modelo);
    }

    public void setItemClick(RecycleItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public modeloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,null,false);
        return new modeloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull modeloViewHolder holder, int position) {
     final Modelo modelo=items.get(position);
     Glide.with(this.miContext).load(modelo.getImagen()).into(holder.imagen);
     holder.Nombre.setText(modelo.getNombre());
     holder.Marca.setText(modelo.getMarca());
     holder.Descri.setText(modelo.getDescripcion());
     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             itemClick.itemClick(modelo);
         }
     });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filter(String txtsearch){
        if (txtsearch.length()==0){
            items.clear();
            items.addAll(originalItems);
        }else{
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                List<Modelo> collect=originalItems.stream().filter(i ->i.getNombre().toLowerCase().contains(txtsearch)).collect(Collectors.toList());
                items.clear();
                items.addAll(collect);
            }else {
                items.clear();
                for (Modelo i:originalItems){
                    if (i.getNombre().toLowerCase().contains(txtsearch)){
                        items.add(i);
                    }
                }
            }
        }notifyDataSetChanged();
    }

    public class modeloViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView Nombre;
        TextView Marca;
        TextView Descri;

        public modeloViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen= (ImageView) itemView.findViewById(R.id.idImagen);
            Nombre=(TextView) itemView.findViewById(R.id.txtNombre);
            Marca=(TextView) itemView.findViewById(R.id.idMarca);
            Descri=(TextView) itemView.findViewById(R.id.txtDescrip);
        }
    }
    public interface RecycleItemClick{
        void itemClick(Modelo item);
    }
}



