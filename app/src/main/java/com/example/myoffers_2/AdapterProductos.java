package com.example.myoffers_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ViewHolderProd> {
    //aqui creo una referencia a la lista que va  a mostrar
    List<ProdxSuper> listProd=new ArrayList<ProdxSuper>();
    private Context context;
    private View vista;
    //generar constructor

    public AdapterProductos(List<ProdxSuper> listProd, Context context) {
        this.listProd = listProd;
        this.context=context;
    }

    @NonNull
    @Override
    //este metodo enlaza el item con el adaptador es por ello que genero un view
    public ViewHolderProd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produc,null,false);
        return new ViewHolderProd(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderProd holder, int position) {
  holder.AsignarProd(listProd.get(position));
    }

    @Override
    public int getItemCount() {
        return listProd.size();
    }

    public class ViewHolderProd extends RecyclerView.ViewHolder {
        TextView nombreSuper;
        TextView direccion;
        TextView nombre;
        TextView desc;
        TextView precio;
        public ViewHolderProd(@NonNull View itemView) {
            super(itemView);
            nombreSuper=itemView.findViewById(R.id.txtNombreSuper);
            direccion=itemView.findViewById(R.id.txtDireccion);
            nombre=itemView.findViewById(R.id.txtNombre);
            desc=itemView.findViewById(R.id.txtDescripcion);
            precio=itemView.findViewById(R.id.txtPrecio);
        }

        public void AsignarProd(ProdxSuper prodxSuper) {
            nombreSuper.setText(prodxSuper.getSuperNom());
            direccion.setText(prodxSuper.getDireccion());
            nombre.setText(prodxSuper.getNombre());
            desc.setText(prodxSuper.getDescripcion());
            String pre=String.valueOf(prodxSuper.getPrecio());
            precio.setText(pre);
        }
    }
}
