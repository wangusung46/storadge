package transaction.model.transaction.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import transaction.model.transaction.Buy;

/**
 *
 * @author Khanza
 */
public class ResponseListTablePayment {

    private Long id;
    private BigDecimal total;
    private BigDecimal cash;
    private BigDecimal change;
    private LocalDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ResponseListTablePayment{" + "id=" + id + ", total=" + total + ", cash=" + cash + ", change=" + change + ", date=" + date + '}';
    }

}
