package com.example.myoffers_2;

import android.util.Log;
import android.widget.Toast;

import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class AdminBD {

    private String Uri;

    public AdminBD() {

       Uri="http://192.168.0.12/myOffers/";
    }

//me devuelve la uri de mi producto
    public String dirProd(){

       String dir=this.Uri+"productos.php";
        return dir;
    }
  public String correo(){
        String dir=this.Uri+"correo.php";
        return dir;
  }
public String dirLogin(){
        String dir=this.Uri+"login.php";
        return dir;
}
    public String dirSuper() {

      String dir=this.Uri+"supermercados.php";
      return dir;
    }

    public String dirProdSuper(){
        String dir=this.Uri+"prodxsuper.php";
        return dir;
    }
    public String dirUsuarios(){
        String dir=this.Uri+"usuarios.php";
        return dir;
    }

}
