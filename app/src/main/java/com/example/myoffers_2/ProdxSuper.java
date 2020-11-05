package com.example.myoffers_2;

public class ProdxSuper {
    private int id;
    private double precio;
    private String imagen;
    private String nombre;
    private int cant;
    private String descripcion;
    private String superNom;

    public ProdxSuper() {

    }
    public ProdxSuper(int id,String nombre, int cant,String descripcion,String superNom, double precio, String imagen){
        this.id=id;
        this.nombre=nombre;
        this.cant=cant;
        this.descripcion=descripcion;
        this.superNom=superNom;
        this.precio=precio;
        this.imagen=imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantPraoferta() {
        return cant;
    }

    public void setCantPraoferta(int cantPraoferta) {
        this.cant= cantPraoferta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSuperNom() {
        return superNom;
    }

    public void setSuperNom(String superNom) {
        this.superNom = superNom;
    }
}