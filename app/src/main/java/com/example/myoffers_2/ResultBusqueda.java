package com.example.myoffers_2;

import android.location.Location;
import android.location.LocationListener;
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

public class ResultBusqueda extends Fragment implements LocationListener {

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
    private String mParam1;
    private String mParam2;
    private AdminBD bd=new AdminBD();
    private supermercados supermercado = new supermercados();
    private productos producto= new productos();
    private String uri;
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
        //fijate bien el tema de la localizacion que te tire bien porque faltan agregar
        //lo permisos de geolocalizacion
        BuscarSuperCerca(dist,prod);

    }
    //este metodo lo que hace es tomar la distancia que le pedi y todos los productos y voy buscando
    //en la base de datos los productos en oferta tomando la direccion en donde estan
    //y calculando la distancia hasta mi posicion actual
    public void BuscarSuperCerca(int ki,String[] productos){

    }
    /**
    Este metodo se ejecuta cuando el GPS recibe nuevas coordenadas
     */
    @Override
    public void onLocationChanged(Location location) {
   double latitud=location.getLatitude();
   double longitud=location.getLongitude();
    }

    /** este metodo me avisa como se encuentra mi proveedor de internet*/
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status){
            case LocationProvider.AVAILABLE:
                Log.d("debug","Esta en servicio");
                //esta en el servicio
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug","Esta fuera de servicio");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug","ESTA TEMPORALMENTE FUERA DE SERVICIO");
                break;
        }
    }
    @Override
    public void onProviderEnabled(String provider) {
     //gps activado
    }
    @Override
    public void onProviderDisabled(String provider) {
  //gps desactivado
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
                                               // Meter(mypro);
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
                    Log.d("ERROR","entramos por el catch "+e.toString());
                }
            }
        });
    }


    //con los productos de la lista pro busco en la BD y traigo toda la info
    public void BuscarSuper(List<productos> pro){
      //  for (int j=0;j<pro.size();j++){
        //    Log.d("entro",pro.get(j).getNombre()+"-"+pro.get(j).getMarca());}
        int i=0;
        int[] vector=new int[3];
        List<ProdxSuper> myList = new ArrayList<ProdxSuper>();
        uri=bd.dirProdSuper();
        while(i<pro.size()) {
            vector= new int[]{0, 0, 0};
            vector[0]=pro.get(i).getId();
            i=i+1;
            vector[1]=pro.get(i).getId();
            i=i+1;
            vector[2]=pro.get(i).getId();
            i=i+1;
            params.put("type","join");
            params.put("super","nada");
            params.put("prod","nada");
            params.put("usuario","nada");
            params.put("pre","nada");
            params.put("oferta","nada");
            params.put("myarray",vector);
            conexion.post(uri, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.d("Error","no se conecto "+responseString);
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    try {
                        JSONArray jsonArray= new JSONArray(responseString);
                        for (int i = 0;i < jsonArray.length();i++){
                            ProdxSuper produc= new ProdxSuper();
                            produc.setId(jsonArray.getJSONObject(i).getInt("id_producto"));
                            produc.setNombre(jsonArray.getJSONObject(i).getString("nombreProd"));
                            produc.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                            produc.setSuperNom(jsonArray.getJSONObject(i).getString("nombresuper"));
                            produc.setDireccion(jsonArray.getJSONObject(i).getString("direccion"));
                            produc.setLatitud(jsonArray.getJSONObject(i).getDouble("latitud"));
                            produc.setLongitud(jsonArray.getJSONObject(i).getDouble("longitud"));
                            produc.setId_super(jsonArray.getJSONObject(i).getInt("super_id"));
                            myList.add(produc);}
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("ERROR","entramos por el catch "+e.toString());
                    }

                }
            });
        }
        //fijate porque no trae losdatos de la base de datos como q el array entra vacio sin datos
        //fijate que el array pro traiga datos y los cargue bien
         Log.d("mercaderia",myList.get(1).getNombre());
    }
}