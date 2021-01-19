package com.example.myoffers_2;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.appcompat.app.AppCompatActivity;
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

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class Bienvenida extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnLista,btnofertas,btnSuper,btnProduc;
    private String nom;

    public Bienvenida() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Bienvenida newInstance(String param1, String param2) {
        Bienvenida fragment = new Bienvenida();
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
    //aqui se infla la vista
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        return inflater.inflate(R.layout.fragment_bienvenida, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView tv= view.findViewById(R.id.IdUsua);
       nom=BienvenidaArgs.fromBundle(getArguments()).getNomUsuario();
        tv.setText("Bienvenido: "+nom+ " que desea hacer:");
      btnLista=view.findViewById(R.id.bntLista);
      btnofertas= view.findViewById(R.id.btnOfertas);
      btnSuper=view.findViewById(R.id.btnSuper);
      btnProduc=view.findViewById(R.id.btnProductos);
      btnLista.setOnClickListener(this);
      btnofertas.setOnClickListener(this);
      btnSuper.setOnClickListener(this);
      btnProduc.setOnClickListener(this);

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.listaMercaderia); }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOfertas:
                Navigation.findNavController(v).navigate(R.id.ofertasCercas);
                break;
            case R.id.btnSuper:
                Navigation.findNavController(v).navigate(R.id.nearby_super);
                break;
            case R.id.btnProductos:
                BienvenidaDirections.ActionBienvenidaToRegisProducto action= BienvenidaDirections.actionBienvenidaToRegisProducto();
                action.setNomUsuario(nom);
                Navigation.findNavController(v).navigate(action);
                break;
        }
    }
}