package com.example.myoffers_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SeleccionProd extends Fragment implements listAdapter.RecycleItemClick, SearchView.OnQueryTextListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private AdminBD bd=new AdminBD();
    private AsyncHttpClient conexion=new AsyncHttpClient();
    private RequestParams params= new RequestParams();
    private String dir;
    private ArrayList<Modelo> Lista_prod= new ArrayList<>();
    private LinearLayoutManager lm;
    private listAdapter myAdapter;
 private int identificador;
 private String nom;
    public SeleccionProd() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SeleccionProd newInstance(String param1, String param2) {
        SeleccionProd fragment = new SeleccionProd();
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
        return inflater.inflate(R.layout.fragment_seleccion_prod, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       recyclerView=view.findViewById(R.id.idViewList);
        Button Agregar = view.findViewById(R.id.btnAgregar);
        searchView=view.findViewById(R.id.idSV);
         identificador=SeleccionProdArgs.fromBundle(getArguments()).getIdentificador();
                 nom=SeleccionProdArgs.fromBundle(getArguments()).getUsuario();
        Lista();
        searchView.setOnQueryTextListener(this);
    }
    private void Lista(){
        dir=bd.dirProd();
        params.put("type","listar");
        params.put("nom","nada");
        params.put("desc","nada");
        params.put("marca","nada");
        params.put("cant","nada");
        conexion.post(dir, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Error","no se conecto "+responseString);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
              Lista_prod=new ArrayList<>();
                try {
                    JSONArray jsonArray=new JSONArray(responseString);
                    Modelo mod;
                    for (int i = 0;i < jsonArray.length();i++) {
                        int id=jsonArray.getJSONObject(i).getInt("id_producto");
                        String nom=jsonArray.getJSONObject(i).getString("nombre").trim();
                        String mar=jsonArray.getJSONObject(i).getString("marca").trim();
                        String ima=jsonArray.getJSONObject(i).getString("imagen").trim();
                        String des=jsonArray.getJSONObject(i).getString("descripcion").trim();
                        mod=new Modelo(nom,mar,ima,des,id);
                        Lista_prod.add(mod);
                    }
                    //myAdapter.notifyDataSetChanged();
                    llenarAdapter();
                }
                catch (Exception e){
                    Log.d("se entro por el catch",responseString);
                    e.printStackTrace();
                } }
        });
    }
    private void llenarAdapter() {
        lm=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);
        myAdapter=new listAdapter(getContext(),Lista_prod,this);
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerView.setAdapter(myAdapter);
        myAdapter.filter(newText);
        return false;
    }

    @Override
    public void itemClick(Modelo item) {
       int id=item.getId();
       String nombre=item.getNombre()+"-"+item.getMarca();
        SeleccionProdDirections.ActionSeleccionProdToNuevaOferta accion=SeleccionProdDirections.actionSeleccionProdToNuevaOferta();
        accion.setIdProd(id);
        accion.setIdUsuario(identificador);
        accion.setNombre(nom);
        Navigation.findNavController(getView()).navigate(accion);
    }
}