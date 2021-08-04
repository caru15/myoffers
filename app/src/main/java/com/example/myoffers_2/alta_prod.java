
package com.example.myoffers_2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;

import androidx.core.content.ContextCompat;
import androidx.cursoradapter.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class alta_prod extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //directorio donde voy a  guardar la foto
    private String CARPETA_RAIZ="misImagenes/";
    private  String nombre="";
    //ruta q el sistema va a cargar la imagen desde el celular
    private String Ruta_Imagen;
    private ImageView imagen;
    private String mParam1;
    private String mParam2;
    private final int CODE_SELECCIONE=10;
    private final int CODE_FOTO=20;
    private String path;
    private String path_1;
    private Bitmap bitmap;

    public alta_prod() {
        // Required empty public constructor
    }

    public static alta_prod newInstance(String param1, String param2) {
        alta_prod fragment = new alta_prod();
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
        return inflater.inflate(R.layout.fragment_alta_prod, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String idmasNombre=alta_prodArgs.fromBundle(getArguments()).getIdNombre();
        String[] Nom=idmasNombre.split("-");
        EditText nombre=view.findViewById(R.id.Idnom);
        EditText descrip=view.findViewById(R.id.Idescr);
        EditText marca=view.findViewById(R.id.Idmarca);
        EditText canti=view.findViewById(R.id.Idunid);
         imagen=view.findViewById(R.id.Idimag);
        Button Ima=view.findViewById(R.id.Idfoto);
        Button Guarda=view.findViewById(R.id.Idguardar);

        //verificarPro()-este metodo es para futuro buscaria si el producto que esta por cargarse
        //ya existe en la BD
        Ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarDialogoOpciones();
            }
        });
        Guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ingresar ala data base
                alta_prodDirections.ActionAltaProdToNuevaOferta accio=alta_prodDirections.actionAltaProdToNuevaOferta();
                accio.setIdUsuario(Integer.valueOf(Nom[0]));
                accio.setNombre(Nom[1]);
                Navigation.findNavController(view).navigate(accio);
            }
        });
    }

    private void MostrarDialogoOpciones() {
        final CharSequence[] opciones={"Tomar Foto","Elejir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Elije una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (opciones[i].equals("Tomar Foto")){
                  TomarFoto();
                }else{
                    if (opciones[i].equals("Elejir de Galeria")){
                        Intent intent=new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),CODE_SELECCIONE);
                    }else{
                        dialog.dismiss(); } }
            }
        });builder.show();
    }

    private void TomarFoto() {
       /** String nombre="";
        File file=new File(Environment.getExternalStorageDirectory(),CARPETA_IMAGE);
        boolean isCreate=file.exists();
        if (isCreate==false){
            isCreate=file.mkdirs();
        }if (isCreate==true){
            nombre=(String.valueOf(System.currentTimeMillis()/100))+"jpg";
        }
        //esta variable path tiene la ruta para guardar la imagen
        path=Environment.getExternalStorageDirectory()+File.separator+CARPETA_IMAGE+File.separator+nombre;
        File myfile= new File(path);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photo=FileProvider.getUriForFile(getContext(),"com.example.myoffers_2.provider",myfile);
        Log.d("se guardo",String.valueOf(photo));
        path_1=myfile.getAbsolutePath();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photo);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);**/

       Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       if (intent.resolveActivity(getContext().getPackageManager())!=null){
                File photoFile=null;

                try {

                    photoFile=createImageFile();
                }catch (IOException ex){
                    Log.d("error",ex.toString());
                }
                if (photoFile!=null){
                  Uri photo=FileProvider.getUriForFile(getContext(),"com.example.myoffers_2.provider",photoFile);
                  intent.putExtra(MediaStore.EXTRA_OUTPUT,photo);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(intent,20);
                } }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                Uri mypath=data.getData();
                imagen.setImageURI(mypath);
                break;
            case 20:
                Bitmap ima=BitmapFactory.decodeFile(Ruta_Imagen);
                imagen.setImageBitmap(ima);
                break;
        }
    }
    private File createImageFile() throws IOException{
        String imageFileName=(String.valueOf(System.currentTimeMillis()/100));
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);;
        File image=File.createTempFile(imageFileName,".jpg",storageDir);
        Ruta_Imagen=image.getAbsolutePath();
        return image;
    }
}