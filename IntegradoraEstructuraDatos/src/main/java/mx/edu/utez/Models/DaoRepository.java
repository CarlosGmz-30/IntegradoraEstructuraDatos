package mx.edu.utez.Models;

import mx.edu.utez.Models.Tarea.Tarea;

import java.util.List;

public interface DaoRepository<T> {
    List<T> findall();
    T findOne(int id);
    boolean create(T object);

    boolean create(Tarea tarea);

    boolean delete(int id);
    boolean update(int id);
}
