package transaction.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Khanza
 */
public class Payment {
    
    private Long id;
    private BigDecimal cash;
    private LocalDateTime date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Payment{" + "id=" + id + ", cash=" + cash + ", date=" + date + '}';
    }
    
}
