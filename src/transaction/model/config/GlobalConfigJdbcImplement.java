package transaction.model.config;

import transaction.connection.Conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Khanza
 */
public class GlobalConfigJdbcImplement implements GlobalConfigJdbc{
    
    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;
    
    public GlobalConfigJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public String selectGlobalConfigValue(Long id) {
        String value = new String();
        try {
            sql = "SELECT value FROM config WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                value = resultSet.getString(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return value;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
    
}
