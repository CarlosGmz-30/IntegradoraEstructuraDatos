package mx.edu.utez.Models.Tarea;

import mx.edu.utez.Models.DaoRepository;
import mx.edu.utez.Utils.MysqlConector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TareaDao implements DaoRepository {

    @Override
    public List<Tarea> findall() {
        List<Tarea> tareaList = new ArrayList<>();
        Connection connection = new MysqlConector().connect();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM tarea;");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                Tarea tarea = new Tarea();
                tarea.setId_tarea(res.getInt("id_tarea"));
                tarea.setTitulo(res.getString("titulo"));
                tarea.setDescripcion(res.getString("descripcion"));
                tarea.setPrioridad(res.getString("prioridad"));
                tarea.setEstado(res.getString("estado"));
                tarea.setFechaString(res.getString("fecha"));
                tareaList.add(tarea);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return tareaList;
    }

    @Override
    public Object findOne(int id) {
        return null;
    }

    @Override
    public boolean create(Object object) {
        return false;
    }

    @Override
    public boolean create(Tarea tarea) {
        Connection connection = new MysqlConector().connect();
        try {
            PreparedStatement stmt = connection.prepareStatement(" call agregar_tarea(?,?,?,?,?);");
            stmt.setString(1, tarea.getTitulo());
            stmt.setString(2, tarea.getDescripcion());
            stmt.setString(3, tarea.getFecha());
            stmt.setString(4, tarea.getPrioridad());
            stmt.setString(5, tarea.getEstado());
            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertAll(Stack<Tarea> variasTareas) {
        Connection connection = new MysqlConector().connect();
        for (Tarea tarea : variasTareas) {
            try {
                PreparedStatement stmt = connection.prepareStatement(" call agregar_tarea(?,?,?,?,?);");
                stmt.setString(1, tarea.getTitulo());
                stmt.setString(2, tarea.getDescripcion());
                stmt.setString(3, tarea.getFecha());
                stmt.setString(4, tarea.getPrioridad());
                stmt.setString(5, tarea.getEstado());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public boolean delete(int id) {
        Connection connection = new MysqlConector().connect();
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM tarea WHERE id_tarea = ?");
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(int id) {
        Connection connection = new MysqlConector().connect();
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE tarea SET estado = 'programada' WHERE id_tarea = ?");
            stmt.setInt(1, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
