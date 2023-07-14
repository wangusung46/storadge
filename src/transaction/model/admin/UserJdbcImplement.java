package transaction.model.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import transaction.connection.Conn;

/**
 *
 * @author Khanza
 */
public class UserJdbcImplement implements UserJdbc {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public UserJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public Boolean login(String userName, String password) {
        try {
            sql = "SELECT * FROM user WHERE user_name = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resultSet.close();
                preparedStatement.close();
                return true;
            } else {
                resultSet.close();
                preparedStatement.close();
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    @Override
    public void insertUser(User user) {
        try {
            if (selectUserExist(user.getUserName())) {
                sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, user.getUserName());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setString(5, user.getGender());
                preparedStatement.setString(6, user.getHandPhone());
                preparedStatement.setString(7, user.getAddress());
                preparedStatement.setString(8, user.getRole());
                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } else {
                JOptionPane.showMessageDialog(null, "Username sudah terdaftar", "Warning", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateUser(Long id, User user) {
        try {
            if (selectUserExist(user.getUserName())) {
                sql = "UPDATE user SET "
                        + "user_name = ?, "
                        + "name = ?, "
                        + "password = ?, "
                        + "gender = ?, "
                        + "hand_phone = ?, "
                        + "address = ?, "
                        + "role = ? "
                        + "WHERE id = ?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getGender());
                preparedStatement.setString(4, user.getHandPhone());
                preparedStatement.setString(5, user.getAddress());
                preparedStatement.setString(6, user.getRole());
                preparedStatement.setLong(7, id);
                System.out.println(preparedStatement.toString());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } else {
                JOptionPane.showMessageDialog(null, "Username sudah terdaftar", "Warning", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Boolean selectUserExist(String userName) {
        try {
            sql = "SELECT * FROM user WHERE user_name = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                resultSet.close();
                preparedStatement.close();
                return false;
            } else {
                resultSet.close();
                preparedStatement.close();
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

}
