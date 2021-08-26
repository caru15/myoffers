package com.example.myoffers_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;
import android.view.View.OnClickListener;

public class editar_oferta extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
ProdxSuper element;
    private String mParam1;
    private String mParam2;

    private AdminBD bd=new AdminBD();
    private RecyclerView recyclerView;
    AsyncHttpClient conexion=new AsyncHttpClient();
    RequestParams params= new RequestParams();
    private String dir;
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
        ImageButton boton=(ImageButton)view.findViewById(R.id.btnMapa);
        //aqui en vex de pasarle el nombre pasale la marca del producto
        String nom=editar_ofertaArgs.fromBundle(getArguments()).getNombre();
        int pos=editar_ofertaArgs.fromBundle(getArguments()).getPosicion();
        Log.d("este es el producto:",String.valueOf(pos));
        //tit.setText(nom);

        dir=bd.dirProdSuper();//"prodxsuper.php
        params.put("type","join");
        params.put("super","nada");
        params.put("prod","nada");
        params.put("usuario","nada");
        params.put("pre","nada");
        params.put("oferta","nada");
        params.put("id",pos);
        conexion.post(dir, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Error","no se conecto "+responseString);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {

                try {
                    ProdxSuper element=new ProdxSuper();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length();i++) {
                        element.setSuperNom(jsonArray.getJSONObject(i).getString("nombresuper"));
                        element.setDireccion(jsonArray.getJSONObject(i).getString("direccion"));
                        element.setLatitud(jsonArray.getJSONObject(i).getDouble("latitud"));
                        element.setLongitud(jsonArray.getJSONObject(i).getDouble("longitud"));
                        element.setNombre(jsonArray.getJSONObject(i).getString("nombreprod"));
                        element.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                        element.setPrecio(jsonArray.getJSONObject(i).getDouble("precio"));
                        element.setImagen(jsonArray.getJSONObject(i).getString("imagen"));
                        Glide.with(view.getContext()).load(element.getImagen()).into(Imagen);
                        supermercado.setText(element.getSuperNom());
                        marca.setText(nom);
                        tit.setText(element.getNombre());
                        desc.setText(element.getDescripcion());
                        precio.setText(String.valueOf(element.getPrecio()));
                        boton.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                //aqui manda al mapa los datos y demas 
                                Toast.makeText(getContext(), String.valueOf(element.getDireccion()), Toast.LENGTH_LONG)
                                        .show();
                            }
                        });
                    }
                    supermercado.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), String.valueOf(element.getDireccion()), Toast.LENGTH_LONG)
                                    .show();
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ERROR", "entramos por el catch " + e.toString());
                }
            }

        });
    }

}