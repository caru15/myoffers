package com.example.myoffers_2;

public class Modelo {
    private String Nombre;
    private String Marca;
    private int imagen;

    public Modelo (){

    }

    public Modelo(String nombre, String marca, int imagen) {
        Nombre = nombre;
        Marca = marca;
        this.imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

}
