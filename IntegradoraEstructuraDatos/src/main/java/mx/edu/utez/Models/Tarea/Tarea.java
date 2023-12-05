package mx.edu.utez.Models.Tarea;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tarea implements Comparable<Tarea>{
    private int id_tarea;
    private String titulo;
    private String descripcion;
    private String estado;
    private String prioridad;
    private Date fecha;
    private String fechaString;

    public Tarea() {

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
                '}'+"\n";
    }

    @Override
    public int compareTo(Tarea comparada) {
        int priori = 0, priori2 = 0;
        String prioridad = this.getPrioridad();
        switch (prioridad){
            case "alta":
                priori = 3;
                break;
            case "media":
                priori = 2;
                break;
            case "baja":
                priori = 1;
                break;
            default:
                System.out.println("");
        }
        String prioridad2 = comparada.getPrioridad();
        switch (prioridad2){
            case "alta":
                priori2 = 3;
                break;
            case "media":
                priori2 = 2;
                break;
            case "baja":
                priori2 = 1;
                break;
            default:
                System.out.println("");
        }
        if (priori != priori2) {
            return Integer.compare(priori2, priori);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);
            Date fecha1;
            Date fecha2;
            try {
                fecha1 = sdf.parse(this.getFechaString());
                fecha2 = sdf.parse(comparada.getFechaString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            int comparacionFecha = fecha1.compareTo(fecha2);
            if (comparacionFecha != 0) {
                return comparacionFecha;
            }
            return 0;
        }
    }
}
