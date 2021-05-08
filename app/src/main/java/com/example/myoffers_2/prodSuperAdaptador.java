package com.example.myoffers_2;

import android.content.Context;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class prodSuperAdaptador extends RecyclerView.Adapter<prodSuperAdaptador.prodViewHolder> {

    private List<ProdxSuper> items;
    private Context micontext;
    private View vista;
    int PosicionMarcada=0;


    public prodSuperAdaptador(List<ProdxSuper> items, Context context){
        this.items=items;
        this.micontext=context;
    }

    public ProdxSuper getItem(int position) {
        return items.get(position);
    }

    @NonNull
    @Override
    public prodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inicializa la interfaz de un item de la lista a partir del layaut utilizando para ello LayoutInflater
        vista = LayoutInflater.from(micontext).inflate(R.layout.list_layout, parent, false);
        return new prodViewHolder(vista);
    }

    @Override
    public void onBindViewHolder( prodViewHolder holder,final int position) {

       // holder.image.setImageResource(R.mipmap.logo);
         ProdxSuper obj=items.get(position);
        //aqui estoy cargando la imagen junto con los demas datos
        Glide.with(micontext).load(obj.getImagen()).into(holder.image);
        holder.titulo.setText(obj.getNombre());
        holder.descripcion.setText(items.get(position).getDescripcion());
        holder.precio.setText(String.valueOf(items.get(position).getPrecio()));
        //este es el evento onClickk
        final int pos=position;
       prodViewHolder.cv.setOnClickListener(new OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    PosicionMarcada=pos;
                                                    Toast toast = Toast.makeText(v.getContext(), "Eleji la tarjeta numero"+position, Toast.LENGTH_SHORT);
                                                    toast.show();
                                                    notifyDataSetChanged();
                                                }
                                            });
       if (PosicionMarcada==position){
           prodViewHolder.cv.setCardElevation(vista.getResources().getDimension(R.dimen.cardView1));
       }else{
           prodViewHolder.cv.setCardElevation(vista.getResources().getDimension(R.dimen.cardView));
       }
       holder.itemView.setOnClickListener(new OnClickListener() {

           @Override
           public void onClick(View v) {
               //pasa como parametros el id del producto y del usuario si es administrador
               //puede sino no
               RegisProductoDirections.ActionRegisProductoToEditarOferta action=RegisProductoDirections.actionRegisProductoToEditarOferta();
               action.setNombre("pan");
               action.setPosicion(pos);
               Navigation.findNavController(v).navigate(action);
           }
       });
    }
public void add(ProdxSuper PS){
        items.add(PS);
        notifyItemInserted(items.size()-1);
}
public void addAll(ArrayList<ProdxSuper> ps){
        for (ProdxSuper prodxSuper: ps){
            add(prodxSuper);
        }
}
    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class prodViewHolder extends RecyclerView.ViewHolder{
    public ImageView image;
    public TextView titulo, descripcion;
    public TextView precio;
     static CardView cv;
    View contenedor;


    public prodViewHolder(View v){
        super(v);
        image=(ImageView) v.findViewById(R.id.rv_imagen);
        titulo=(TextView)v.findViewById(R.id.rv_nombre);
        descripcion=(TextView)v.findViewById(R.id.rv_descripcion);
        precio=(TextView)v.findViewById(R.id.rv_precio);
        cv = (CardView)v.findViewById(R.id.cardview);
        contenedor=v;

    }}
}
