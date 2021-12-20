package com.example.myoffers_2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import static android.R.attr.radius;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class nearby_super extends Fragment implements OnMapReadyCallback, LocationListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txtLabel;
    private GoogleMap map;
    private String mParam1;
    private String mParam2;
    private static final int LOCATION_REQUEST_CODE=1;
    private boolean mLocationPermissionGrated= false;
    private LatLng myPlace;
    private View view;
    private LatLng mipos;
    private   List<supermercados> lista= new ArrayList<supermercados>();
    private Location mipos1 = new Location("mipos");
    private int distancia;
    private MarkerOptions markerOptions;
    private MenuInflater inflater;
    private Toolbar toolbar;
    private LocationManager locationManager;
    private AdminBD bd=new AdminBD();
    private supermercados supermercado = new supermercados();
    private String uri;
    private boolean bandera;
    private int identificador;
    private String direccion;
    AsyncHttpClient conexion=new AsyncHttpClient();
    RequestParams params= new RequestParams();
    
    public nearby_super() {
        // Required empty public constructor
    }

    public static nearby_super newInstance(String param1, String param2) {
        nearby_super fragment = new nearby_super();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nearby_super, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.idtoolbar);
        setToolbar(view);
        distancia=20;
       SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);

        try {
            this.locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
            LocationProvider proveedor = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,4,this);
        }catch (SecurityException e){
                e.printStackTrace();}
            }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng Salta = new LatLng(-24.7859, -65.4116);
      //  map.addMarker(new MarkerOptions().position(Libertad).title("Libertad"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Salta));
        // Asigno un nivel de zoom mas grande el numero se acerca mas la camara'
        CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(12);
        map.animateCamera(ZoomCam);
        updateLocationUI();
    }

    private void updateLocationUI(){
        mLocationPermissionGrated= false;
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //aqui solicitmos permiso
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }else{
            mLocationPermissionGrated= true;
            map.setMyLocationEnabled(true);
            getDeviceLocation();
        } }

    private void getDeviceLocation(){
        if (mLocationPermissionGrated){
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            //aqui activo la localizacion y me incluye la ubicacion y el marcado
            map.getUiSettings().setMyLocationButtonEnabled(true);
        } }

        @Override
        public void onLocationChanged(Location location) {
    //este me dice cuando la ubicacion ha cambiado y d aqui manda al otro metodo donde este
          // metodo te muestre lo que queres en el mapa
          mipos= new LatLng(location.getLatitude(),location.getLongitude());
          map.moveCamera(CameraUpdateFactory.newLatLngZoom(mipos,10));
          mapa(location.getLatitude(),location.getLongitude(),distancia);
    }
   //lo que hace este metodo segun la latitud y longitud mia me tira en el mapa los super mas cercanos
    private void mapa(double latitude, double longitude, int distancia) {
        //debo traer todas las latitudes y longitudes de la base de datos y agregarlas al lati
        Location OtraLocation= new Location("super");
        mipos1.setLatitude(latitude);
        mipos1.setLongitude(longitude);
        uri=bd.dirSuper();
        params.put("type","listar");
        params.put("nom","nada");
        params.put("dir","nada");
        params.put("localidad","nada");
        conexion.post(uri, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Error","no se conecto "+responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String respuesta) {
                List<supermercados> lista= new ArrayList<supermercados>();
                Boolean bandera=false;
                try {
                    JSONArray jsonArray= new JSONArray(respuesta);
                    for (int i = 0;i < jsonArray.length();i++){
                        supermercados misuper = new supermercados();
                        misuper.setId(jsonArray.getJSONObject(i).getInt("id_supermercado"));
                        misuper.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                        misuper.setDireccion(jsonArray.getJSONObject(i).getString("direccion"));
                        misuper.setLat(jsonArray.getJSONObject(i).getDouble("latitud"));
                        misuper.setLongitud(jsonArray.getJSONObject(i).getDouble("longitud"));
                        lista.add(misuper);
                    }
                   for (int j=0; j<lista.size();j++){

                       OtraLocation.setLatitude((double)lista.get(j).getLat());
                       OtraLocation.setLongitude((double)lista.get(j).getLongitud());
                       float dis=OtraLocation.distanceTo(mipos1)/1000;
                       Log.d("la diferencia",String.valueOf(dis));
                       if (dis<=distancia){
                        LatLng sp = new LatLng(lista.get(j).getLat(), lista.get(j).getLongitud());
                            String titulo=lista.get(j).getNombre();
                            map.addMarker(new MarkerOptions().position(sp).title(titulo));}
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("ERROR","entramos por el catch "+e.toString());
                }
            }
        });
   }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
     //como se encuentra nuestro proveedor
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
                    } }

    @Override
    public void onProviderEnabled(String provider) {
        //gps activado
    }
    @Override
    public void onProviderDisabled(String provider) {
    //gps desactivado
    }
   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       super.onCreateOptionsMenu(menu, inflater);
       inflater.inflate(R.menu.menu_mapa, menu);
    }

 @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idDistancia:
                return true;
            case R.id.id1:
                Toast.makeText(this.getContext(),"distancia 1",Toast.LENGTH_LONG).show();
                map.clear();
               mapa(mipos1.getLatitude(),mipos1.getLongitude(),1);
                return true;
            case R.id.id2:
                Toast.makeText(this.getContext(),"distancia 2",Toast.LENGTH_LONG).show();
                map.clear();
                mapa(mipos1.getLatitude(),mipos1.getLongitude(),2);
                return true;
            case R.id.id3:
                Toast.makeText(this.getContext(),"distancia 3",Toast.LENGTH_LONG).show();
                map.clear();
                mapa(mipos1.getLatitude(),mipos1.getLongitude(),3);
                return true;
            case R.id.id4:
                Toast.makeText(this.getContext(),"distancia 4",Toast.LENGTH_LONG).show();
                map.clear();
                mapa(mipos1.getLatitude(),mipos1.getLongitude(),20);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Este método inicializa el toolbar y le da opciones de menú
    public void setToolbar(View view){
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.idtoolbar);
        ((AppCompatActivity) this.getActivity()).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_mapa);
    }
}