package com.example.myoffers_2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import android.location.LocationManager;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.Header;


public class Distancia implements LocationListener, ActivityCompat.OnRequestPermissionsResultCallback {

    //atributos
    private Double OtraLat;
    private Double OtraLong;
    private float dist;
    private Location mipos;
    //private LocationManager locManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);;

    //constructores
    public Distancia(Double lat,Double lon, int distancia) {
        this.OtraLat=lat;
        this.OtraLong=lon;
        //lo paso a metros
        this.dist=(float)distancia*1000;
       }
    public Distancia(){}

   //metodos
    @Override
    public void onLocationChanged(Location location) {
        double latitud=location.getLatitude();
        double longitud=location.getLongitude();
         mipos.setLongitude(longitud);
         mipos.setLatitude(latitud);
         Log.d("mi posicion",String.valueOf(mipos.getLatitude())+"-"+String.valueOf(mipos.getLongitude()));
        //Buscar(latitud,longitud);
    }

   public Boolean Buscar() {
        Boolean band=false;
        float dis;
        onLocationChanged(mipos);
            Location OtraLocation= new Location("super");
            OtraLocation.setLatitude(OtraLat);
            OtraLocation.setLongitude(OtraLong);
            //1 km=1000 mtrs
            dis=OtraLocation.distanceTo(mipos);
            Log.d("la diferencia",String.valueOf(dis));
            if (dis<=dist){band=true;}
        return band;
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
   //GPS Habilitado
    }
    @Override
    public void onProviderDisabled(String provider) {
   //GPS Deshabilitado
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }
}
