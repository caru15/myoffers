
package com.example.myoffers_2;

import android.Manifest;
import android.app.ProgressDialog;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.Base64;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class alta_prod extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //directorio donde voy a  guardar la foto
    private String CARPETA_RAIZ="misImagenes/";
    //ruta q el sistema va a cargar la imagen desde el celular
    private String Ruta_Imagen;
    private ImageView imagen;
    private String mParam1;
    private AsyncHttpClient conexion=new AsyncHttpClient();
    private RequestParams params= new RequestParams();
    private String mParam2;
   private EditText nombre;
   private EditText descrip;
   private EditText marca;
   private EditText canti;
    private final int CODE_SELECCIONE=10;
    private final int CODE_FOTO=20;
    private String path;
    private String path_1;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;

    private String dir;
    AdminBD bd=new AdminBD();
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
         nombre=(EditText)view.findViewById(R.id.Idnom);
         descrip=view.findViewById(R.id.Idescr);
         marca=view.findViewById(R.id.Idmarca);
         canti=view.findViewById(R.id.Idunid);
         imagen=view.findViewById(R.id.Idimag);
        Button Ima=view.findViewById(R.id.Idfoto);
        Button Guarda=view.findViewById(R.id.Idguardar);
       byteArrayOutputStream = new ByteArrayOutputStream();
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
                SubirImagen();
                alta_prodDirections.ActionAltaProdToNuevaOferta accio=alta_prodDirections.actionAltaProdToNuevaOferta();
                accio.setIdUsuario(Integer.valueOf(Nom[0]));
                accio.setNombre(Nom[1]);
                Navigation.findNavController(view).navigate(accio);
            }
        });
    }//FIN DEL ONVIEWCREATE

    //AQUI COMIENZAN LOS METODOS
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
        //aqui abre la camara
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //aqui solo me valida que puedo usar este recurso de la camara
       if (intent.resolveActivity(getContext().getPackageManager())!=null){
                File photoFile=null;
                try {
                    //en esta variable guardo el archivo temporal en donde guarde mi foto
                    //abajo esta este metodo
                    photoFile=createImageFile();
                }
                //es caso de error sale por aqui
                catch (IOException ex){
                    Log.d("error",ex.toString());
                }
                //me pregunta si el archivo no esta vacio
                if (photoFile!=null){
                    //en photo guardo la ruta del archivo photoFile
                  Uri photo=FileProvider.getUriForFile(getContext(),"com.example.myoffers_2.provider",photoFile);
                  //pasamos este objeto uri por el intent
                  intent.putExtra(MediaStore.EXTRA_OUTPUT,photo);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //esto me manda un codigo al metodo onActivityResult que me dice que esta abriendo la camara CODE_FOTO=20
                    startActivityForResult(intent,CODE_FOTO);
                } }
    }
//este metodo es el resultado de la actividad de tomar foto en donde le paso el codigo que genero
    //dicha actividad y la fotografia este metodo lo que hace es traer la imagen y mostarla en mi IMAGEN VIEW
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //aqui hago un switch con 2 opciones o viene de la camara(20) o la foto viene de la galeria de imagenes(10)
        switch (requestCode){
            case 10:
                Uri mypath=data.getData();
                try {
                    //decodifico la imagen que esta en la galeria y la mando al imagen view
                    bitmap=android.provider.MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),mypath);
                    imagen.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
                break;
            case 20:
                //se toma la imagen se la decodifica en bitmap y se la manda al imagenview
                bitmap=BitmapFactory.decodeFile(Ruta_Imagen);
                imagen.setImageBitmap(bitmap);
              //  bitmap=(Bitmap) data.getExtras().get("data");
              //  imagen.setImageBitmap(bitmap);
                break;
        }
    }

    public void SubirImagen() {
     bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
      byteArray=byteArrayOutputStream.toByteArray();
       String Convertimagen=Base64.encodeToString(byteArray,Base64.DEFAULT);
        //----------AQUI SUBIMOS AL SERVIDOR TODOS LOS DATOS------//
        dir=bd.dirProd();
        String nombre_pro=nombre.getText().toString();
        String descrip_pro=descrip.getText().toString();
        String marca_pro=marca.getText().toString();
        String canti_pro=canti.getText().toString();
        params.put("type","alta");
        params.put("nom",nombre_pro);
        params.put("desc",descrip_pro);
        params.put("marca",marca_pro);
        params.put("cant",canti_pro);
        params.put("imagen",Convertimagen);
        conexion.post(dir, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Error","no se conecto "+responseString);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                   Log.d("entro 1",responseString);
                try {
                    JSONObject JO=new JSONObject(responseString);
                    String respuesta=JO.getString("message");
                    Toast toast=Toast.makeText(getContext(),respuesta,Toast.LENGTH_LONG);
                    toast.show();
                }
                catch (Exception e){
                    Log.d("se entro por el catch",responseString);
                    e.printStackTrace();
                } }
        });
    }
//este metodo retorna un archivo temp en donde esta guardada mi foto
    private File createImageFile() throws IOException{
        String imageFileName=(String.valueOf(System.currentTimeMillis()/100));
        //este archivo tiene en donde se va a ubicar el archivo
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);;
        //creamos un archivo temporal de nuestra fotografia estara en el storageDir antes creado
        File image=File.createTempFile(imageFileName,".jpg",storageDir);
        //ruta absoluta de donde esta guardando mi archivo junto con su nombre
        Ruta_Imagen=image.getAbsolutePath();
        return image;
    }

}