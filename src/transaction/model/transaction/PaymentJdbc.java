package transaction.model.transaction;

import java.util.List;
import transaction.model.transaction.response.ResponseListTablePayment;

/**
 *
 * @author Khanza
 */
public interface PaymentJdbc {

    public abstract List<ResponseListTablePayment> selectPayments(String search);

    public abstract void insertPayment(Payment payment);

}
