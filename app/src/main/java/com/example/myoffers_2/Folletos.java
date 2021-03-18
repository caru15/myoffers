  package com.example.myoffers_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.hololo.tutorial.library.TutorialActivity;

import static android.R.anim.slide_in_left;
import static android.R.anim.slide_out_right;

public class Folletos extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String dir;
    private int i;
    private String mParam1;
    private String mParam2;
    private WebView wv1;
    private ViewFlipper viewFlipper;
    private Boolean bandera;
    public Folletos() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Folletos newInstance(String param1, String param2) {
        Folletos fragment = new Folletos();
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
        return inflater.inflate(R.layout.fragment_folletos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //int imagenes[]={R.drawable.vea1,R.drawable.vea2,R.drawable.vea3,R.drawable.vea4,R.drawable.vea5};
        int imagenes[]=FolletosArgs.fromBundle(getArguments()).getImagenes();
        viewFlipper = (ViewFlipper)view.findViewById(R.id.myflipper);
        ImageButton btn1=(ImageButton)view.findViewById(R.id.btnAnterior);
        btn1.setOnClickListener(this);
        i=0;
        //hago un boton start y stop
        bandera=true;
        while (bandera && i<imagenes.length){
            FlipperImagen(imagenes[i]);
            i=i+1;
        }
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bandera){
                btn1.setBackgroundResource(android.R.drawable.ic_media_play);
                viewFlipper.stopFlipping();
                bandera=false;
                }else{
                    btn1.setBackgroundResource(android.R.drawable.ic_media_pause);
                    viewFlipper.startFlipping();
                    bandera=true;
                }
            }
        });

    }

    public void FlipperImagen(int imagen){
    //aqui corre el flipper
        ImageView ima= new ImageView(this.getContext());
        ima.setBackgroundResource(imagen);
        viewFlipper.addView(ima);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();
        viewFlipper.setInAnimation(this.getContext(),slide_in_left);
        viewFlipper.setOutAnimation(this.getContext(),slide_out_right);
    }
    @Override
    public void onClick(View v) {
    }
}