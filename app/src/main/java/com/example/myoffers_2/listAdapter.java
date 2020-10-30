package com.example.myoffers_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class listAdapter extends ArrayAdapter<Modelo> {

    private List<Modelo> miLista;
    private Context miContext;
    private int resourceLay;
    private static LayoutInflater inflater = null;

    public listAdapter(@NonNull Context context, int resource, List<Modelo> objects) {
        super(context, resource, objects);
        this.miLista = objects;
        this.miContext= context;
        this.resourceLay=resource;
    }
    //ahora debemos buscar las vistas que vamos a poner en cada fila
    //esto es una vista inflada de los elementos que van a ir en cada fila
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(miContext).inflate(resourceLay, null);

        Modelo modelo = miLista.get(position);
        ImageView imageView = view.findViewById(R.id.idImagen);
        imageView.setImageResource(modelo.getImagen());
        TextView txtView = view.findViewById(R.id.txtNombre);
        txtView.setText(modelo.getNombre());
        TextView txtV2 = view.findViewById(R.id.idMarca);
        txtV2.setText(modelo.getMarca());
    return view;}
}



