package transaction.model.transaction.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import transaction.model.transaction.Buy;
import transaction.model.transaction.Payment;

/**
 *
 * @author Khanza
 */
public class ResponseListTableSell {

    private Long id;
    private Buy buy;
    private Integer sellAmount;
    private Payment payment;

    public ResponseListTableSell() {
        buy = new Buy();
        payment = new Payment();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Buy getBuy() {
        return buy;
    }

    public void setBuy(Buy buy) {
        this.buy = buy;
    }

    public Integer getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(Integer sellAmount) {
        this.sellAmount = sellAmount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "ResponseListTableSell{" + "id=" + id + ", buy=" + buy + ", sellAmount=" + sellAmount + ", payment=" + payment + '}';
    }
}
