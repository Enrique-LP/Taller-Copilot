package com.academia.batch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Documento de MongoDB (@Document collection = "reportes_estudiantes") con:
// id (String, anotado con @Id), nombre (String), grupo (String), promedio (double) y estado (String),
// Constructor vacío, getters y setters y toString

@Document(collection = "reportes_estudiantes")
public class EstudianteReporte {




    @Id private String id;
    private String nombre;
    private String grupo;
    private double promedio;
    private String estado;

    public EstudianteReporte() {
    }

    public EstudianteReporte(String id, String nombre, String grupo, double promedio, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.grupo = grupo;
        this.promedio = promedio;
        this.estado = estado;
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}
