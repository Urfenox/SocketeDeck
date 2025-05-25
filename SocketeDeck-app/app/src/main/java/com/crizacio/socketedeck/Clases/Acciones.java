package com.crizacio.socketedeck.Clases;

import java.util.ArrayList;
import java.util.List;

public class Acciones {
    private String nombre;
    private String descripcion;
    private String autor;
    private String version;
    private String modulo;
    private List<String> textos_acciones;

    public Acciones() {}

    public Acciones(String nombre, String descripcion, String autor, String version, String modulo, List<String> textos_acciones) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.autor = autor;
        this.version = version;
        this.modulo = modulo;
        this.textos_acciones = new ArrayList<>(textos_acciones);
    }

    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getAutor() {
        return autor;
    }
    public String getVersion() {
        return version;
    }
    public String getModulo() {
        return modulo;
    }
    public List<String> getTextos_acciones() {
        return textos_acciones;
    }
}
