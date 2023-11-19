package mx.edu.utez.Models.Tarea;

import mx.edu.utez.Models.DaoRepository;
import mx.edu.utez.Utils.MysqlConector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TareaDao implements DaoRepository {

    @Override
    public List findall() {
        return null;
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
            //PreparedStatement stmt = connection.prepareStatement(" call agregar_tarea('Investigacion','Investigacion sobre JWT','media','pendiente')");
            PreparedStatement stmt = connection.prepareStatement(" call agregar_tarea(?,?,?,?);");

            stmt.setString(1, tarea.getTitulo());
            stmt.setString(2, tarea.getDescripcion());
            stmt.setString(3, tarea.getPrioridad());
            stmt.setString(4, tarea.getEstado());
            return stmt.executeUpdate() == 1;


        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean update(Object object, int id) {
        return false;
    }
}

/*
public boolean create(Usuario usr) {
        Connection con = new MysqlConector().connect();
        try {
            PreparedStatement stmt = con.prepareStatement("CALL REGISTRAR_USUARIO(?,?,?,?);");
            stmt.setString(1, usr.getCorreoUsuario());
            stmt.setString(2, usr.getNombreUsuario());
            stmt.setString(3, usr.getApellidoUsuario());
            stmt.setString(4, usr.getContrasenaUsuario());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
 */