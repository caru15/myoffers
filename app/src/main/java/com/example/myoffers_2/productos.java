package com.example.myoffers_2;

public class productos {
    private int id;
    private String nombre;
    private String descripcion;
    private String marca;
    private int cant_unidad;
    private int imagen;

    public productos(int id,String nombre, String descripcion, String marca, int cant_unidad, int imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.cant_unidad = cant_unidad;
        this.imagen = imagen;
        this.id=id; //ojo aqui que agregaste este campo fijate si no te causa problemas en la insercion de un producto
       // lo agregue porque cuando leo un producto de la BD necesito recuperar tambien su id
    }
    public productos(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCant_unidad() {
        return cant_unidad;
    }

    public void setCant_unidad(int cant_unidad) {
        this.cant_unidad = cant_unidad;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

}
