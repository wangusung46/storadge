package transaction.model.transaction;

import transaction.connection.Conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import transaction.model.transaction.response.ResponseListCartId;
import transaction.model.transaction.response.ResponseListTableCart;

/**
 *
 * @author Khanza
 */
public class CartJdbcImplement implements CartJdbc {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public CartJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public List<ResponseListTableCart> selectCarts(String search) {
        List<ResponseListTableCart> carts = new ArrayList<>();
        try {
            sql = "SELECT a.id, d.name, e.name, f.name, a.cart_amount, c.sell_price "
                    + "FROM cart a "
                    + "LEFT JOIN buy c ON a.id_buy = c.id "
                    + "LEFT JOIN category d ON c.id_category = d.id "
                    + "LEFT JOIN item e ON c.id_item = e.id "
                    + "LEFT JOIN unit f ON c.id_unit = f.id "
                    + "WHERE a.id LIKE ? "
                    + "OR d.name LIKE ? "
                    + "OR e.name LIKE ? "
                    + "OR f.name LIKE ? "
                    + "OR a.cart_amount LIKE ? "
                    + "OR c.sell_price LIKE ? "
                    + "ORDER BY a.id DESC";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setString(5, search);
            preparedStatement.setString(6, search);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResponseListTableCart cart = new ResponseListTableCart();
                cart.setId(resultSet.getLong(1));
                cart.setNameCategory(resultSet.getString(2));
                cart.setNameItem(resultSet.getString(3));
                cart.setNameUnit(resultSet.getString(4));
                cart.setAmountSell(resultSet.getInt(5));
                cart.setPriceSell(resultSet.getBigDecimal(6));
                carts.add(cart);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return carts;
    }

    @Override
    public void insertCart(Cart cart) {
        try {
            sql = "INSERT INTO cart VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, cart.getId());
            preparedStatement.setLong(2, cart.getBuy().getId());
            preparedStatement.setLong(3, cart.getCartAmount());
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteCart(Long id) {
        try {
            sql = "DELETE FROM cart WHERE id = ?";
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
    public Integer totalPriceCart() {
        Integer total = new Integer(0);
        try {
            sql = "SELECT SUM(a.cart_amount * b.sell_price) "
                    + "FROM cart a "
                    + "LEFT JOIN buy b ON a.id_buy = b.id";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return total;
    }

    @Override
    public void resetCart() {
        try {
            sql = "TRUNCATE TABLE cart";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<ResponseListCartId> selectCartsId() {
        List<ResponseListCartId> carts = new ArrayList<>();
        try {
            sql = "SELECT d.id, e.id, f.id, c.id, a.cart_amount "
                    + "FROM cart a "
                    + "LEFT JOIN buy c ON a.id_buy = c.id "
                    + "LEFT JOIN category d ON c.id_category = d.id "
                    + "LEFT JOIN item e ON c.id_item = e.id "
                    + "LEFT JOIN unit f ON c.id_unit = f.id ";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResponseListCartId cart = new ResponseListCartId();
                cart.setIdCategory(resultSet.getLong(1));
                cart.setIdItem(resultSet.getLong(2));
                cart.setIdUnit(resultSet.getLong(3));
                cart.setIdBuy(resultSet.getLong(4));
                cart.setCartAmount(resultSet.getInt(5));
                carts.add(cart);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return carts;
    }

}
