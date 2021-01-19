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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class nearby_super extends Fragment{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txtLabel;
    private String mParam1;
    private String mParam2;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            GoogleMap mapa = googleMap;
            LatLng Libertad = new LatLng(-24.83124422197392, -65.42875512201249);
            mapa.addMarker(new MarkerOptions().position(Libertad).title("Marker in Sydney"));
            mapa.moveCamera(CameraUpdateFactory.newLatLng(Libertad));
            txtLabel.setText("hola");
        }
    };

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
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);}
        txtLabel= (TextView) view.findViewById(R.id.txtLatitud);
        txtLabel.setText("Carina");

    }

  //  @Override
 //   public void onLocationChanged(Location location) {
 //este me dice cuando la ubicacion ha camniado d aqui manda al otro metodo donde este
        //metodo te muestre lo quequeres en el mapa
      //  String txt="Latitud: "+   location.getLatitude()+"\n"+"Longitud: "+location.getLongitude();
      //   txtLabel.setText(txt);
       // mapa(location.getLatitude(),location.getLongitude());
   // }

 //   private void mapa(double latitude, double longitude) {
        //aqui meto en el mapa la latitud y longitu
 //   }

 /**   @Override
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
    }**/

}