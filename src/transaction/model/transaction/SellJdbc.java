package transaction.model.transaction;

import transaction.model.transaction.response.ResponseListTableSell;
import java.util.List;

/**
 *
 * @author Khanza
 */
public interface SellJdbc {

    public abstract List<ResponseListTableSell> selectSells(String search, Long idPayment);
    
    public abstract Payment lastInsertId();

    public abstract void insertSell(Sell sell);

}
