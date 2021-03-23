package com.example.myoffers_2;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResultBusqueda extends Fragment implements LocationListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String[] prod;
    private int[] distancia;
    private int dist;
    private String mParam1;
    private String mParam2;

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
        prod=ResultBusquedaArgs.fromBundle(getArguments()).getProductos();
        dist=ResultBusquedaArgs.fromBundle(getArguments()).getDistancia();
        textView2.setText(prod[1]);
        BuscarSuper(dist,prod);

    }
    //este metodo lo que hace es tomar la distancia que le pedi y todos los productos y voy buscando
    //en la base de datos los productos en oferta tomando la direccion en donde estan
    //y calculando la distancia hasta mi posicion actual
    public void BuscarSuper(int ki,String[] productos){
        int j=0;
        for (int i=0;i<productos.length;i++){

        }

    }

    /**
    Este metodo se ejecuta cuando el GPS recibe nuevas coordenadas
     */
    @Override
    public void onLocationChanged(Location location) {
   double latitud=location.getLatitude();
   double longitud=location.getLongitude();
    }

    /**
     * This callback will never be invoked and providers can be considers as always in the
     * {@link LocationProvider#AVAILABLE} state.
     *
     * @param provider
     * @param status
     * @param extras
     * @deprecated This callback will never be invoked.
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    @Override
    public void onProviderEnabled(String provider) {
     //gps activado
    }

    @Override
    public void onProviderDisabled(String provider) {
  //gps desactivado
    }
}