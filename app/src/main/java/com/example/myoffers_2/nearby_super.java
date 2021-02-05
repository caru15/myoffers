package com.example.myoffers_2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
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
import androidx.appcompat.widget.Toolbar;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class nearby_super extends Fragment implements OnMapReadyCallback, LocationListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txtLabel;
    private GoogleMap map;
    private String mParam1;
    private String mParam2;
    private static final int LOCATION_REQUEST_CODE=1;
    private boolean mLocationPermissionGrated= false;
    private float DEFAULT_ZOOM=14.0f;
    private LatLng myPlace;
    private MarkerOptions markerOptions;
    private MenuInflater inflater;
    private Toolbar toolbar;
    private LocationManager locationManager;
    
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby_super, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.idtoolbar);
        setHasOptionsMenu(true);
        setToolbar(view);
         txtLabel = (TextView) view.findViewById(R.id.txtLatitud);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            this.locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
            LocationProvider proveedor = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,4,this);

        }catch (SecurityException e){
                e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng Libertad = new LatLng(-24.83124422197392, -65.42875512201249);
        map.addMarker(new MarkerOptions().position(Libertad).title("Libertad"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Libertad));
        // Asigno un nivel de zoom
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
        }
    }
    private void getDeviceLocation(){
        if (mLocationPermissionGrated){
            map.getUiSettings().setCompassEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);
            //aqui activo la localizacion y me incluye la ubicacion y el marcado
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }
      @Override
   public void onLocationChanged(Location location) {
 //este me dice cuando la ubicacion ha camniado d aqui manda al otro metodo donde este
        //metodo te muestre lo quequeres en el mapa
        String txt="Latitud: "+   location.getLatitude()+"\n"+"Longitud: "+location.getLongitude();
         txtLabel.setText(txt);
        mapa(location.getLatitude(),location.getLongitude());
    }

    private void mapa(double latitude, double longitude) {
        //aqui meto en el mapa la latitud y longitu
   }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
     //como se encuantra nuestro proveedor
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
   @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       super.onCreateOptionsMenu(menu, inflater);
       inflater.inflate(R.menu.menu_mapa, menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.idSalir:

                return true;
            case R.id.idDistancia:

                return true;
            case R.id.idVistas:

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

    //Este método infla el fragment que quieres que se cargue por defecto
    // dentro del FrameLayout
   // private void setHomeFragment(){
   //     getFragmentManager().beginTransaction()
   //             .replace(R.id.nearby_super, new HomeFragment())
   //             .commit();}
    //opcion de menu, cambiar el tipo de mapa a satelital e hibrido y otras opciones
    //   map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    //map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
}