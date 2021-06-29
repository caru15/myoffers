package com.example.myoffers_2;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.google.android.gms.location.LocationListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ruta extends Fragment implements LocationListener {
    GoogleMap map;
    Boolean actualposition = true;
    JSONObject jso;
    Double latitudOrigen, longitudOrigen;
    Double latitudDest, longitudDest;
    LatLng dest;
    Toolbar toolbar;
    private int[] prod=new int[2];

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
        }
    };
    private LocationManager locationManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ruta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/**
      toolbar =(Toolbar) view.findViewById(R.id.idtool);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.main);
        ((AppCompatActivity)getContext()).setSupportActionBar(toolbar);*/

        String lat=rutaArgs.fromBundle(getArguments()).getLatitud();
        latitudDest=Double.parseDouble(lat);
        String longi=rutaArgs.fromBundle(getArguments()).getLongitud();
        longitudDest=Double.parseDouble(longi);
       dest= new LatLng(latitudDest,longitudDest);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
        try {
            this.locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
            LocationProvider proveedor = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,4, (android.location.LocationListener) this);
        }catch (SecurityException e){
            e.printStackTrace();}

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        {
            inflater.inflate(R.menu.main, menu);
        }
    }
 /**   @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        prod[0]=0;
        prod[1]=0;
        int i=item.getItemId();
        if (i==R.id.idExit){
        rutaDirections.ActionRutaToResultBusqueda action=rutaDirections.actionRutaToResultBusqueda(prod);
        action.setLongitud("0");
        action.setLatitud("0");
        action.setDistancia(0);
        action.setProductos(prod);
        Navigation.findNavController(getView()).navigate(action);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onLocationChanged(@NonNull Location location) {
        LatLng mipos = new LatLng(location.getLatitude(), location.getLongitude());
       // map.moveCamera(CameraUpdateFactory.newLatLngZoom(mipos,14));
        latitudOrigen=location.getLatitude();
        longitudOrigen=location.getLongitude();
        map.addMarker(new MarkerOptions().position(mipos).title("Estas Aqui"));
        map.addMarker(new MarkerOptions().position(dest).title("Super"));
        CameraPosition cameraPosition= new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(),location.getLongitude()))
                .zoom(17)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        String url="https://maps.googleapis.com/maps/api/directions/json?origin="+latitudOrigen+","+longitudOrigen +"&destination="+latitudDest+",%20"+longitudDest+"&key=AIzaSyBNL9KGx-ir7crVB-j7xMcwaQeYrApllH4";
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jso=new JSONObject(response);
                    dibujarRuta(jso);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void dibujarRuta(JSONObject jso) {
        JSONArray JsonRoutes;
        JSONArray JsonLegs;
        JSONArray JsonSteps;
        try {
            JsonRoutes=jso.getJSONArray("routes");
            for (int i=0;i<JsonRoutes.length();i++){
                JsonLegs=((JSONObject) (JsonRoutes.get(i))).getJSONArray("legs");
                 for (int j=0;j<JsonLegs.length();j++){
                     JsonSteps=((JSONObject)JsonLegs.get(j)).getJSONArray("steps");
                     for (int k=0;k<JsonSteps.length();k++){
                         String polyline=""+((JSONObject)((JSONObject) JsonSteps.get(k)).get("polyline")).get("points");
                         Log.d("end"," "+polyline);
                         List<LatLng> list= PolyUtil.decode(polyline);
                         map.addPolyline(new PolylineOptions().addAll(list).color(R.color.Rojo).width(10));
                     }
                 }
            }
        }catch (JSONException e){

        }
    }

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

    }
    @Override
    public void onProviderDisabled(String provider) {

    }
}