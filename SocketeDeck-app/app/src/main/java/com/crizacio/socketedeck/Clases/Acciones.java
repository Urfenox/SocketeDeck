package com.crizacio.socketedeck.Clases;

import java.util.ArrayList;
import java.util.List;

public class Acciones {
    private String nombre;
    private List<String> textos_acciones;

    public Acciones() {}

    public Acciones(String nombre, List<String> textos_acciones) {
        this.nombre = nombre;
        this.textos_acciones = new ArrayList<>(textos_acciones);
    }

    public String getNombre() {
        return nombre;
    }
    public List<String> getTextos_acciones() {
        return textos_acciones;
    }
}
