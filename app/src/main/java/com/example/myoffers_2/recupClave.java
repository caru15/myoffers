package com.example.myoffers_2;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class recupClave extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String dir;
    private AdminBD bd= new AdminBD();
    final AsyncHttpClient client = new AsyncHttpClient();
    final RequestParams params = new RequestParams();
    private Usuarios usuarios=new Usuarios();

    public recupClave() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static recupClave newInstance(String param1, String param2) {
        recupClave fragment = new recupClave();
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
        return inflater.inflate(R.layout.fragment_recup_clave, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText dni=view.findViewById(R.id.txtDni1);
        Button btn=view.findViewById(R.id.btnOK1);

        dir=bd.dirUsuarios();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doc= dni.getText().toString();

        params.put("type", "listar");
        params.put("ape","nada");
        params.put("name","nada");
        params.put("docu",doc);
        params.put("usua","nada");
        params.put("email","nada");
        params.put("password","nada");
        params.put("repu",0);

        client.post(dir, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Snackbar.make(view, "algo salio mal",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Snackbar.make(view, "entrams",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                try {
                    JSONArray jsonArray= new JSONArray(responseString);

                    for (int i=0;i < jsonArray.length();i++) {
                        usuarios.setUsuario(jsonArray.getJSONObject(i).getString("usuario"));
                        usuarios.setNombre(jsonArray.getJSONObject(i).getString("nombre"));
                        usuarios.setPassword(jsonArray.getJSONObject(i).getInt("password"));
                        usuarios.setEmail(jsonArray.getJSONObject(i).getString("email"));
                        Log.d("lo q recupera", usuarios.getEmail()+'-'+usuarios.getPassword());
                        recupClaveDirections.ActionRecupClaveToNuevaClave accion=recupClaveDirections.actionRecupClaveToNuevaClave();
                        accion.setUsuario(usuarios.getUsuario());
                        accion.setNombre(usuarios.getNombre());
                        accion.setPassword(usuarios.getPassword());
                        accion.setCorreo(usuarios.getEmail());
                        Navigation.findNavController(view).navigate(accion);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Snackbar.make(view, "json no lee",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                } }
        });
            }
        });
    }
}