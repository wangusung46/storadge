package transaction.model.supplier;

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
public class SupplierJdbcImplement implements SupplierJdbc {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public SupplierJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public List<Supplier> selectSuppliers(String search) {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            sql = "SELECT * "
                    + "FROM supplier "
                    + "WHERE id LIKE ? "
                    + "OR name LIKE ? "
                    + "OR hand_phone LIKE ? "
                    + "OR email LIKE ? "
                    + "OR address LIKE ? "
                    + "ORDER BY id DESC";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setString(5, search);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                supplier.setId(resultSet.getLong(1));
                supplier.setName(resultSet.getString(2));
                supplier.setHandPhone(resultSet.getString(3));
                supplier.setEmail(resultSet.getString(4));
                supplier.setAddress(resultSet.getString(5));
                suppliers.add(supplier);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return suppliers;
    }

    @Override
    public void insertSupplier(Supplier supplier) {
        try {
            sql = "INSERT INTO supplier VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, supplier.getId());
            preparedStatement.setString(2, supplier.getName());
            preparedStatement.setString(3, supplier.getHandPhone());
            preparedStatement.setString(4, supplier.getEmail());
            preparedStatement.setString(5, supplier.getAddress());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        try {
            sql = "UPDATE supplier SET "
                    + "name = ?, "
                    + "hand_phone = ?, "
                    + "email = ?, "
                    + "address = ? "
                    + "WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getHandPhone());
            preparedStatement.setString(3, supplier.getEmail());
            preparedStatement.setString(4, supplier.getAddress());
            preparedStatement.setLong(5, supplier.getId());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteSupplier(Long id) {
        try {
            sql = "DELETE FROM supplier WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
