package com.example.proyectoappmovil;

public class Ruta {

    String id;
    String nombre;
    String tipo;
    float distancia;
    String dificultad;

    public Ruta() { //firebase
    }

    public Ruta(String id, String nombre, String tipo, float distancia, String dificultad) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.distancia = distancia;
        this.dificultad = dificultad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }
}
