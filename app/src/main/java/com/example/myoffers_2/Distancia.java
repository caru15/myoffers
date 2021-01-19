package com.example.myoffers_2;

import android.location.Location;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import android.location.LocationManager;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.Header;


public class Distancia {
    //esta clase tendra un arrayList de objetos de la clase Supermercados
    //con sus respectivos setters y getters un metodo que calcula la
    //distancia entre mi posicion actual y los supermercados cargados en la base de datos
    private ArrayList<supermercados> misSuper= new ArrayList<>();
    private AsyncHttpClient conexion=new AsyncHttpClient();
    private RequestParams params= new RequestParams();
    private String uri;
    private supermercados mysuper;
    AdminBD mybase= new AdminBD();
    private Location location;
    public Distancia() {
        //aqui ingreso ala base de Datos
        this.uri=mybase.dirSuper();
        params.put("type","listar");
        params.put("nom","nada");
        params.put("dir","nada");
        params.put("localidad","nada");
        conexion.post(uri, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                final int error = Log.d("Error", "no se conecto " + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("caru", "entramos " + responseString);
                try {
                    JSONArray jsonArray = new JSONArray(responseString);
                    for (int i = 0; i < jsonArray.length();i++) {
                        //fijate bien que te devuelva un array y que los nombre coincidan con el campo q te devuelve el php
                        int id= jsonArray.getJSONObject(i).getInt("id");
                        String nombre=jsonArray.getJSONObject(i).getString("nombre");
                       String direccion=jsonArray.getJSONObject(i).getString("direccion");
                       String localidad=jsonArray.getJSONObject(i).getString("localidad");
                       Double Latitud=jsonArray.getJSONObject(i).getDouble("latitud");
                       Double Longitud=jsonArray.getJSONObject(i).getDouble("longitud");
                       mysuper= new supermercados(nombre,direccion,localidad,Latitud,Longitud);
                       misSuper.add(mysuper);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("ERROR", "entramos por el catch " + e.toString());
                }
            }
        });
    }

    public ArrayList<supermercados> getMisSuper() {
        return misSuper;
    }

    public void setMisSuper(ArrayList<supermercados> misSuper) {
        this.misSuper = misSuper;
    }

    public void CalcularDistancia(ArrayList<supermercados> elejidos, Double miLat,Double miLong,int distancia ){
        //este metodo lo que hace es recibir un array vacio y devolver un array con todos los super cercanos que cumplan
        //con la distancia solicitada en kilometros, que tambien es un parametro
        Double latitud=0.0;
        Double longitud=0.0;
        float distan=(float) distancia;

    Location location= new Location("miLugar");
    location.setLatitude(miLat);
    location.setLongitude(miLong);

 Location location1= new Location("otroLugar");
 for (int i=0;i<misSuper.size();i++){
         latitud=misSuper.get(i).getLat();
         longitud=misSuper.get(i).getLongitud();
         location1.setLatitude(latitud);
         location1.setLongitude(longitud);
    float dis=location.distanceTo(location1);
        if (dis<=distan){
            elejidos.add(misSuper.get(i)); }
 } }
}
