package com.example.myoffers_2;

public class supermercados {
    private int id;
    private String nombre, direccion,localidad;
    private Double lat, longitud;
    private Float distancia;

    public supermercados(String nombre, String direccion, String localidad, Double Lat, Double longitud) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.localidad = localidad;
       // this.provincia = provincia;
        this.lat=Lat;
        this.longitud=longitud;
    }

    public supermercados() {
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Float getDistancia() {
        return distancia;
    }

    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }
}
