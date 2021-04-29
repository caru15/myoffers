 package com.example.myoffers_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import cz.msebera.android.httpclient.Header;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Registrar extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String dir;
    private String mParam1;
    private String mParam2;
    private AdminBD bd=new AdminBD();

    public Registrar() {

    }

    public static Registrar newInstance(String param1, String param2) {
        Registrar fragment = new Registrar();
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
        return inflater.inflate(R.layout.fragment_registrar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText Apellido=view.findViewById(R.id.idApellido);
        final EditText Nombre= view.findViewById(R.id.idNombre);
        final EditText Usuario = view.findViewById(R.id.idUsuario);
        final EditText Email = view.findViewById(R.id.idEmail);
        final EditText Password = view.findViewById(R.id.idPassword);
        Button enviar =view.findViewById(R.id.btnGuardar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AsyncHttpClient client = new AsyncHttpClient();
                final RequestParams params = new RequestParams();
                 dir=bd.dirUsuarios();
                String apellido = Apellido.getText().toString();
                String nombre=Nombre.getText().toString();
                String usuario = Usuario.getText().toString();
                String email = Email.getText().toString();
                String contraseña = Password.getText().toString();

                params.put("type","alta");
                params.put("ape", apellido);
                params.put("name", nombre);
                params.put("usua", usuario);
                params.put("email",email);
                params.put("password", contraseña);

                client.post(dir, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(v.getContext() , "No se pudo Ingresar", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            String prueba=array.getJSONObject(0).getString("message");
                            Toast.makeText(v.getContext() ,prueba, Toast.LENGTH_LONG).show();
                            Snackbar.make(v, prueba,Snackbar.LENGTH_LONG).setAction("Action",null).show();

                            }catch (Exception e){
                            e.printStackTrace();
                            Snackbar.make(v, "json no lee",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                            }
                    }
                });
            }
        });
    }
}