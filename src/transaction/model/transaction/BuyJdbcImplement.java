package transaction.model.transaction;

import transaction.model.transaction.response.ResponseListTableBuy;
import transaction.connection.Conn;
import transaction.model.category.Category;
import transaction.model.item.Item;
import transaction.model.unit.Unit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Khanza
 */
public class BuyJdbcImplement implements BuyJdbc {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public BuyJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public List<ResponseListTableBuy> selectBuys(String search) {
        List<ResponseListTableBuy> buys = new ArrayList<>();
        try {
            sql = "SELECT a.id, c.name, d.name, e.name, f.name, a.amount_item, a.buy_price, a.sell_price, a.date "
                    + "FROM buy a "
                    + "LEFT JOIN supplier c ON a.id_supplier = c.id "
                    + "LEFT JOIN category d ON a.id_category = d.id "
                    + "LEFT JOIN item e ON a.id_item = e.id "
                    + "LEFT JOIN unit f ON a.id_unit = f.id "
                    + "WHERE a.id LIKE ? "
                    + "OR c.name LIKE ? "
                    + "OR d.name LIKE ? "
                    + "OR e.name LIKE ? "
                    + "OR f.name LIKE ? "
                    + "OR a.amount_item LIKE ? "
                    + "OR a.buy_price LIKE ? "
                    + "OR a.sell_price LIKE ? "
                    + "OR a.date LIKE ? "
                    + "ORDER BY a.id DESC";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setString(5, search);
            preparedStatement.setString(6, search);
            preparedStatement.setString(7, search);
            preparedStatement.setString(8, search);
            preparedStatement.setString(9, search);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResponseListTableBuy buy = new ResponseListTableBuy();
                buy.setId(resultSet.getLong(1));
                buy.getSupplier().setName(resultSet.getString(2));
                buy.getCategory().setName(resultSet.getString(3));
                buy.getItem().setName(resultSet.getString(4));
                buy.getUnit().setName(resultSet.getString(5));
                buy.setCountItem(resultSet.getInt(6));
                buy.setBuyPrice(resultSet.getBigDecimal(7));
                buy.setSellPrice(resultSet.getBigDecimal(8));
                buy.setDate(resultSet.getTimestamp(9).toLocalDateTime());
                buys.add(buy);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return buys;
    }

