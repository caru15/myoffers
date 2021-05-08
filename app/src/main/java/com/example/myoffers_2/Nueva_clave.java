package com.example.myoffers_2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//en este fragmento vas a poner para que ingrese nueva clave porque se va a cargar solo el usuario
//consulta en la base de datos el dni carga el usuario y toma la nueva contraseña
//ingresa ala base de datos y modifica la contrasela segun el id que encontro con ese dni
public class Nueva_clave extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nueva_clave() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Nueva_clave newInstance(String param1, String param2) {
        Nueva_clave fragment = new Nueva_clave();
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
        return inflater.inflate(R.layout.fragment_nueva_clave, container, false);
    }
}