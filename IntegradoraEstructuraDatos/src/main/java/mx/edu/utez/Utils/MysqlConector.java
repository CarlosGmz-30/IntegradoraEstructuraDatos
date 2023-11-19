package mx.edu.utez.Utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConector {
    final String DBNAME = "tareas",
            USER = "root",
            PASSWORD = "root",
            TIMEZONE = "America/Mexico_City",
            USESSL = "false",
            PUBLICKEY = "true",
            DDLAUTO = "true",
            HOST = "localhost";

    public Connection connect() {
        String dataSource = String.format(
                "jdbc:mysql://%s:3306/%s?user=%s&password=%s&serverTimezone=%s&useSSL=%s&allowPublicKeyRetrieval=%s&createDatabaseIfNotExist=%s", HOST, DBNAME, USER, PASSWORD, TIMEZONE, USESSL, PUBLICKEY, DDLAUTO);
        try {
            DriverManager.registerDriver(
                    new com.mysql.cj.jdbc.Driver());
            return DriverManager.getConnection(
                    dataSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void main(String[] args) {
        try {
            Connection conn = new

                    MysqlConector().connect();
            if (conn != null) {
                System.out.println("Conexion realizada");
                conn.close();
            } else {
                System.out.println("Conexion fallida");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
}
