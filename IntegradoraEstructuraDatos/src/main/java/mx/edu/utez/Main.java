package mx.edu.utez;

import mx.edu.utez.Models.Tarea.Tarea;
import mx.edu.utez.Models.Tarea.TareaDao;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LinkedList<Tarea> tareaLinkedList = new LinkedList<>();
        Stack<Tarea> stackTareas = new Stack<>();
        int opc = 0;



        do {
            if (!tareaLinkedList.isEmpty()) {
                mostrarLinkedListTareas(tareaLinkedList);
                //mostrarTareasFindAll();
            }
            try {
                opc = menuPrincipal(sc);
                switch (opc) {
                    case 1:
                        if (!tareaLinkedList.isEmpty()) {
                            //System.out.println("linked list");
                            mostrarLinkedListTareas(tareaLinkedList);
                            //System.out.println("findAll");
                            //mostrarTareasFindAll();
                        }
                        System.out.println("""
                                1.- Agregar al final de la lista.
                                2.- Agregar en una posicion especifica.
                                3.- Regresar <--
                                """);
                        System.out.print("Opcion: ");
                        int opcCase1 = sc.nextInt();
                        switch (opcCase1) {
                            case 1:
                                Tarea tareaNueva = agregarTarea();
                                if (tareaNueva != null) {
                                    tareaLinkedList.add(tareaNueva);
                                    System.out.println("\nTarea agregada con exito\n");
                                } else {
                                    System.out.println("\n**Error al agregar tarea**\n");
                                }
                                break;
                            case 2:
                                mostrarLinkedListTareas(tareaLinkedList);
                                //agregarTareaEnPosicionEspecifica(tareaLinkedList);
                                break;
                            case 3:
                                break;
                        }
                        break;
                    case 2: // TODO: 11/19/2023 Tareas pendientes
                        TareaDao daoTarea = new TareaDao();
                        List<Tarea> tareaList = daoTarea.findall(); // obtenemos la lista de tareas de la base de datos
                        for (Tarea tarea : tareaList) { // llenamos el stack de tareas con la lista obtenida de la base de datos
                            stackTareas.push(tarea);
                        }
                        int opcTareasPendientes = 0;

                        do {
                            try {
                                if (!stackTareas.isEmpty()) {
                                   // System.out.println("stack");
                                    mostrarStackTareas(stackTareas); // mostramos el stack
                                    //System.out.println("find all");
                                   // mostrarTareasFindAll();
                                    System.out.println("""
                                            1.- Marcar tarea como completada.
                                            2.- Regresar <--
                                            """);
                                    System.out.print("Opcion: ");
                                    opcTareasPendientes = sc.nextInt();
                                    switch (opcTareasPendientes) {
                                        case 1: // eliminar sacar del stack (y eleminar de la base de datos)
                                            daoTarea.delete(stackTareas.pop().getId_tarea());
                                            break;
                                        case 2: // regresar
                                            break;
                                    }
                                } else {
                                    System.out.println("Sin tareas pendientes");
                                }
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        } while (opcTareasPendientes != 2);

                        break;
                    case 3: // TODO: 11/19/2023 Tareas programadas 
                        break;
                    case 4: // TODO: 11/19/2023 Lista de prioridades 
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
        System.out.println();
        System.out.println("""
                1.- Agregar tarea.
                2.- Tareas pendientes.
                3.- Tareas programadas.
                4.- Lista de prioridades.
                5.- Salir.
                """);
        System.out.print("Opcion: ");
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
        if (tareaDao.create(tarea)) {
            return tarea;
        } else {
            return null;
        }
    }

    public static void mostrarLinkedListTareas(LinkedList<Tarea> tareasLinkedList) {
        AtomicInteger contador = new AtomicInteger(1); // para poder hacer el incremento
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%-4s %-16s %-25s %-10s %-12s %-12s", "No.", "TITULO", "DESCRIPCION", "PRIORIDAD", "ESTADO", "FECHA");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------");
        tareasLinkedList.forEach(tarea -> {
            System.out.printf("%-4s %-16s %-25s %-10s %-12s %-12s",
                    contador,
                    tarea.getTitulo(),
                    tarea.getDescripcion(),
                    tarea.getPrioridad(),
                    tarea.getEstado(),
                    tarea.getFechaString());
            System.out.println();
            contador.getAndIncrement(); // esta wea es para aumentar el contador en 1(no sabia que en una lambda no era tan facil)
        });
        System.out.println("-----------------------------------------------------------------------------------\n");
    }

    public static void mostrarTareasFindAll() {
        TareaDao tareaDao = new TareaDao();
        AtomicInteger contador = new AtomicInteger(1); // para poder hacer el incremento
        List<Tarea> tareaList = tareaDao.findall();
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%-4s %-16s %-25s %-10s %-12s %-12s", "No.", "TITULO", "DESCRIPCION", "PRIORIDAD", "ESTADO", "FECHA");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------");
        tareaList.forEach(tarea -> {
            System.out.printf("%-4s %-16s %-25s %-10s %-12s %-12s",
                    contador,
                    tarea.getTitulo(),
                    tarea.getDescripcion(),
                    tarea.getPrioridad(),
                    tarea.getEstado(),
                    tarea.getFechaString());
            System.out.println();
            contador.getAndIncrement(); // esta wea es para aumentar el contador en 1(no sabia que en una lambda no era tan facil)
        });
        System.out.println("-----------------------------------------------------------------------------------\n");
    }

    public static void agregarTareaEnPosicionEspecifica(LinkedList<Tarea> tareasLinkedList) {
        Scanner sc = new Scanner(System.in);
        Tarea tareaNuevaCase2 = agregarTarea();
        //mostrarTareasFindAll();
        //mostrarTareas(tareaLinkedList);
        boolean continuar = false;
        if (tareaNuevaCase2 != null) {
            do {
                System.out.println("Ingresa la posicion en la que quieres agregar la tarea");
                int index = sc.nextInt();
                try {
                    tareasLinkedList.add(index - 1, tareaNuevaCase2);
                    continuar = false;
                } catch (Exception e) {
                    System.out.println("Esa posicion no existe, elige otra");
                    continuar = true;
                }
            } while (continuar);
            System.out.println("\nTarea agregada con exito\n");
        } else {
            System.out.println("\n**Error al agregar tarea**\n");
        }
    }

    public static void mostrarStackTareas(Stack<Tarea> stackTareas) {
        if (!stackTareas.isEmpty()) {
            AtomicInteger contador = new AtomicInteger(1); // para poder hacer el incremento
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.printf("%-4s %-16s %-25s %-10s %-12s %-12s", "No.", "TITULO", "DESCRIPCION", "PRIORIDAD", "ESTADO", "FECHA");
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------");
            stackTareas.forEach(tarea -> {
                System.out.printf("%-4s %-16s %-25s %-10s %-12s %-12s",
                        contador,
                        tarea.getTitulo(),
                        tarea.getDescripcion(),
                        tarea.getPrioridad(),
                        tarea.getEstado(),
                        tarea.getFechaString());
                System.out.println();
                contador.getAndIncrement(); // esta wea es para aumentar el contador en 1(no sabia que en una lambda no era tan facil)
            });
            System.out.println("-----------------------------------------------------------------------------------\n");
        } else {
            System.out.println("El stack de tareas esta vacio");
        }
    }


}