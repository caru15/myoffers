package com.example.myoffers_2;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

import static android.widget.Toast.*;
import static com.example.myoffers_2.R.color.colorAccent;
import static com.example.myoffers_2.R.color.colorPrimary;
import static com.example.myoffers_2.R.color.colorPrimaryDark;


public class Inicio extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AdminBD bd=new AdminBD();
    private String dir;
    public Inicio() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Inicio newInstance(String param1, String param2) {
        Inicio fragment = new Inicio();
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
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText usuario= view.findViewById(R.id.username);
        final EditText contraseña= view.findViewById(R.id.password);
        Button Submit=view.findViewById(R.id.btnLogin);
        Button Register= view.findViewById(R.id.btnRegister);
        final Button Olvide= view.findViewById(R.id.btnRecup);
        final String a ="admin";
        final String b= "admin";
         dir=bd.dirLogin();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if ((usuario.getText().toString().equals(a)) && (contraseña.getText().toString().equals(b))){
                    InicioDirections.ActionInicioToBienvenida action= InicioDirections.actionInicioToBienvenida();
                    action.setNomUsuario(a);
                    action.setIdCliente(0);
                    Navigation.findNavController(view).navigate(action);
                }
                if ((usuario.getText().toString().length() == 0) || (contraseña.getText().toString().length() == 0)) {
                    Toast.makeText(v.getContext() , "Ingrese Usuario", Toast.LENGTH_LONG).show();
                }else{
                final AsyncHttpClient client = new AsyncHttpClient();
                final RequestParams params = new RequestParams();

                final String user = usuario.getText().toString();
                final String login=contraseña.getText().toString();
                params.put("username", user);
                params.put("password", login);
                client.post(dir, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Snackbar.make(v, "algo salio mal",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    }
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Snackbar.make(v, "entrams",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                        try {
                            JSONObject jsonObject= new JSONObject(responseString);
                            String prueba=jsonObject.getString("usuario");
                            int id=jsonObject.getInt("id_usua");
                            Snackbar.make(v, prueba,Snackbar.LENGTH_LONG).setAction("Action",null).show();

                            InicioDirections.ActionInicioToBienvenida action= InicioDirections.actionInicioToBienvenida();
                            action.setNomUsuario(prueba);
                            action.setIdCliente(id);
                            Navigation.findNavController(view).navigate(action);

                             }catch (Exception e){
                              e.printStackTrace();
                              Snackbar.make(v, "json no lee",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                                                  } }
                }); }}
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Navigation.findNavController(v).navigate(R.id.registrar);
            }
        });
       Olvide.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Olvide.setTextColor(colorAccent);
                Navigation.findNavController(v).navigate(R.id.recupClave);
            }
        }); }
}