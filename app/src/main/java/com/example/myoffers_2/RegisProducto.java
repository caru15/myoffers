package com.example.myoffers_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;


import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RegisProducto extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String dirProducto;
    private String dirProdxSup;
    private String marca;
    private String dir, uri;
    private String mParam1;
    private String mParam2;
    private AdminBD bd=new AdminBD();
    private   RecyclerView recyclerView;
    AsyncHttpClient conexion=new AsyncHttpClient();
    RequestParams params= new RequestParams();
    private ProdxSuper PS;
    List<ProdxSuper> items= new ArrayList<>();

    public RegisProducto() {
    }

    public static RegisProducto newInstance(String param1, String param2) {
        RegisProducto fragment = new RegisProducto();
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
        return inflater.inflate(R.layout.fragment_regis_producto, container, false);
        //setteo una escucha anonima con el set onclicklistener
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm= new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(lm);
        dir=bd.dirProdSuper();//"prodxsuper.php
        params.put("type","join1");
        params.put("super","nada");
        params.put("prod","nada");
        params.put("usuario","nada");
        params.put("pre","nada");
        params.put("oferta","nada");
        conexion.post(dir, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Error","no se conecto "+responseString);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {

                try {
                   prodSuperAdaptador adapter= new prodSuperAdaptador(items, view.getContext());
                    recyclerView.setAdapter(adapter);
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length();i++) {
                        int id= jsonArray.getJSONObject(i).getInt("id");
                        String nom= jsonArray.getJSONObject(i).getString("nom");
                        int cant= jsonArray.getJSONObject(i).getInt("cantidad");
                        String descripcion =jsonArray.getJSONObject(i).getString("des");
                        String supNombre =jsonArray.getJSONObject(i).getString("super");
                        double pre=jsonArray.getJSONObject(i).getDouble("precio");
                        String imagen=jsonArray.getJSONObject(i).getString("imagen");
                        PS=new ProdxSuper(id,nom,cant,descripcion,supNombre,pre,imagen);
                        PS.setId_prod(jsonArray.getJSONObject(i).getInt("idprod"));
                        marca=jsonArray.getJSONObject(i).getString("marca");
                        items.add(PS);
                        adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ERROR", "entramos por el catch " + e.toString());
                }
            }

        });

        //Recibo parametro del fragment anterior
        String nom=RegisProductoArgs.fromBundle(getArguments()).getNomUsuario();

        //instancio mi boton floating
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Crear nueva Oferta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //inicia Actividad de Insercion y mando el parametro
                RegisProductoDirections.ActionRegisProductoToNuevaOferta action=RegisProductoDirections.actionRegisProductoToNuevaOferta();
             action.setNombre(marca);
                Navigation.findNavController(view).navigate(action);
            }
        });

    }
}