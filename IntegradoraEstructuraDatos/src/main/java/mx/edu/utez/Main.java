package mx.edu.utez;

import mx.edu.utez.Models.Tarea.Tarea;
import mx.edu.utez.Models.Tarea.TareaDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Tarea> tareaLinkedList = new LinkedList<>();

        int opc = 0;

        do {
            try {
                opc = menuPrincipal(sc);
                switch (opc) {
                    case 1:
                        Tarea tareaNueva = agregarTarea();
                        if (tareaNueva != null) {
                            tareaLinkedList.add(tareaNueva);
                            System.out.println("\nTarea agregada con exito\n");
                        } else {
                            System.out.println("\n**Error al agregar tarea**\n");
                        }
                        mostrarTareas(tareaLinkedList);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("camara!");
                        break;
                }


            } catch (Exception e) {
                System.out.println("CATCH!");
                System.out.println(e);
            }
        } while (opc != 5);

    }

    public static int menuPrincipal(Scanner sc) {
        System.out.println("""
                1.- Agregar tarea.
                2.- Tareas pendientes.
                3.- Tareas programadas.
                4.- Lista de prioridades.
                5.- Salir.
                """);
        return sc.nextInt();
    }

    public static Tarea agregarTarea() {
        TareaDao tareaDao = new TareaDao();
        Scanner sc = new Scanner(System.in);
        Tarea tarea = new Tarea();
        System.out.println("Titulo:");
        tarea.setTitulo(sc.nextLine());
        System.out.println("Descripcion:");
        tarea.setDescripcion(sc.nextLine());
        System.out.println("Fecha de entrega (formato dd/MM/yyyy):");
        String fechaString = sc.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date fecha = dateFormat.parse(fechaString);
            tarea.setFecha(fecha);
        } catch (Exception e) {
            System.out.println("Error al convertir la fecha");
        }

        System.out.println("Prioridad:");
        tarea.setPrioridad(sc.nextLine().toLowerCase());
        tarea.setEstado("pendiente");
        //sc.close();
        if (tareaDao.create(tarea)) {
            return tarea;
        } else {
            return null;
        }
    }

    public static void mostrarTareas(LinkedList<Tarea> tareasLinkedList) {
        System.out.println("\n========== TAREAS ACTUALES ==========\n");
        tareasLinkedList.forEach(tarea -> {
            System.out.printf("""
                    ------------------------------
                    Tarea #1: %s
                    Fecha entrega: %s
                    ------------------------------
                    """, tarea.getTitulo(), tarea.getFecha());
        });
    }

}