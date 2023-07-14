package transaction.model.transaction;

import transaction.model.transaction.response.ResponseListTableStock;
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
public class StockJdbcImplement implements StockJdbc {

    private final Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private String sql;

    public StockJdbcImplement() {
        connection = Conn.getConnection();
    }

    @Override
    public List<ResponseListTableStock> selectStocks(String search) {
        List<ResponseListTableStock> stocks = new ArrayList<>();
        try {
            sql = "SELECT b.name, c.name, d.name, e.name, SUM(a.amount_item) - IFNULL(SUM(f.sell_amount), 0) AS amount "
                    + "FROM buy a "
                    + "LEFT JOIN supplier b ON a.id_supplier = b.id "
                    + "LEFT JOIN category c ON a.id_category = c.id "
                    + "LEFT JOIN item d ON a.id_item = d.id "
                    + "LEFT JOIN unit e ON a.id_unit = e.id "
                    + "LEFT JOIN sell f ON a.id = f.id_buy "
                    + "WHERE b.name LIKE ? "
                    + "OR c.name LIKE ? "
                    + "OR d.name LIKE ? "
                    + "OR e.name LIKE ? "
                    + "GROUP BY b.id, c.id, d.id, e.id "
                    + "ORDER BY a.id";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResponseListTableStock stock = new ResponseListTableStock();
                stock.getSupplier().setName(resultSet.getString(1));
                stock.getCategory().setName(resultSet.getString(2));
                stock.getItem().setName(resultSet.getString(3));
                stock.getUnit().setName(resultSet.getString(4));
                stock.setCountItem(resultSet.getInt(5));
                stocks.add(stock);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.WARNING_MESSAGE);
        }
        return stocks;
    }
}
