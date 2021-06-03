package com.example.myoffers_2;

import android.os.Bundle;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class nueva_oferta extends Fragment {

    private Spinner spinner, spinner2;
    private AdminBD bd=new AdminBD();
    private supermercados super1 = new supermercados();
    private String uri;
    private int identificador;
    private String direccion;
    AsyncHttpClient conexion=new AsyncHttpClient();
    RequestParams params= new RequestParams();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public nueva_oferta() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static nueva_oferta newInstance(String param1, String param2) {
        nueva_oferta fragment = new nueva_oferta();
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
        return inflater.inflate(R.layout.fragment_nueva_oferta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.Idusuario);
        EditText tvprecio=view.findViewById(R.id.Id_precio);
        EditText tvCantidad=view.findViewById(R.id.Id_cant);
        Button guardar= view.findViewById(R.id.IdGuardar);
        spinner= view.findViewById(R.id.spinner_super);
        spinner2=view.findViewById(R.id.spinner_prod);

        String usuario=nueva_ofertaArgs.fromBundle(getArguments()).getNombre();
        List<supermercados> ListaSuper= new ArrayList<supermercados>();
        List<productos> ListPro =new ArrayList<productos>();
        llenar_Spinner(ListaSuper, view);
        llenar_Spinner2(ListPro, view);
        tv.setText(usuario);

        // aqui hago el evento onclick para guardar los datos en la base de datos
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pre = tvprecio.getText().toString();
                String can=tvCantidad.getText().toString();

                int id_super=Devuelve_id(spinner);
                int id_prod=Devuelve_id(spinner2);
                double precio = Double.parseDouble(pre);
                int cantidad = Integer.parseInt(can);
                DevolverIdUsuario(v,usuario,id_super,id_prod,precio,cantidad);
                Navigation.findNavController(v).navigate(R.id.regisProducto);
            }
        });
    }
    public int Devuelve_id(Spinner sp){

        String sup=sp.getSelectedItem().toString();
        String [] vect = sup.split("-");
        int id=Integer.valueOf(vect[0].trim());
        return id;
    }
    public void DevolverIdUsuario(View v,String usuario, int id_super, int id_prod,double precio,int cantidad){

        uri=bd.dirUsuarios();
        params.put("type","buscar");
        params.put("ape","nada");
        params.put("name","nada");
        params.put("usua",usuario);
        params.put("email","nada");
        params.put("password","nada");
        params.put("repu",1);
        conexion.post(uri, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Error","no se conecto "+responseString);
                 int id  =0;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
             try {
                JSONArray jsonArray=new JSONArray(responseString);
                int id=jsonArray.getJSONObject(0).getInt("id_usuario");
                identificador=id;
                 String dir=bd.dirProdSuper();
                 params.put("type","alta");
                 params.put("super",id_super);
                 params.put("prod",id_prod);
                 params.put("usuario",identificador);
                 Log.d("este es el id",String.valueOf(identificador));
                 params.put("pre",precio);
                 params.put("oferta",cantidad);
                 conexion.post(dir, params, new TextHttpResponseHandler() {
                     @Override
                     public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                         Toast toast = Toast.makeText(v.getContext(), "no se conecto", Toast.LENGTH_SHORT);
                         toast.show();
                     }

                     @Override
                     public void onSuccess(int statusCode, Header[] headers, String responseString) {
                         try {
                             JSONObject jsonObject= new JSONObject(responseString);
                             String info=jsonObject.getString("message");
                             Toast toast = Toast.makeText(v.getContext(),info, Toast.LENGTH_SHORT);
                             toast.show();

                         }catch (Exception e){
                             Log.d("aquiii esta el error",responseString);
                             Toast toast = Toast.makeText(v.getContext(), "entro por el catch no se guardo", Toast.LENGTH_SHORT);
                             toast.show();
                         }
                     }
                 });
                 }

             catch (Exception e){
                 e.printStackTrace();
                 Log.d("ERROR","entramos por el catch "+e.toString());
                 int id=0;
             }
            }

        });
    }

    public void llenar_Spinner(List<supermercados> lista, View v){
        uri=bd.dirSuper();
        params.put("type","listar");
        params.put("nom","nada");
        params.put("dir","nada");
        params.put("localidad","nada");
        conexion.post(uri, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                supermercados misuper = new supermercados("nada","nada","nada",12.3,12.3);
                lista.add(misuper);
                Log.d("caru","no cargo"+statusCode);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String response) {

                try {
                    JSONArray jsonArray= new JSONArray(response);
                    for (int i = 0;i < jsonArray.length();i++){
                        supermercados misuper = new supermercados();
                        misuper.setId(jsonArray.getJSONObject(i).getInt("id_supermercado"));
                        misuper.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                        misuper.setDireccion(jsonArray.getJSONObject(i).getString("direccion"));
                        misuper.setLocalidad(jsonArray.getJSONObject(i).getString("localidad"));
                        Log.d("Super",misuper.getNombre());
                        lista.add(misuper);
                    }
                    int pos;
                    String nom;
                    supermercados sup= new supermercados();
                    List<String> sp = new ArrayList<>();
                    for (int i=0;i<lista.size();i++){
                        sup =lista.get(i);
                        pos=sup.getId();
                        nom= String.valueOf(pos)+"-"+ sup.getNombre()+"-"+sup.getDireccion();
                        sp.add(nom);
                    }
                    ArrayAdapter<String> a = new ArrayAdapter<String>(v.getContext(),R.layout.spinner_item_fer,sp);
                    spinner.setAdapter(a);

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("ERROR","entramos por el catch "+e.toString());

                } }

        });

    }
    public void llenar_Spinner2(List<productos> list, View view){
        direccion=bd.dirProd();
        params.put("type","listar");
       params.put("nom","nada");
        params.put("desc","nda");
        params.put("cant",1);
        params.put("marca","nda");
        params.put("imagen",0);
        conexion.post(direccion, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productos pro = new productos("nada","nada","nada",1,1);
                list.add(pro);
                Log.d("caru","no cargo"+statusCode);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("caru","entramos2 "+responseString);
                try {

                    JSONArray jsonA= new JSONArray(responseString);
                    for (int i = 0;i < jsonA.length();i++){
                        productos pro=new productos();
                        pro.setId(jsonA.getJSONObject(i).getInt("id_producto"));
                        pro.setNombre(jsonA.getJSONObject(i).getString("nombre"));
                        pro.setDescripcion(jsonA.getJSONObject(i).getString("descripcion"));
                        pro.setMarca(jsonA.getJSONObject(i).getString("marca"));
                        Log.d("producto",pro.getNombre());

                        pro.setCant_unidad(jsonA.getJSONObject(i).getInt("cant_unidad"));
                        //pro.setImagen(jsonA.getJSONObject(i).getInt("imagen"));
                        //aqui falta settear la imagen
                        list.add(pro);
                    }
                    int pos;
                    String nom;
                    productos producto1= new productos();
                    List<String> sp = new ArrayList<>();
                    for (int i=0;i<list.size();i++){
                        producto1 =list.get(i);
                        pos=producto1.getId();
                        nom= String.valueOf(pos)+"-"+ producto1.getNombre()+"-"+producto1.getDescripcion();

                        sp.add(nom);
                    }
                    ArrayAdapter<String> a = new ArrayAdapter<String>(view.getContext(),R.layout.spinner_item_fer,sp);
                    spinner2.setAdapter(a);

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("ERROR","entramos porel catch2 "+e.toString());

                } }

        });

    }
}