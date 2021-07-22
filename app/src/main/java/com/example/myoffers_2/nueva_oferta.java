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
import android.widget.ImageButton;
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
    private String usuario;
    private int identificador;
    private String direccion;
    private int id_prod;
    AsyncHttpClient conexion=new AsyncHttpClient();
    RequestParams params= new RequestParams();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int identi;
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
        EditText producto=view.findViewById(R.id.IdProducto);
        EditText tvprecio=view.findViewById(R.id.Id_precio);
        EditText tvCantidad=view.findViewById(R.id.Id_cant);
        Button guardar= view.findViewById(R.id.IdGuardar);
        ImageButton buscar=view.findViewById(R.id.btnBuscar);
        spinner= view.findViewById(R.id.spinner_super);
        Bundle bundle=new Bundle();
        usuario=nueva_ofertaArgs.fromBundle(getArguments()).getNombre();
         identi=nueva_ofertaArgs.fromBundle(getArguments()).getIdUsuario();
        String prod=nueva_ofertaArgs.fromBundle(getArguments()).getIdProd();
        if (!prod.equals("nada")){
            String[] pro=prod.split("-");
            id_prod=Integer.valueOf(pro[0]);
            producto.setText(pro[1]+"-"+pro[2]);
        }

        List<supermercados> ListaSuper= new ArrayList<supermercados>();
        List<productos> ListPro =new ArrayList<productos>();
        llenar_Spinner(ListaSuper, view);

        tv.setText(usuario+", estas Ingresando Nueva Oferta");
      buscar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              nueva_ofertaDirections.ActionNuevaOfertaToSeleccionProd accion=nueva_ofertaDirections.actionNuevaOfertaToSeleccionProd();
              accion.setIdentificador(identi);
              accion.setUsuario(usuario);
              Navigation.findNavController(v).navigate(accion);
          }
      });
        // aqui hago el evento onclick para guardar los datos en la base de datos
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pre = tvprecio.getText().toString();
                String can=tvCantidad.getText().toString();

                int id_super=Devuelve_id(spinner);
                double precio = Double.parseDouble(pre);
                int cantidad = Integer.parseInt(can);
                GuardarProd(v,usuario,identi,id_super,id_prod,precio,cantidad);
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
    public void GuardarProd(View v,String usuario,int identificador, int id_super, int id_prod,double precio,int cantidad){
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
                    toast.show(); } }
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
}