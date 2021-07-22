package com.example.myoffers_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class alta_prod extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public alta_prod() {
        // Required empty public constructor
    }


    public static alta_prod newInstance(String param1, String param2) {
        alta_prod fragment = new alta_prod();
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
        return inflater.inflate(R.layout.fragment_alta_prod, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String idmasNombre=alta_prodArgs.fromBundle(getArguments()).getIdNombre();
        String[] Nom=idmasNombre.split("-");
        EditText nombre=view.findViewById(R.id.Idnom);
        EditText descrip=view.findViewById(R.id.Idescr);
        EditText marca=view.findViewById(R.id.Idmarca);
        EditText canti=view.findViewById(R.id.Idunid);
        EditText imagen=view.findViewById(R.id.Idimag);
        Button Guarda=view.findViewById(R.id.Idguardar);
        //verificarPro()-este metodo buscaria si el producto que esta por cargarse
        //ya existe en la BD
        Guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ingresar ala data base
                alta_prodDirections.ActionAltaProdToNuevaOferta accio=alta_prodDirections.actionAltaProdToNuevaOferta();
                accio.setIdUsuario(Integer.valueOf(Nom[0]));
                accio.setNombre(Nom[1]);
                Navigation.findNavController(view).navigate(accio);
            }
        });
    }
}