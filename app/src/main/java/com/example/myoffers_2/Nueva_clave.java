package com.example.myoffers_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Nueva_clave extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String dir;
    private AdminBD bd= new AdminBD();
    private String mParam1;
    private String mParam2;
    final AsyncHttpClient client = new AsyncHttpClient();
    final RequestParams params = new RequestParams();

    public Nueva_clave() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Nueva_clave newInstance(String param1, String param2) {
        Nueva_clave fragment = new Nueva_clave();
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
        return inflater.inflate(R.layout.fragment_nueva_clave, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dir=bd.correo();
        TextView txt1=view.findViewById(R.id.txtTitulo);
        TextView txt2=view.findViewById(R.id.txtMensaje);
        Button btn=view.findViewById(R.id.btnRecuperar);

        String nombre=Nueva_claveArgs.fromBundle(getArguments()).getNombre();
        String Usuario=Nueva_claveArgs.fromBundle(getArguments()).getUsuario();
        String correo=Nueva_claveArgs.fromBundle(getArguments()).getCorreo();
        int contrase=Nueva_claveArgs.fromBundle(getArguments()).getPassword();
        txt1.setText("Hola "+nombre);
        params.put("usua",Usuario);
        params.put("clave",contrase);
        params.put("nom",nombre);
        params.put("correo",correo);

        client.post(dir, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Snackbar.make(view, "algo salio mal",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Snackbar.make(view, "entrams",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                try {
                    txt2.setText("Verifique su correo se le envio su Usuario y Contraseña");
                    Log.d("see envio",responseString);
                   btn.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Navigation.findNavController(v).navigate(R.id.inicio);
                       }
                   });

                }catch (Exception e){
                    e.printStackTrace();
                    txt2.setText("Error, vaya para atras e intente nuevamente");
                    Snackbar.make(view, "json no lee",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                } }
        });


                }
}