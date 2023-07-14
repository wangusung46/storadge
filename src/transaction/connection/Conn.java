package transaction.connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn {

    private static java.sql.Connection connection;
    private static Statement st = null;

    public static java.sql.Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "root",
                        "");
                st = connection.createStatement();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e);
            }
        }
        return connection;

    }
}