    @Override
    public void insertBuy(Buy buy) {
        try {
            sql = "INSERT INTO buy VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, buy.getId());
            preparedStatement.setLong(2, buy.getSupplier().getId());
            preparedStatement.setLong(3, buy.getCategory().getId());
            preparedStatement.setLong(4, buy.getItem().getId());
            preparedStatement.setLong(5, buy.getUnit().getId());
            preparedStatement.setInt(6, buy.getCountItem());
            preparedStatement.setBigDecimal(7, buy.getBuyPrice());
            preparedStatement.setBigDecimal(8, buy.getSellPrice());
            preparedStatement.setTimestamp(9, Timestamp.valueOf(buy.getDate()));
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateBuy(Buy buy) {
        try {
            sql = "UPDATE buy SET "
                    + "id_supplier = ?, "
                    + "id_category = ?, "
                    + "id_item = ?, "
                    + "id_unit = ?, "
                    + "amount_item = ?, "
                    + "buy_price = ?, "
                    + "sell_price = ? "
                    + "WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, buy.getSupplier().getId());
            preparedStatement.setLong(2, buy.getCategory().getId());
            preparedStatement.setLong(3, buy.getItem().getId());
            preparedStatement.setLong(4, buy.getUnit().getId());
            preparedStatement.setInt(5, buy.getCountItem());
            preparedStatement.setBigDecimal(6, buy.getBuyPrice());
            preparedStatement.setBigDecimal(7, buy.getSellPrice());
            preparedStatement.setLong(8, buy.getId());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteBuy(Long id) {
        try {
            sql = "DELETE FROM buy WHERE id = ?";
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
    public Buy lastInsertId() {
        Buy buy = new Buy();
        try {
            sql = "SELECT LAST_INSERT_ID()";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                buy.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return buy;
    }

    @Override
    public List<Category> selectBuyCategory() {
        List<Category> categorys = new ArrayList<>();
        try {
            sql = "SELECT b.* FROM buy a LEFT JOIN category b ON a.id_category = b.id GROUP BY b.id";
            preparedStatement = connection.prepareStatement(sql);
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
    public List<Item> selectBuyItem(Long idCategory) {
        List<Item> items = new ArrayList<>();
        try {
            sql = "SELECT c.* FROM buy a "
                    + "LEFT JOIN category b ON a.id_category = b.id "
                    + "LEFT JOIN item c ON a.id_item = c.id "
                    + "WHERE b.id = ? "
                    + "GROUP BY c.id";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idCategory);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getLong(1));
                item.setName(resultSet.getString(2));
                items.add(item);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return items;
    }

    @Override
    public List<Unit> selectBuyUnit(Long idCategory, Long idItem) {
        List<Unit> units = new ArrayList<>();
        try {
            sql = "SELECT d.* FROM buy a "
                    + "LEFT JOIN category b ON a.id_category = b.id "
                    + "LEFT JOIN item c ON a.id_item = c.id "
                    + "LEFT JOIN unit d ON a.id_unit = d.id "
                    + "WHERE b.id = ? AND c.id = ? "
                    + "GROUP BY d.id";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idCategory);
            preparedStatement.setLong(2, idItem);
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
    public List<Buy> selectDate(Long idCategory, Long idItem, Long idUnit) {
        List<Buy> buys = new ArrayList<>();
        try {
            sql = "SELECT a.* FROM buy a "
                    + "LEFT JOIN category b ON a.id_category = b.id "
                    + "LEFT JOIN item c ON a.id_item = c.id "
                    + "LEFT JOIN unit d ON a.id_unit = d.id "
                    + "WHERE b.id = ? AND c.id = ? AND d.id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idCategory);
            preparedStatement.setLong(2, idItem);
            preparedStatement.setLong(3, idUnit);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Buy buy = new Buy();
                buy.setId(resultSet.getLong(1));
                buy.setCountItem(resultSet.getInt(6));
                buy.setBuyPrice(resultSet.getBigDecimal(7));
                buy.setSellPrice(resultSet.getBigDecimal(8));
                buy.setDate(resultSet.getTimestamp(9).toLocalDateTime());
                buys.add(buy);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return buys;
    }

    @Override
    public Buy selectBuy(Long idCategory, Long idItem, Long idUnit, Long id) {
        Buy buy = new Buy();
        try {
            sql = "SELECT a.* FROM buy a "
                    + "LEFT JOIN category b ON a.id_category = b.id "
                    + "LEFT JOIN item c ON a.id_item = c.id "
                    + "LEFT JOIN unit d ON a.id_unit = d.id "
                    + "WHERE b.id = ? AND c.id = ? AND d.id = ? AND a.id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idCategory);
            preparedStatement.setLong(2, idItem);
            preparedStatement.setLong(3, idUnit);
            preparedStatement.setLong(4, id);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                buy.setId(resultSet.getLong(1));
                buy.setCountItem(resultSet.getInt(6));
                buy.setBuyPrice(resultSet.getBigDecimal(7));
                buy.setSellPrice(resultSet.getBigDecimal(8));
                buy.setDate(resultSet.getTimestamp(9).toLocalDateTime());
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return buy;
    }

    @Override
    public Buy selectStock(Long idCategory, Long idItem, Long idUnit, Long id) {
        Buy buy = new Buy();
        try {
            sql = "SELECT a.amount_item - IFNULL(f.cart_amount, 0) AS amount "
                    + "FROM buy a "
                    + "LEFT JOIN category c ON a.id_category = c.id "
                    + "LEFT JOIN item d ON a.id_item = d.id "
                    + "LEFT JOIN unit e ON a.id_unit = e.id "
                    + "LEFT JOIN cart f ON a.id = f.id_buy "
                    + "WHERE c.id = ? AND d.id = ? AND e.id = ? AND a.id = ? ";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idCategory);
            preparedStatement.setLong(2, idItem);
            preparedStatement.setLong(3, idUnit);
            preparedStatement.setLong(4, id);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                buy.setCountItem(resultSet.getInt(1));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return buy;
    }

}
