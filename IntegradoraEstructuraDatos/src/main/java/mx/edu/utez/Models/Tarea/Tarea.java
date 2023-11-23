package mx.edu.utez.Models.Tarea;

import java.util.Date;

public class Tarea {
    private int id_tarea;
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    private Date fecha;
    private String fechaString;

    public Tarea() {

    }

    public Tarea(int id_tarea, String titulo, String descripcion, String estado, String prioridad, Date fecha, String fechaString) {
        this.id_tarea = id_tarea;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fecha = fecha;
        this.fechaString = fechaString;
    }

    public Tarea(String titulo, String descripcion, String estado, String prioridad, String fechaString) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fechaString = fechaString;
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

    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
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

    public String getFechaString() {
        return fechaString;
    }

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id_tarea=" + id_tarea +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", fecha=" + fecha +
                ", fechaString='" + fechaString + '\'' +
                '}';
    }
}
