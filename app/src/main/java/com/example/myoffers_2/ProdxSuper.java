package com.example.myoffers_2;

public class ProdxSuper {
    private int id, id_super, id_prod, id_usua, cantPraoferta;
    private double precio;

    public ProdxSuper() {

    }
    public ProdxSuper(int id,int id_super,int id_prod, int id_usua, int cant, double precio){
        this.id=id;
        this.id_super=id_super;
        this.id_prod=id_prod;
        this.id_usua=id_usua;
        this.cantPraoferta=cant;
        this.precio=precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_super() {
        return id_super;
    }

    public void setId_super(int id_super) {
        this.id_super = id_super;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public int getId_usua() {
        return id_usua;
    }

    public void setId_usua(int id_usua) {
        this.id_usua = id_usua;
    }

    public int getCantPraoferta() {
        return cantPraoferta;
    }

    public void setCantPraoferta(int cantPraoferta) {
        this.cantPraoferta = cantPraoferta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}