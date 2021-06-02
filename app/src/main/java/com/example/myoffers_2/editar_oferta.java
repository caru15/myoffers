package com.example.myoffers_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class editar_oferta extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public editar_oferta() {
        // Required empty public constructor
    }

    public static editar_oferta newInstance(String param1, String param2) {
        editar_oferta fragment = new editar_oferta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editar_oferta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView Imagen=(ImageView) view.findViewById(R.id.cabecera);
        TextView tit=(TextView)view.findViewById(R.id.Titulo);
        TextView supermercado=(TextView)view.findViewById(R.id.NombreLabel);
        TextView marca=(TextView)view.findViewById(R.id.MarcaLabel);
        TextView desc=(TextView)view.findViewById(R.id.DescLabel);
        TextView precio=(TextView)view.findViewById(R.id.PrecioLabel);

        String nom=editar_ofertaArgs.fromBundle(getArguments()).getNombre();
        int pos=editar_ofertaArgs.fromBundle(getArguments()).getPosicion();
        Log.d("este es el producto:",String.valueOf(pos));
        tit.setText(nom);
      //aqui falta ingresar ala base de datos obtener la imagen y los demas datos para mostarr en los text
    }
}