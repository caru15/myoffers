package com.example.myoffers_2;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/***En esta clase lo que va son folletos de los supermercados que tiene ofertas***/

public class ofertasCercas extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageButton imagen1;
    private ImageButton imagen2;
    private ImageButton imagen3;
    private ImageButton imagen4;
    private ImageButton imagen5;
    private ImageButton imagen6;
    private String url;
    private String direccion;
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
         imagen1= (ImageButton) view.findViewById(R.id.imageButton2);
        imagen2= (ImageButton)view.findViewById(R.id.imageButton);
        imagen3=(ImageButton) view.findViewById(R.id.imageButton3);
         imagen4=(ImageButton) view.findViewById(R.id.imageButton4);
         imagen5=(ImageButton) view.findViewById(R.id.imageButton5);
        imagen6=(ImageButton) view.findViewById(R.id.imageButton6);
        imagen1.setOnClickListener(this);
        imagen2.setOnClickListener(this);
        imagen3.setOnClickListener(this);
        imagen4.setOnClickListener(this);
        imagen5.setOnClickListener(this);
        imagen6.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton2://changoMas
                int i[]={R.mipmap.chango1,R.mipmap.chango2,R.mipmap.chango3,R.mipmap.chango4,R.mipmap.chango5,R.mipmap.chango6};
                ofertasCercasDirections.ActionOfertasCercasToFolletos actio=ofertasCercasDirections.actionOfertasCercasToFolletos(i);
                actio.setImagenes(i);
                Navigation.findNavController(v).navigate(actio);
                break;
            case R.id.imageButton://carrefour
                int imagene[]={R.mipmap.carrefour1,R.mipmap.carrefour2,R.mipmap.carrefour3,R.mipmap.carrefour4};
                ofertasCercasDirections.ActionOfertasCercasToFolletos action=ofertasCercasDirections.actionOfertasCercasToFolletos(imagene);
                action.setImagenes(imagene);
                Navigation.findNavController(v).navigate(action);
                break;
            case R.id.imageButton3://Libertad

                break;
            case R.id.imageButton4://dia
                 int ima[]={R.mipmap.dia1,R.mipmap.dia2,R.mipmap.dia3,R.mipmap.dia4,R.mipmap.dia5,R.mipmap.dia6,R.mipmap.dia7};
                 ofertasCercasDirections.ActionOfertasCercasToFolletos action4 = ofertasCercasDirections.actionOfertasCercasToFolletos(ima);
                 action4.setImagenes(ima);
                 Navigation.findNavController(v).navigate(action4);
                break;
            case R.id.imageButton5://vea
                int imagenes[]={R.mipmap.vea1,R.mipmap.vea2,R.mipmap.vea3,R.mipmap.vea4,R.mipmap.vea5,R.mipmap.vea6};
                ofertasCercasDirections.ActionOfertasCercasToFolletos action2=ofertasCercasDirections.actionOfertasCercasToFolletos(imagenes);
                action2.setImagenes(imagenes);
                Navigation.findNavController(v).navigate(action2);
                break;
            case R.id.imageButton6://Damesco
                int imag[]={R.mipmap.damesco1,R.mipmap.damesco2,R.mipmap.damesco3,R.mipmap.damesco4};
                ofertasCercasDirections.ActionOfertasCercasToFolletos action6=ofertasCercasDirections.actionOfertasCercasToFolletos(imag);
                action6.setImagenes(imag);
                Navigation.findNavController(v).navigate(action6);
                break;
            default:
                
        }
    }

   /** public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuItem item=menu.add("Ver Mapa");
        MenuItem item1=menu.add("Ingresar Productos");
        MenuItem item2=menu.add("Salir");
        return true;
    }*/
}