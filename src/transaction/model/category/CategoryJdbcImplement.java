package transaction.model.category;

import transaction.connection.Conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Khanza
 */
public class CategoryJdbcImplement implements CategoryJdbc {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public CategoryJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public List<Category> selectCategorys(String search) {
        List<Category> categorys = new ArrayList<>();
        try {
            sql = "SELECT * "
                    + "FROM category "
                    + "WHERE id LIKE ? "
                    + "OR name LIKE ? "
                    + "ORDER BY id DESC";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong(1));
                category.setName(resultSet.getString(2));
                categorys.add(category);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return categorys;
    }

    @Override
    public void insertCategory(Category category) {
        try {
            sql = "INSERT INTO category VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, category.getId());
            preparedStatement.setString(2, category.getName());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateCategory(Category category) {
        try {
            sql = "UPDATE category SET "
                    + "name = ? "
                    + "WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setLong(2, category.getId());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        try {
            sql = "DELETE FROM category WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Boolean countTotal() {
        try {
            sql = "SELECT COUNT(*) FROM category";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(1) > 0) {
                    return true;
                }
            }
            resultSet.close();
            preparedStatement.close();
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

}
