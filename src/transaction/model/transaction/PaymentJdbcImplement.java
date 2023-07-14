package transaction.model.transaction;

import transaction.model.transaction.response.ResponseListTablePayment;
import transaction.connection.Conn;
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
public class PaymentJdbcImplement implements PaymentJdbc {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public PaymentJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public List<ResponseListTablePayment> selectPayments(String search) {
        List<ResponseListTablePayment> payments = new ArrayList<>();
        try {
            sql = "SELECT "
                    + "a.id, "
                    + "SUM(b.sell_amount * c.sell_price) AS total, a.cash, "
                    + "a.cash - SUM(b.sell_amount * c.sell_price) AS back, "
                    + "a.date "
                    + "FROM payment a "
                    + "LEFT JOIN sell b ON a.id = b.id_payment "
                    + "LEFT JOIN buy c ON b.id_buy = c.id "
                    + "WHERE a.id LIKE ? "
                    + "OR a.cash LIKE ? "
                    + "OR a.date LIKE ? "
                    + "GROUP BY a.id "
                    + "ORDER BY a.id DESC";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResponseListTablePayment payment = new ResponseListTablePayment();
                payment.setId(resultSet.getLong(1));
                payment.setTotal(resultSet.getBigDecimal(2));
                payment.setCash(resultSet.getBigDecimal(3));
                payment.setChange(resultSet.getBigDecimal(4));
                payment.setDate(resultSet.getTimestamp(5).toLocalDateTime());
                payments.add(payment);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return payments;
    }

    @Override
    public void insertPayment(Payment payment) {
        try {
            sql = "INSERT INTO payment VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, payment.getId());
            preparedStatement.setBigDecimal(2, payment.getCash());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(payment.getDate()));
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
