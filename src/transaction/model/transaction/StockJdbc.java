package transaction.model.transaction;

import transaction.model.transaction.response.ResponseListTableStock;
import java.util.List;

/**
 *
 * @author Khanza
 */
public interface StockJdbc {

    public abstract List<ResponseListTableStock> selectStocks(String search);

}
