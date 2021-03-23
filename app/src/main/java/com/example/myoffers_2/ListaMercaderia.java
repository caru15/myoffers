package com.example.myoffers_2;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import java.util.ArrayList;
import java.util.List;

import static com.example.myoffers_2.R.*;

public class ListaMercaderia extends Fragment {

    private ArrayList<String> productos;
    private listAdapter myAdapter;
    private int kilometros;
    private List<Modelo> myLista = new ArrayList<>();
    private ListView lvprod;
    private EditText et1, et2;
    private Modelo modelo;
    private PopupWindow popupWindow;
    private String k;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaMercaderia() {
        // Required empty public constructor
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(layout.fragment_lista_mercaderia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvprod = view.findViewById(id.idlistView);
        Button btn = view.findViewById(id.btnAgregar);
        Button btnfiltrar = view.findViewById(id.btnFiltrar);
        Button btnBuscar = view.findViewById(id.btnbuscar);
        et1 = view.findViewById(id.idProd);
        et2 = view.findViewById(id.idMarca);
        kilometros=0;
        final FrameLayout frameLayout=view.findViewById(id.fl);

        myAdapter = new listAdapter(view.getContext(), layout.item_row, myLista);
        lvprod.setAdapter(myAdapter);
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
                                kilometros=4;
                                k=String.valueOf(kilometros);
                                break;
                        }
                    }
                });
                popupWindow = new PopupWindow(customView, 700, 600);
                popupWindow.showAtLocation(frameLayout , Gravity.CENTER, 0, 0);
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss(); }
                });
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelo = new Modelo();
                String prod = et1.getText().toString();
                modelo.setNombre(prod);
                String mar = et2.getText().toString();
                modelo.setMarca(mar);
                modelo.setImagen(mipmap.ic_launcher_round);
                myLista.add(modelo);
                myAdapter.notifyDataSetChanged();
                et1.setText("");
                et2.setText("");
            }
        });
        lvprod.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int posicion = position;
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(view.getContext());
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Desea Eliminar este producto?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        myLista.remove(posicion);
                        myAdapter.notifyDataSetChanged();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                return false;
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String[] miarray = new String[myLista.size()];
                for (int i = 0; i < myLista.size(); i++) {
                    modelo = myLista.get(i);
                    miarray[i]=modelo.getNombre()+'-'+modelo.getMarca();}
                ListaMercaderiaDirections.ActionListaMercaderiaToResultBusqueda action=
                        ListaMercaderiaDirections.actionListaMercaderiaToResultBusqueda(miarray);
               action.setProductos(miarray);
               action.setDistancia(kilometros);
              Navigation.findNavController(v).navigate(action);
            } });
    }}



