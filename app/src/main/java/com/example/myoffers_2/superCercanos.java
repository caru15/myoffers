package com.example.myoffers_2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

import org.osmdroid.LocationListenerProxy;

import static org.osmdroid.tileprovider.tilesource.TileSourceFactory.DEFAULT_TILE_SOURCE;

public class superCercanos extends Fragment implements LocationListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private static final int STORAGE_REQUEST = 100;
    private static final int LOCATION_REQUEST = 101;
    private LocationManager locationManager;
    private Context context;
    private GeoPoint newPoint;
    private MapView map;
    private Location LOCALIDAD;
    private int counter;
    private IMapController mapController;
    private Marker startMarker;
    private Button btnLocaliza;
    private TextView tvLocation;
    private MyLocationNewOverlay mLocationOverlay;
    private ArrayList<supermercados> misSuper;

    public superCercanos() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static superCercanos newInstance(String param1, String param2) {
        superCercanos fragment = new superCercanos();
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
        return inflater.inflate(R.layout.fragment_super_cercanos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext().getApplicationContext();
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        map = (MapView) view.findViewById(R.id.IdMap);
        getLocation();
        this.map.setMultiTouchControls(true);
        map.setBuiltInZoomControls(true);
        map.setTileSource(TileSourceFactory.BASE_OVERLAY_NL);
        newPoint= new GeoPoint(-24.788319588401357, -65.41088780441387);
        this.mapController = this.map.getController();
        this.mapController.setZoom(15.0);
        //aqui tenemos acceso al controlador del mapa
        requestPermissionsIfNecessary(new String[] {
                //esta linea es para mostrar la ubicacion actual//es necesario para mostrar el mapa
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE});

    }

    private void getLocation() {
        try {
            //aqui obtengo el proveedor del servicio
            locationManager=(LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            //este metodo notifico los cambio de posicion
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,8000,100,this);
            map.getOverlays().clear();
            map.invalidate();
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }
    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {

            if (ContextCompat.checkSelfPermission(this.getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {

            ActivityCompat.requestPermissions((Activity) this.getContext(), permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
    @Override
    //esto se activará cada vez que se obtenga una nueva posición
    public void onLocationChanged(Location location) {
            this.newPoint=new GeoPoint(location.getLatitude(),location.getLongitude());
            startMarker= new Marker(map);
            mapController.setCenter(newPoint);
            startMarker.setTitle("Inicio");
            startMarker.setIcon(getResources().getDrawable(R.drawable.marker_default));
            startMarker.setPosition(newPoint);
            startMarker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);
            this.map.getOverlays().add(startMarker);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    Toast.makeText(this.context,"habilite gps e internet", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i =0;i<grantResults.length;i++){
            permissionsToRequest.add(permissions[i]);
        } if (permissionsToRequest.size()>0){
            ActivityCompat.requestPermissions((Activity) context,permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
}