
package com.example.myoffers_2;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.DialogInterface;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.example.myoffers_2.R.*;

public class ListaMercaderia extends Fragment implements LocationListener,listAdapter.RecycleItemClick, SearchView.OnQueryTextListener{

    private listAdapter myAdapter;
    private ListAdaptador adapter;
   private List<Modelo> myLista;
   private List<Modelo> otraLista=new ArrayList<>();
    private SearchView searchView;
    private RecyclerView recyclerView;
    private Modelo modelo;
    private AdminBD bd=new AdminBD();
    private AsyncHttpClient conexion=new AsyncHttpClient();
    private  RequestParams params= new RequestParams();
    private PopupWindow popupWindow;
    private String k;
    private int kilometros;
    private LinearLayoutManager lm;
    private String latitud;
    private String longitud;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  String dir;
    private String mParam1;
    private String mParam2;
    public ListaMercaderia() {

    }

    public static ListaMercaderia newInstance(String param1, String param2) {
        ListaMercaderia fragment = new ListaMercaderia();
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
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_lista_mercaderia, container, false); }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(id.idlistView);
        Button btnfiltrar = view.findViewById(id.btnFiltrar);
        Button btnBuscar = view.findViewById(id.btnbuscar);
        searchView=view.findViewById(id.sv1);
        kilometros=0;
        llenaLista();
        searchView.setOnQueryTextListener(this);

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationStart(); }
        final FrameLayout frameLayout=view.findViewById(id.fl);

 btnfiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(layout.popup,null);
                Button btnok = customView.findViewById(id.btnOk);
                RadioGroup radioGroup=(RadioGroup) customView.findViewById(id.rdioGr);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        //la opcion elejida la guardo en entero en una variable y la guardo en string en otra
                        switch (checkedId){
                            case  id.rb1:
                                kilometros=1;
                                k=String.valueOf(kilometros);
                                break;
                            case id.rb2:
                                kilometros=2;
                                k=String.valueOf(kilometros);
                                break;
                            case id.rb3:
                                kilometros=3;
                                k=String.valueOf(kilometros);
                                break;
                            case id.rb4:
                                kilometros=15;
                                k=String.valueOf(kilometros);
                                break;
                        } }
                });
                popupWindow = new PopupWindow(customView, 700, 800);
                popupWindow.showAtLocation(frameLayout , Gravity.CENTER, 0, 0);
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss(); }
                }); }
        });
 
    btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadena = "";
               int[] miarray = new int[otraLista.size()];
                for (int i = 0; i < otraLista.size(); i++) {
                    modelo = otraLista.get(i);
                    miarray[i]=modelo.getId();
                cadena=cadena+miarray[i]+"-";}
                Log.d("lo q pasa",cadena+"-"+kilometros+"-"+latitud+"-"+longitud);
                ListaMercaderiaDirections.ActionListaMercaderiaToResultBusqueda action= ListaMercaderiaDirections.actionListaMercaderiaToResultBusqueda(miarray);
               action.setProductos(miarray);
               action.setDistancia(kilometros);
               action.setLatitud(latitud);
               action.setLongitud(longitud);
              Navigation.findNavController(v).navigate(action);
            } });
    }

    private void llenaLista() {
        dir=bd.dirProd();
        params.put("type","listar");
        params.put("nom","nada");
        params.put("desc","nada");
        params.put("marca","nada");
        params.put("cant","nada");
        conexion.post(dir, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Error","no se conecto "+responseString);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                myLista=new ArrayList<>();
                try {
                    JSONArray jsonArray=new JSONArray(responseString);
                    Modelo mod;
                    for (int i = 0;i < jsonArray.length();i++) {
                        int id=jsonArray.getJSONObject(i).getInt("id_producto");
                        String nom=jsonArray.getJSONObject(i).getString("nombre").trim();
                        String mar=jsonArray.getJSONObject(i).getString("marca").trim();
                        String ima=jsonArray.getJSONObject(i).getString("imagen").trim();
                        String des=jsonArray.getJSONObject(i).getString("descripcion").trim();
                        mod=new Modelo(nom,mar,ima,des,id);
                        myLista.add(mod);
                    }
                 //myAdapter.notifyDataSetChanged();
                    llenarAdapter();
                }
                catch (Exception e){
                    Log.d("se entro por el catch",responseString);
                    e.printStackTrace();
                } }
        }); }

    private void llenarAdapter() {
        lm=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);
        myAdapter=new listAdapter(getContext(),myLista, this);
        recyclerView.setAdapter(myAdapter);
    }

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }
    @Override
    public void onLocationChanged(Location location) {
       latitud=String.valueOf(location.getLatitude());
       longitud=String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
    }}

    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            } } }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        recyclerView.setAdapter(myAdapter);
        myAdapter.filter(newText);
        return false;
    }

    @Override
    public void itemClick(Modelo item) {
    Toast toast=Toast.makeText(getContext(),item.getNombre(),Toast.LENGTH_SHORT);
    toast.show();
    otraLista.add(item);
        adapter= new ListAdaptador(otraLista,getContext());
        recyclerView.setAdapter(adapter);
    }
}
