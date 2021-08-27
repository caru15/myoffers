package com.example.myoffers_2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class locationSuper extends Fragment implements OnMapReadyCallback{

double lat;
double lon;
String nom;
private GoogleMap map;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
     //       LatLng sydney = new LatLng(lat, lon);
      //      googleMap.addMarker(new MarkerOptions().position(sydney).title(nom));
     //       googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location_super, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nom=locationSuperArgs.fromBundle(getArguments()).getNombreSup();
        String latitud=locationSuperArgs.fromBundle(getArguments()).getLatitud1();
        String longitud=locationSuperArgs.fromBundle(getArguments()).getLongitud1();
       lat=Double.valueOf(latitud);
         lon=Double.valueOf(longitud);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this::onMapReady);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng sydney = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions().position(sydney).title(nom));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // Asigno un nivel de zoom mas grande el numero se acerca mas la camara'
        CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(17);
        map.animateCamera(ZoomCam);
    }
}