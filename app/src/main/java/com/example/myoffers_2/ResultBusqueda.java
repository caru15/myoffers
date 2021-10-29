package com.example.myoffers_2;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.Header;

import static androidx.core.content.ContextCompat.getSystemService;

public class ResultBusqueda extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int[] prod;//recibe los id de los prod cargados en la pantalla anterior
    private int[] vector;
    private Double miLatitud;
    private Double milongitud;
    private TextView textView1;
    private TextView textView;
    private String lati;
    private String longi;
    private Boolean band;//esta bandera la pongo para saber si encontro o no el producto en la BD
    private List<productos> listNew= new ArrayList<productos>();
    private int[] distancia;
    private int dist;
    private float midis;
    private Location mipos = new Location("mipos");;
   private List<ProdxSuper> myList = new ArrayList<>();
   private List<ProdxSuper> myList2=new ArrayList<ProdxSuper>();//aqui se guardan los productos que no cumplen con la distancia pedida
    private String mParam1;
    private String mParam2;
    private AdminBD bd=new AdminBD();
    private supermercados supermercado = new supermercados();
    private productos producto= new productos();
    private AdapterProductos adapterProductos;
    private String uri;
    RecyclerView recycler;
    private RecyclerView.LayoutManager layoutManager;
    private LocationManager locManager;
    private Location loc;
    private Button btn;
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
        } }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_busqueda, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView2=view.findViewById(R.id.text3);
        btn=view.findViewById(R.id.btnRuta);
        recycler=(RecyclerView)view.findViewById(R.id.RecView);
        layoutManager=new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(layoutManager);
        prod=ResultBusquedaArgs.fromBundle(getArguments()).getProductos();//RECIBE LOS ID DE LOS PRODUCTOS CARGADOS EN LA PANTALLA ANTERIOR
        dist=ResultBusquedaArgs.fromBundle(getArguments()).getDistancia();
        lati=ResultBusquedaArgs.fromBundle(getArguments()).getLatitud();
        longi=ResultBusquedaArgs.fromBundle(getArguments()).getLongitud();
        miLatitud=Double.parseDouble(lati);
        milongitud=Double.parseDouble(longi);
        mipos.setLatitude(miLatitud);
        mipos.setLongitude(milongitud);
        textView2.setText("Lista de Supermercados a "+dist+"km de distancia");
        BuscarSuper(prod);

   //CON ESTE BOTON SE VE LA RUTA COMPLETA A SEGUIR
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultBusquedaDirections.ActionResultBusquedaToRuta action=ResultBusquedaDirections.actionResultBusquedaToRuta(vector);
                action.setLatitud("0");
                action.setMisSuper(vector);
                action.setLongitud("0");
                Navigation.findNavController(v).navigate(action);
            }
        });
    }
    //con los productos de la lista pro busco en la BD y traigo toda la info
    public void BuscarSuper(int[] pro){
        int id=0;
        uri=bd.dirProdSuper();
       for (int j=0;j<pro.length;j++){
            params.put("id",pro[j]);}
            params.put("type","join");
            params.put("super","nada");
            params.put("prod","nada");
            params.put("usuario","nada");
            params.put("pre","nada");
            params.put("oferta","nada");
           // params.put("id",pro);
            params.put("dist",dist);
            params.put("latitu",miLatitud);
            params.put("longitu",milongitud);
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
                            ProdxSuper produc=new ProdxSuper();
                            produc.setId(jsonArray.getJSONObject(i).getInt("id_producto"));
                            produc.setNombre(jsonArray.getJSONObject(i).getString("nombreprod"));
                            produc.setDescripcion(jsonArray.getJSONObject(i).getString("descripcion"));
                            produc.setSuperNom(jsonArray.getJSONObject(i).getString("nombresuper"));
                            produc.setDireccion(jsonArray.getJSONObject(i).getString("direccion"));
                            produc.setLatitud(jsonArray.getJSONObject(i).getDouble("latitud"));
                            produc.setLongitud(jsonArray.getJSONObject(i).getDouble("longitud"));
                            produc.setId_super(jsonArray.getJSONObject(i).getInt("super_id"));
                            produc.setPrecio(jsonArray.getJSONObject(i).getDouble("precio"));
                            Double distancia_2=jsonArray.getJSONObject(i).getDouble("distancia");
                            Float distancia_1=distancia_2.floatValue();
                            produc.setDistancia(distancia_1);
                            myList.add(produc);
                          //  OrdenarPorDistancia(myList);
                            }
                       // ordenaArray(myList);
                       Mostrar(myList);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Log.d("ERROR","entramos por el catch del ultimo"+e.toString());
                    } }
            });
       //}
    }
/*
    public Boolean Guardar(ProdxSuper produc) {
        Log.d("adentro","-"+produc.getSuperNom());
               Location OtraLocation= new Location("super");
               OtraLocation.setLatitude(produc.getLatitud());
               OtraLocation.setLongitude(produc.getLongitud());
               //1 km=1000 mtrs
               midis=(OtraLocation.distanceTo(mipos)/1000);
               Log.d("la diferencia",String.valueOf(midis));
               produc.setDistancia(midis);
               if (midis<=dist){
                   Log.d("lo q paso",produc.getSuperNom()+"-"+produc.getDireccion());
                   return true;
               }
                 else{
                   return false;
                    }
        }*/

   public void Mostrar(List<ProdxSuper> result){
      adapterProductos = new AdapterProductos(result, this.getContext());
       recycler.setAdapter(adapterProductos);
       recycler.setHasFixedSize(true);
       adapterProductos.notifyDataSetChanged();
       DividerItemDecoration myDivider = new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL);
       myDivider.setDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.cutm_dvdr));
       recycler.addItemDecoration(myDivider );
    }
    private void OrdenarPorDistancia(List<ProdxSuper> myList) {
   //tam tiene la cantidad de elementos
//hace sto llenando directamente al vector
        //ala laista dejala tal cual te al amandan
        int tam=myList.size();
        ProdxSuper aux=new ProdxSuper();
        if (tam>1){
        aux=myList.get(tam-1);
        for (int i=0;i<tam-1;i++){
              if (myList.get(i).getId_super()==aux.getId_super()){

              }else{
                  if (myList.get(i).getDistancia()>aux.getDistancia()){

                      for (int j=i;j<tam-1;j++){
                          myList.add(myList.get(j));
                      }
                      myList.add(i,aux);
                  } } }
        }
   }
        private void ordenaArray(List<ProdxSuper> lista_1){
       int tam=lista_1.size();
       vector=new int[tam];
        for (int j=0;j<tam;j++){
            vector[j]=lista_1.get(j).getId_super();
        }
    }
}