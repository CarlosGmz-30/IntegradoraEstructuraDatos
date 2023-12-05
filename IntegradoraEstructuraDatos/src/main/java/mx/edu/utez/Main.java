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
        Queue<Tarea> queueTareas = new ArrayDeque<>();
        int opc = 0;

        do {
            try {
                opc = menuPrincipal(sc);
                sc.nextLine();
                switch (opc) {
                    case 1: // Mostrar todas las tareas
                        mostrarTareasFindAll();
                        break;
                    case 2: // Agregar tarea
                        if (!tareaLinkedList.isEmpty()) {
                            mostrarTareasFindAll();
                        }
                        Tarea tareaNueva = agregarTarea();
                        if (tareaNueva != null) {
                            tareaLinkedList.add(tareaNueva);
                            System.out.println("\nTarea agregada con exito\n");
                        } else {
                            System.out.println("\n**Error al agregar tarea**\n");
                        }
                        break;
                    case 3: // TODO: 12/01/2023 Agregar varias tareas
                        Stack<Tarea> variasTareas = agregarVariasTareas();
                        if (variasTareas != null) {
                            TareaDao tareaDao = new TareaDao();
                            tareaDao.insertAll(variasTareas);
                        }
                        break;
                    case 4: // TODO: 11/19/2023 Tareas pendientes
                        TareaDao daoTarea = new TareaDao();
                        List<Tarea> tareaList = daoTarea.findall(); // obtenemos la lista de tareas de la base de datos
                        for (Tarea tarea : tareaList) { // llenamos el stack de tareas con la lista obtenida de la base de datos
                            stackTareas.push(tarea);
                        }
                        int opcTareasPendientes = 0;

                        do {
                            try {
                                if (!stackTareas.isEmpty()) {
                                    mostrarTareasFindAll();
                                    System.out.println("""
                                            1.- Marcar tarea como completada.
                                            2.- Regresar <--
                                            """);
                                    System.out.print("Opcion: ");
                                    opcTareasPendientes = sc.nextInt();
                                    switch (opcTareasPendientes) {
                                        case 1: // eliminar sacar del stack (y eleminar de la base de datos)
                                            int idTarea = stackTareas.pop().getId_tarea();
                                            daoTarea.delete(idTarea);
                                            break;
                                        case 2: // regresar
                                            break;
                                    }
                                } else {
                                    System.out.println("Sin tareas pendientes");
                                    if (stackTareas.isEmpty()) break;
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                break;
                            }
                        } while (opcTareasPendientes != 2);

                        break;
                    case 5: // TODO: 11/19/2023 Tareas programadas
                        TareaDao daoTareaQ = new TareaDao();
                        List<Tarea> tareaListQ = daoTareaQ.findall();
                        Date fechaActual = new Date();
                        String fechaQ = fechaActual.toString();
                        String[] fechaArrayQ = fechaQ.split(" ");
                        String fechaComp = fechaArrayQ[1] + " " + fechaArrayQ[2] + " " + fechaArrayQ[5];
                        for (Tarea tarea : tareaListQ) {
                            if (tarea.getFechaString() != null && fechaComp.equals(tarea.getFechaString())) {
                                tarea.setEstado("programada");
                                daoTareaQ.update(tarea.getId_tarea());
                                queueTareas.add(tarea);
                            }
                        }
                        mostrarQueue(queueTareas);
                        break;
                    case 6: // TODO: 11/19/2023 Lista de prioridades
                        TareaDao daoTareaT = new TareaDao();
                        List<Tarea> tareaListT = daoTareaT.findall();
                        TreeSet<Tarea> tareaTreeSet = new TreeSet<>();
                        for (Tarea tarea : tareaListT) {
                            tareaTreeSet.add(tarea);
                        }
                        mostrarTree(tareaTreeSet);
                        break;
                    case 7:
                        System.out.println("camara!");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("CATCH! MENU PRINCIPAL");

            }
        } while (opc != 7);
    }

    public static int menuPrincipal(Scanner sc) {
        int opc = 0;
        try {
            System.out.println("""
                                    
                    1.- Mostrar tareas.
                    2.- Agregar tarea.
                    3.- Agregar varias tareas.
                    4.- Tareas pendientes.
                    5.- Tareas programadas.
                    6.- Lista de prioridades.
                    7.- Salir.
                    """);
            System.out.print("Opcion: ");
            return sc.nextInt();

        } catch (InputMismatchException e) {
            return opc;
        }

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

    public static void mostrarTareasFindAll() {
        TareaDao tareaDao = new TareaDao();
        AtomicInteger contador = new AtomicInteger(1); // para poder hacer el incremento
        List<Tarea> tareaList = tareaDao.findall();
        if (!tareaList.isEmpty()) {
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
        } else {
            System.out.println("\nAUN NO HAY TAREAS AGREGADAS");
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
                        tarea.getFecha());
                //tarea.getFechaString());
                System.out.println();
                contador.getAndIncrement(); // esta wea es para aumentar el contador en 1(no sabia que en una lambda no era tan facil)
            });
            System.out.println("-----------------------------------------------------------------------------------\n");
        } else {
            System.out.println("El stack de tareas esta vacio");
        }
    }

    public static void mostrarQueue(Queue<Tarea> queueTareas) {
        AtomicInteger contador = new AtomicInteger(1); // para poder hacer el incremento
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%-4s %-16s %-25s %-10s %-12s %-12s", "No.", "TITULO", "DESCRIPCION", "PRIORIDAD", "ESTADO", "FECHA");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------");
        queueTareas.forEach(tarea -> {
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

    public static void mostrarTree(TreeSet<Tarea> treeTareas) {
        AtomicInteger contador = new AtomicInteger(1); // para poder hacer el incremento
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.printf("%-4s %-16s %-25s %-10s %-12s %-12s", "No.", "TITULO", "DESCRIPCION", "PRIORIDAD", "ESTADO", "FECHA");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------");
        treeTareas.forEach(tarea -> {
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

    public static Stack<Tarea> agregarVariasTareas() {
        Stack<Tarea> variasTareas = new Stack<>();
        Scanner sc = new Scanner(System.in);
        int opc = 0;
        do {
            try {
                if (!variasTareas.isEmpty()) {
                    System.out.println("Tareas agregadas");
                    mostrarStackTareas(variasTareas);
                }
                System.out.println("""
                        1.- Agregar tarea.
                        2.- Finalizar agregacion.
                        3.- Cancelar [X]
                        """);
                System.out.print("Opcion: ");
                opc = sc.nextInt();
                switch (opc) {
                    case 1:
                        sc.nextLine();
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
                        variasTareas.push(tarea);
                        break;
                    case 2:
                        return variasTareas;
                    case 3:
                        variasTareas.clear();
                        return variasTareas;
                }
            } catch (InputMismatchException e) {
                System.out.println("\n    ! OPCION NO VALIDA !\n");
            }
            sc.nextLine();
        } while (opc != 3);
        return null;
    }

}