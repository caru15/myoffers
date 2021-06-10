package com.example.myoffers_2;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ListaProductos {
    private List<Modelo> Productos=new ArrayList<>();
    private Modelo mod;
    private String dir;
    private AdminBD bd=new AdminBD();
   private AsyncHttpClient conexion=new AsyncHttpClient();
  private  RequestParams params= new RequestParams();

    public ListaProductos() {
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

                try {
                    JSONArray jsonArray=new JSONArray(responseString);
                    for (int i = 0;i < jsonArray.length();i++) {
                        String nom=jsonArray.getJSONObject(i).getString("nombre").trim();
                        String mar=jsonArray.getJSONObject(i).getString("marca").trim();
                        String ima=jsonArray.getJSONObject(i).getString("imagen").trim();
                        String des=jsonArray.getJSONObject(i).getString("descripcion").trim();
                        mod=new Modelo(nom,mar,ima,des);
                       Guardar(mod);
                    }

                }
                catch (Exception e){
                    Log.d("se entro por el catch",responseString);
                    e.printStackTrace();
                }
            }

        });
    }
    public void Guardar(Modelo m){
        Productos.add(m);
    }

    public List<Modelo> getProductos() {
        return Productos;
    }

    public void setProductos(List<Modelo> productos) {
        Productos = productos;
    }
}
