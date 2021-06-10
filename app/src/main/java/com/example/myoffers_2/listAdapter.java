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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class listAdapter extends RecyclerView.Adapter<listAdapter.modeloViewHolder> {

    private List<Modelo> miLista;
    private List<Modelo> Lista;
    private RecycleritemClick itemClick;
    private Context miContext;
    private int resourceLay;
    private static LayoutInflater inflater = null;

    public listAdapter(@NonNull Context context, List<Modelo> modelo) {
        this.miLista = modelo;
        this.miContext= context;
      //  this.itemClick=itemClick;
        this.Lista= new ArrayList<>();
      Lista.addAll(modelo);
    }

    @NonNull
    @Override
    public modeloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,null,false);
        return new modeloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull modeloViewHolder holder, int position) {
     Modelo modelo=miLista.get(position);
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
        return miLista.size();
    }
    public void filter(String search){
        if (search.length()==0){
            miLista.clear();
            miLista.addAll(Lista);
        }else{
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                miLista.clear();
                List<Modelo> collect=Lista.stream().filter(i->i.getNombre().toLowerCase().contains(search)).collect(Collectors.toList());
                miLista.addAll(collect);
            }else {
                miLista.clear();
                for (Modelo i:Lista){
                    if (i.getNombre().toLowerCase().contains(search)){
                        miLista.add(i);
                    }
                }
            }
        }notifyDataSetChanged();
    }
    public interface RecycleritemClick {
        void itemClick(Modelo modelo);
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

}



