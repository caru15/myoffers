package com.example.myoffers_2;

public class Modelo {
    private String Nombre;
    private String Marca;
    private String Descripcion;
    private String imagen;
    private int id;

    public Modelo (){
    }

    public Modelo(String nombre, String marca, String imagen,String des,int id) {
        Nombre = nombre;
        Marca = marca;
        Descripcion=des;
        this.id=id;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        this.Marca = marca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
