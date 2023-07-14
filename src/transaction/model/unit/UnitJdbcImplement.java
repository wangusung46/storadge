package transaction.model.unit;

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
public class UnitJdbcImplement implements UnitJdbc {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public UnitJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public List<Unit> selectUnits(String search) {
        List<Unit> units = new ArrayList<>();
        try {
            sql = "SELECT * "
                    + "FROM unit "
                    + "WHERE id LIKE ? "
                    + "OR name LIKE ? "
                    + "ORDER BY id DESC";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Unit unit = new Unit();
                unit.setId(resultSet.getLong(1));
                unit.setName(resultSet.getString(2));
                units.add(unit);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return units;
    }

    @Override
    public void insertUnit(Unit unit) {
        try {
            sql = "INSERT INTO unit VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, unit.getId());
            preparedStatement.setString(2, unit.getName());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateUnit(Unit unit) {
        try {
            sql = "UPDATE unit SET "
                    + "name = ? "
                    + "WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, unit.getName());
            preparedStatement.setLong(2, unit.getId());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteUnit(Long id) {
        try {
            sql = "DELETE FROM unit WHERE id = ?";
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
    public Unit selectUnitByName(String name) {
        Unit unit = new Unit();
        try {
            sql = "SELECT * FROM unit WHERE name = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                unit.setId(resultSet.getLong(1));
                unit.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return unit;
    }

    @Override
    public Boolean countTotal() {
        try {
            sql = "SELECT COUNT(*) FROM unit";
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
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
