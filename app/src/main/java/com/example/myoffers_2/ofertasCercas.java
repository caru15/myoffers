package com.example.myoffers_2;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/***En esta clase lo que va son folletos de los supermercados que tiene ofertas***/

public class ofertasCercas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ofertasCercas() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ofertasCercas newInstance(String param1, String param2) {
        ofertasCercas fragment = new ofertasCercas();
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
        return inflater.inflate(R.layout.fragment_ofertas_cercas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public void Vea(View v){

    }
    public void Dia(View v){

    }
    public void Libertad(View v){
     }

    public void ChangoMas(View v){

    }
    public void Carrefour(View v){

    }
    public void Damesco(View v){

       // i.putExtra("sitioweb","http://www.damesco.com.ar/ofertas");

    }
   /*** public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuItem item=menu.add("Ver Mapa");
        MenuItem item1=menu.add("Ingresar Productos");
        MenuItem item2=menu.add("Salir");
        return true;
    }**/
}