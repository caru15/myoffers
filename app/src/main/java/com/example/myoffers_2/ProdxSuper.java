package com.example.myoffers_2;

public class ProdxSuper {
    private int id;
    private double precio;
    private String imagen;
    private String nombre;//es el nombre del producto
    private int cant;
    private String descripcion;
    private String superNom;
    private float distancia;
    private String direccion;//direccion del super
    private double latitud;
    private double longitud;
    private int id_prod;
    private int id_super;

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

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public int getId_super() {
        return id_super;
    }

    public void setId_super(int id_super) {
        this.id_super = id_super;
    }
}