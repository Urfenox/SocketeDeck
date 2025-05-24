package com.crizacio.socketedeck.Clases;

import java.util.ArrayList;
import java.util.List;

public class Configuracion {
    private String accion;
    private List<Acciones> acciones;

    public Configuracion() {}

    public Configuracion(String accion, List<Acciones> acciones) {
        this.accion = accion;
        this.acciones = new ArrayList<>(acciones);
    }

    public String getAccion() {
        return accion;
    }
    public void setAccion(String accion) {
        this.accion = accion;
    }

    public List<Acciones> getAcciones() {
        return acciones;
    }
}