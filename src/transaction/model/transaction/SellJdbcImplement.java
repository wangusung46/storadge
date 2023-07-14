package transaction.model.transaction;

import transaction.model.transaction.response.ResponseListTableSell;
import transaction.connection.Conn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Khanza
 */
public class SellJdbcImplement implements SellJdbc {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public SellJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public List<ResponseListTableSell> selectSells(String search, Long idPayment) {
        List<ResponseListTableSell> sells = new ArrayList<>();
        try {
            sql = "SELECT a.id, d.name, e.name, f.name, a.sell_amount, c.sell_price, c.buy_price, g.id "
                    + "FROM sell a "
                    + "LEFT JOIN buy c ON a.id_buy = c.id "
                    + "LEFT JOIN category d ON c.id_category = d.id "
                    + "LEFT JOIN item e ON c.id_item = e.id "
                    + "LEFT JOIN unit f ON c.id_unit = f.id "
                    + "LEFT JOIN payment g ON a.id_payment = g.id "
                    + "WHERE "
                    + "(a.id LIKE ? "
                    + "OR d.name LIKE ? "
                    + "OR e.name LIKE ? "
                    + "OR f.name LIKE ? "
                    + "OR a.sell_amount LIKE ? "
                    + "OR c.sell_price LIKE ? "
                    + "OR c.buy_price LIKE ?) "
                    + "AND "
                    + "a.id_payment = ? "
                    + "ORDER BY a.id DESC";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setString(5, search);
            preparedStatement.setString(6, search);
            preparedStatement.setString(7, search);
            preparedStatement.setLong(8, idPayment);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResponseListTableSell sell = new ResponseListTableSell();
                sell.setId(resultSet.getLong(1));
                sell.getBuy().getCategory().setName(resultSet.getString(2));
                sell.getBuy().getItem().setName(resultSet.getString(3));
                sell.getBuy().getUnit().setName(resultSet.getString(4));
                sell.setSellAmount(resultSet.getInt(5));
                sell.getBuy().setSellPrice(resultSet.getBigDecimal(6));
                sell.getBuy().setBuyPrice(resultSet.getBigDecimal(7));
                sell.getPayment().setId(resultSet.getLong(8));
                sells.add(sell);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return sells;
    }

    @Override
    public void insertSell(Sell sell) {
        try {
            sql = "INSERT INTO sell VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, sell.getId());
            preparedStatement.setLong(2, sell.getBuy().getId());
            preparedStatement.setLong(3, sell.getSellAmount());
            preparedStatement.setLong(4, sell.getPayment().getId());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Payment lastInsertId() {
        Payment payment = new Payment();
        try {
            sql = "SELECT LAST_INSERT_ID()";
            preparedStatement = connection.prepareStatement(sql);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                payment.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return payment;
    }

}
