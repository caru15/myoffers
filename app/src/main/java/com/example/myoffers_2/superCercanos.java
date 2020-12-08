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

public class superCercanos extends Fragment implements LocationListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE=1;
    private LocationManager locationManager;
    private Context context;
    private GeoPoint newPoint;
    private MapView map;
    private int counter;
    private boolean restart;
    private IMapController mapController;
    private Marker startMarker;
    private Button btnLocaliza;
    private TextView tvLocation;
    private static final int STORAGE_REQUEST =100;
    private static final int LOCATION_REQUEST=101;
    private MyLocationNewOverlay mLocationOverlay;

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
        this.restart=false;
        context= getContext().getApplicationContext();
        Configuration.getInstance().load(context,PreferenceManager.getDefaultSharedPreferences(context));
        map=(MapView) view.findViewById(R.id.IdMap);
        btnLocaliza=(Button) view.findViewById(R.id.btnLocation);
        tvLocation=(TextView)view.findViewById(R.id.tvLocation);
        this.counter=0;
        btnLocaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });
        this.map.setTileSource(TileSourceFactory.MAPNIK);
        this.map.setBuiltInZoomControls(true);
        this.map.setMultiTouchControls(true);
      // this.newPoint = new GeoPoint(-24.740555,-65.447805);

        this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context),map);
        this.mLocationOverlay.enableMyLocation();
        map.getOverlays().add(this.mLocationOverlay);
       //aqui tenemos acceso al controlador del mapa
       this.mapController=this.map.getController();
        this.mapController.setZoom(16.0);
        //this.mapController.setCenter(this.newPoint);
        requestPermissionsIfNecessary(new String[] {
           //esta linea es para mostrar la ubicacion actual//es necesario para mostrar el mapa
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }
    void getLocation(){
        try {
            Toast.makeText(this.getContext(), "Inicio", Toast.LENGTH_SHORT).show();
            Log.d("GPS::2","getLocation");
            this.locationManager=(LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,4000,5,this);
            map.getOverlays().clear();
            map.invalidate();
            this.restart= true;
        }catch (SecurityException e){
            Log.d("GPS::3","Error getLocation");
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
    public void onLocationChanged(Location location) {
        this.counter=this.counter+1;
        this.tvLocation.setText("Mi Localizacion actual "+this.counter +": Lat: "+location.getLatitude()+" Long: "+location.getLongitude());
        this.newPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
        this.mapController.setCenter(this.newPoint);
        this.mapController.setZoom(15.0);
        startMarker = new Marker(map);
        if (this.restart){
            startMarker.setIcon(getResources().getDrawable(R.drawable.marker_default));
            startMarker.setTitle("Inicio");
            restart= false;
        }else{
            startMarker.setIcon(getResources().getDrawable(R.drawable.ic_baseline_directions_walk_24));
        }
        startMarker.setPosition(this.newPoint);
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