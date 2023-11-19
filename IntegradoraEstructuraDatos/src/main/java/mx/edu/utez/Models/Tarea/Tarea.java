package mx.edu.utez.Models.Tarea;

import java.util.Date;

public class Tarea {
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    private Date fecha;

    public Tarea() {

    }

    public Tarea(String titulo, String descripcion, String estado, String prioridad, Date fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fecha = fecha;
    }

    public Tarea(String titulo, String descripcion, String estado, String prioridad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getFecha() {
        // Cortar la fecha de esto Sat Nov 18 00:00:00 CST 2023 a Nov 18 2023
        String fechaString = fecha.toString();
        String[] fechaArray = fechaString.split(" ");
        return fechaArray[1] + " " + fechaArray[2] + " " + fechaArray[5];
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
