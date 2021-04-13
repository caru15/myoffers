package com.example.myoffers_2;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.Header;

public class ResultBusqueda extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String[] prod;
    private int[] vector;
    private String[] ProdNo;
    private int l;
    private TextView textView1;
    private Boolean band;//esta bandera la pongo para saber si encontro o no el producto en la BD
    private List<productos> listNew= new ArrayList<productos>();
    private int[] distancia;
    private int dist;
   private List<ProdxSuper> myList = new ArrayList<ProdxSuper>();
    private String mParam1;
    private String mParam2;
    private AdminBD bd=new AdminBD();
    private supermercados supermercado = new supermercados();
    private productos producto= new productos();
    private String uri;
    private Distancia midistancia;
    private Location mipos;
    private LocationManager locManager;
    AsyncHttpClient conexion=new AsyncHttpClient();
    RequestParams params= new RequestParams();

    public ResultBusqueda() { }

    public static ResultBusqueda newInstance(String param1, String param2) {
        ResultBusqueda fragment = new ResultBusqueda();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
/**
 * lo que queda hacer es pone dos label uno que te muestre latitud y longitud
 * del movil verifica que te muestre bien los datos
 * y aqui nomas en esta clase en el metodo Buscar calculala distancia de tu pos al super
 * asi vas guardando los supermercados que te sirven 
 */

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
        return inflater.inflate(R.layout.fragment_result_busqueda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView2=view.findViewById(R.id.textView2);
        TextView textView= view.findViewById(R.id.text1);
       textView1=view.findViewById(R.id.prueba);
        prod=ResultBusquedaArgs.fromBundle(getArguments()).getProductos();
        dist=ResultBusquedaArgs.fromBundle(getArguments()).getDistancia();
        textView2.setText(prod[0]);
        textView.setText(Integer.toString(dist));
        Compara(prod,ProdNo);
        Mostrar();

    }

    public void Compara(String[] prod,String[] prodNo){
        l=0;
        uri=bd.dirProd();
        params.put("type","listar");
        params.put("nom","nada");
        params.put("desc","nada");
        params.put("marca","nada");
        params.put("cant","nada");
        conexion.post(uri, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Error","no se conecto "+responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String respuesta) {
                List<productos> lista= new ArrayList<productos>();
                List<productos> listNew= new ArrayList<productos>();
                try {
                    JSONArray jsonArray= new JSONArray(respuesta);
                    for (int i = 0;i < jsonArray.length();i++){
                        productos produc= new productos();
                        produc.setId(jsonArray.getJSONObject(i).getInt("id_producto"));
                        produc.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                        produc.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                        produc.setMarca(jsonArray.getJSONObject(i).getString("marca"));
                        lista.add(produc);}
                      for (int j=0;j<prod.length;j++){
                          String[] pm=prod[j].split("-");
                          String pm1=pm[0].toLowerCase().trim();
                          String pm2=pm[1].toLowerCase().trim();
                          band=false;
                             for (int k=0;k<lista.size();k++){
                                       String p=lista.get(k).getNombre().toLowerCase().trim();
                                       String m=lista.get(k).getMarca().toLowerCase().trim();
                                       productos mypro=lista.get(k);
                                        if (pm1.equalsIgnoreCase(p)){
                                            if (m.equalsIgnoreCase(pm2)){
                                                //aqui guardo este producto junto con su id
                                                listNew.add(mypro);
                                                Log.d("entrams por el true","entramos bien"+listNew.get(0).getId());

                                                band=true;
                                            }else {
                                                if (pm2.isEmpty()){
                                                    //guardo este producto me sirve el id
                                                    listNew.add(mypro);
                                                    band=true;
                                                }
                                            }
                                        } }
                             if (!band){//productos no encontrados guardado en array
                                                       prodNo[l]=prod[j];
                                                       Log.d("dio todo false", "se guardo un producto no encontrado");
                                                            l++;}
                                                        }
                      BuscarSuper(listNew);

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("ERROR","entramos por el catch 1"+e.toString());
                }
            }
        });
    }

    //con los productos de la lista pro busco en la BD y traigo toda la info
    public void BuscarSuper(List<productos> pro){
        int id=0;
        //List<ProdxSuper> myList = new ArrayList<ProdxSuper>();
        uri=bd.dirProdSuper();
       for (int j=0;j<pro.size();j++){
            id=pro.get(j).getId();
            Log.d("id del prod",String.valueOf(id));
            params.put("type","join");
            params.put("super","nada");
            params.put("prod","nada");
            params.put("usuario","nada");
            params.put("pre","nada");
            params.put("oferta","nada");
            params.put("id",id);
            conexion.post(uri, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("Error","no se conecto "+responseString);
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        ProdxSuper produc= new ProdxSuper();
                        JSONArray jsonArray= new JSONArray(responseString);
                        for (int i = 0;i < jsonArray.length();i++){
                            produc.setId(jsonArray.getJSONObject(i).getInt("id_producto"));
                            produc.setNombre(jsonArray.getJSONObject(i).getString("nombreprod"));
                            produc.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                            produc.setSuperNom(jsonArray.getJSONObject(i).getString("nombresuper"));
                            produc.setDireccion(jsonArray.getJSONObject(i).getString("direccion"));
                            produc.setLatitud(jsonArray.getJSONObject(i).getDouble("latitud"));
                            produc.setLongitud(jsonArray.getJSONObject(i).getDouble("longitud"));
                            produc.setId_super(jsonArray.getJSONObject(i).getInt("super_id"));
                            //myList.add(produc);

                            Guardar(produc);

                    } jsonArray = new JSONArray(new ArrayList<String>());
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("ERROR","entramos por el catch del ultimo"+e.toString());
                    }

                }
            });
        }
    }

    private void Guardar(ProdxSuper produc) {
        //antes de guardar el producto me fijo si cumple con las condiciones de distancia
        //si cumple la guarda sino la desecha
        Log.d("adentro","-"+produc.getSuperNom());
        midistancia=new Distancia(produc.getLatitud(),produc.getLongitud(),dist);
        band=false;
        band=midistancia.Buscar();
        Log.d("bandera",String.valueOf(band));
        if (band){
        myList.add(produc);
        Log.d("lo q paso",produc.getSuperNom()+"-"+myList.get(0).getDireccion());
          //  String cadena=myList.get(0).getNombre()+"-"+myList.get(0).getDireccion()+"-";
           // textView1.setText(cadena);
        }
    }
    private void Mostrar(){
       // String cadena="";
        //for(int i=0;i<myList.size();i++){

     //   }

    }
}